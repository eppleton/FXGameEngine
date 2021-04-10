/**
 * This file is part of FXGameEngine A Game Engine written in JavaFX Copyright
 * (C) 2012 Anton Epple <info@eppleton.de>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. Look for COPYING file in the top folder. If not, see
 * http://opensource.org/licenses/GPL-2.0.
 *
 * For alternative licensing or use in closed source projects contact Anton
 * Epple
 * <info@eppleton.de>
 */
package de.eppleton.fx2d;

import de.eppleton.fx2d.action.Behavior;
import de.eppleton.fx2d.action.SpriteBehavior;
import de.eppleton.fx2d.collision.Collision;
import com.dukescript.api.events.Event;
import com.dukescript.api.events.EventHandler;
import com.dukescript.api.events.EventSource;
import com.dukescript.api.events.MouseEvent;
import de.eppleton.fx2d.timer.Handler;
import de.eppleton.fx2d.timer.GamePulse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author antonepple
 */
public class Level extends Screen implements Handler {

    final double screenWidth;
    final double screenHeight;
    final ArrayList<Layer> layers;
    private final HashMap<Behavior, Long> behaviours = new HashMap<Behavior, Long>();
    final HashMap<String, Sprite> sprites;
    private GamePulse timer;
    final double cameraMaxX;
    final double cameraMaxY;
    private final EventSource eventSource;
    Camera camera;
    // @TODO move to Camera
    private double cameraX;
    private double cameraY;
    // performance metrics
    private long lastPulse;
    private long timePassed;
    private boolean dirty = true;
    private int cleanCounter;
    private int pulses;
    private int stutter;
    private long lastSlowness;
    private long maxTimePassed = 0;
    // TODO not Used
    private float alpha = 1;

    public Level(double playfieldWidth, double playfieldHeight, double viewPortWidth, double viewPortHeight) {
        this.screenWidth = viewPortWidth;
        this.screenHeight = viewPortHeight;
        this.cameraMaxX = playfieldWidth - viewPortWidth;
        this.cameraMaxY = playfieldHeight - viewPortHeight;
        this.layers = new ArrayList<Layer>();
        this.sprites = new HashMap<String, Sprite>();
        eventSource = EventSource.create(this, "game-canvas", true);
        addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Collection<Sprite> sprites1 = getSprites();

                for (Sprite sprite : sprites1) {
                    if (sprite.getMouseClickHandler() != null && sprite.contains(event.getX(), event.getY())) {
                        sprite.getMouseClickHandler().handle(event);
                    }
                }
            }
        });

    }

    protected void initGame() {
    }

    public final void start() {

        if (timer == null) {
            timer = GamePulse.create(this);
        }
        initGame();
        timer.start();
    }

    public final void stop() {
        timer.stop();
    }

    /*
     * Getters and Setters 
     */
    public double getScreenWidth() {
        return screenWidth;
    }

    public double getScreenHeight() {
        return screenHeight;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public final <T extends Event> void addEventHandler(Event.Type<T> type, EventHandler<T> handler) {
        eventSource.addEventHandler(type, handler);
    }

    public final <T extends Event> void removeEventHandler(Event.Type<T> type, EventHandler<T> handler) {
        eventSource.removeEventHandler(type, handler);
    }

    List<Behavior> addBehavior;

    /**
     * add a {@link  SpriteBehavior}
     *
     * @param spriteBehaviour
     */
    public final void addBehaviour(final Behavior behaviour) {
        synchronized (behaviours) {
            behaviours.put(behaviour, System.currentTimeMillis());
        }
    }

    public final void addLayer(Layer layer) {
        layers.add(layer);
    }

    public final void addLayer(int index, Layer layer) {
        layers.add(index, layer);
    }

    public final void removeLayer(Layer layer) {
        layers.remove(layer);
    }

    public final Layer getLayer(String name) {
        for (Layer layer : layers) {
            if (layer.getName().equals(name)) {
                return layer;
            }
        }
        return null;
    }

    public final Collection<Sprite> getSprites() {
        return new ArrayList<Sprite>(sprites.values());
    }

    public final void addSprite(Sprite sprite) {
        synchronized (sprites) {
            sprites.put(sprite.getName(), sprite);
        }
    }

    public void removeSprite(final Sprite sprite) {
        Sprite get = sprites.get(sprite.getName());
        if (get == sprite) {
            synchronized (sprites) {
                sprites.remove(sprite.getName());

            }
        }
    }

    public final Sprite getSprite(String ball) {
        return sprites.get(ball);
    }



    /*
     * Game Loop 
     */
    @Override
    //TODO make private
    public final void pulse(long l) {
        // performance measurement
        pulses++;
        timePassed = l - lastPulse;
        if (timePassed > 30000000) {
            stutter++;
            lastSlowness = timePassed;
            if (timePassed > maxTimePassed) {
                if (maxTimePassed == 0) { // skip the first value
                    maxTimePassed = -1;
                } else {
                    maxTimePassed = timePassed;
                }
            }
        }
        // @TODO is dirty still doing anything? maybe remove dirty check
        dirty = update(l);
        render(l);
        if (dirty) {
            dirty = false;
        } else {
            cleanCounter++;
        }
        lastPulse = l;
    }

    private void render(long delta) {

        // draw each individual layer
        for (Layer layer : layers) {
            if (layer.isVisible()) {
                layer.draw(cameraX * layer.getParallaxFactor(), cameraY * layer.getParallaxFactor(), screenWidth, screenHeight);
            }
            if (layer.getName().equals("sprites")) {
                List<Sprite> values = new ArrayList<Sprite>(sprites.values());
                Collections.sort(values, Sprite.COMPARATOR);
                for (Sprite sprite : values) {
                    double x = sprite.getX();
                    double y = sprite.getY();

                    if (isOnScreen(sprite)) {
                        graphicsContext.save();
                        graphicsContext.translate(x - cameraX,
                                y - cameraY);
                        sprite.drawSprite(layer.graphicsContext, alpha, delta);
                        layer.graphicsContext.restore();
                    }

                }

            }
        }
    }

    private void updateCamera() {
        // the center of the screen is the preferred location of our hero
        double centerX = screenWidth / 2;
        double centerY = screenHeight / 2;
        if (camera == null) {
            camera = new Camera(centerX, centerY);
        }
        cameraX = camera.getX() - centerX;
        cameraY = camera.getY() - centerY;

        // if we get too close to the borders  
        if (cameraX <= 0) {
            cameraX = 0;
        } else if (cameraX >= cameraMaxX) {
            cameraX = cameraMaxX;
        }

        if (cameraY <= 0) {
            cameraY = 0;
        } else if (cameraY >= cameraMaxY) {
            cameraY = cameraMaxY;
        }
    }

    private boolean update(long l) {
        boolean changed = false;
        // invoke Behaviours, Animation, etc.
        List<Behavior> toRemove = new ArrayList<Behavior>();
        Set<Map.Entry<Behavior, Long>> entrySet = behaviours.entrySet();
        for (Map.Entry<Behavior, Long> entry : entrySet) {
            long evaluationInterval = entry.getKey().getEvaluationInterval();
            long currentTime = l;
            if ((currentTime - entry.getValue()) > evaluationInterval) {
                Behavior behavior = entry.getKey();
                if (!behavior.perform(this, l)) {
                    toRemove.add(behavior);
                } else {
                    entry.setValue(currentTime);
                }
            }
        }
        for (Behavior done : toRemove) {
            behaviours.remove(done);
        }
        // let the Sprites invoke their Behaviors
        Collection<Sprite> arrayList = getSprites();
        for (Sprite sprite : arrayList) {
            if (sprite.pulse(this, l)) {
                changed = true;
            }
        }
        updateCamera();
        return changed;
    }

    /*
     * Performance related
     */
    final int getPulses() {
        return pulses;
    }

    final int getStutter() {
        return stutter;
    }

    final long getLastSlowness() {
        return lastSlowness;
    }

    final long getMaxTimePassed() {
        return maxTimePassed;
    }

    final int getCleanCounter() {
        return cleanCounter;
    }

    public final Collection<Collision> checkCollisions(Sprite sprite) {
        ArrayList<Collision> collisions = new ArrayList<Collision>();
        ArrayList<Sprite> spriteList = new ArrayList<Sprite>(sprites.values());
        for (Sprite sprite1 : spriteList) {
            if (sprite1 != sprite) {
                if (isCollision(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(),
                        sprite1.getX(), sprite1.getY(), sprite1.getWidth(), sprite1.getHeight())) {
                    collisions.add(new Collision(sprite, sprite1));
                }
            }
        }
        return collisions;
    }

    boolean isCollision(double x0, double y0, double w0, double h0, double x2, double y2, double w1, double h1) {
        double x1 = x0 + w0;
        double y1 = y0 + h0;

        double x3 = x2 + w1;
        double y3 = y2 + h1;

        return !(x1 < x2 || x3 < x0 || y1 < y2 || y3 < y0);
    }

    /**
     * This returns zero by default, meaning that we call update on every tick.
     *
     * @return the update rate in ms
     */
    public int updateRate() {
        return 0;
    }

    public boolean isOnScreen(Sprite sprite) {
        double x = sprite.getX();
        double y = sprite.getY();
        double maxX = x + sprite.getWidth();
        double maxY = y + sprite.getHeight();
        if (isOnScreen(x, y)) {
            return true;
        }
        if (isOnScreen(x, maxY)) {
            return true;
        }
        if (isOnScreen(maxX, y)) {
            return true;
        }
        if (isOnScreen(maxX, maxY)) {
            return true;
        }
        return false;
    }

    private boolean isOnScreen(double x, double y) {
        double screenCoordX = x - cameraX;
        double screenCoordY = y - cameraY;

        if (screenCoordX > 0 && screenCoordX < screenWidth && screenCoordY > 0 && screenCoordY < screenHeight) {
            return true;
        }
        return false;
    }

    public interface MoveValidator {

        public abstract boolean isValidMove(double x, double y, double width, double height);
    }
}
