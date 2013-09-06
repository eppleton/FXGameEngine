/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d;

import de.eppleton.fx2d.action.Behavior;
import de.eppleton.fx2d.action.SpriteBehavior;
import de.eppleton.fx2d.collision.Collision;
import de.eppleton.fx2d.event.Event;
import de.eppleton.fx2d.event.EventDispatcher;
import de.eppleton.fx2d.event.EventHandler;
import de.eppleton.fx2d.event.MouseEvent;
import de.eppleton.fx2d.timer.Pulse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.java.html.canvas.GraphicsContext;

/**
 *
 * @author antonepple
 */
public abstract class Level extends Screen implements Pulse.Handler{
    
    private Camera camera;
    private float alpha = 1;
    private HashMap<Behavior, Long> behaviours = new HashMap<>();
    private Updater updater = new DefaultUpdater();
    private Pulse timer;
    private static Comparator<Sprite> comparator = new Comparator<Sprite>() {
        @Override
        public int compare(Sprite o1, Sprite o2) {
            if (o1.getY() > o2.getY()) {
                return 1;
            } else if (o1.getY() == o2.getY()) {
                return 0;
            } else {
                return -1;
            }
        }
    };
    private final double screenWidth;
    private final double screenHeight;
    private ArrayList<Layer> layers;
    private final HashMap<String, Sprite> sprites;
    public double cameraX;
    public double cameraY;
    public final double cameraMaxX;
    public final double cameraMaxY;
    private long lastPulse;
    private long timePassed;
    private boolean dirty = true;
    private int cleanCounter;
    private int pulses;
    private int stutter;
    private long lastSlowness;
    private long maxTimePassed = 0;
    private EventDispatcher eventDispatcher = new EventDispatcher() ;
    
     public Level(GraphicsContext graphicsContext, double playfieldWidth, double playfieldHeight, double viewPortWidth, double viewPortHeight) {
        this.graphicsContext = graphicsContext;
        this.screenWidth = viewPortWidth;
        this.screenHeight = viewPortHeight;
        this.cameraMaxX = playfieldWidth - viewPortWidth;
        this.cameraMaxY = playfieldHeight - viewPortHeight;
        this.layers = new ArrayList<>();
        sprites = new HashMap<>();

        addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                for (Sprite sprite : sprites.values()) {
                    if (sprite.getMouseClickHandler()!=null && sprite.contains(event.getX(), event.getY())){
                        sprite.getMouseClickHandler().handle(event);
                    }
                }
            }
        });
        timer = Pulse.create(this);
        initGame();
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void dispatchEvent(Event event){
        eventDispatcher.dispatchEvent(event);
    }
    
    public final <T extends Event> void addEventHandler(Event.Type<T> type, EventHandler<T> handler){
        eventDispatcher.addEventHandler(type, handler);
    }
    
    public final <T extends Event> void removeEventHandler(Event.Type<T> type, EventHandler<T> handler){
        eventDispatcher.removeEventHandler(type, handler);
    }
    
    /**
     * add a {@link  SpriteBehavior}
     *
     * @param spriteBehaviour
     */
    public void addBehaviour(Behavior behaviour) {
        behaviours.put(behaviour, System.nanoTime());
    }

    public int getPulses() {
        return pulses;
    }

    public void pulse(long l) {
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

        dirty = update(l);
        render(l);
        if (dirty) {
            dirty = false;
        } else {
            cleanCounter++;
        }
        lastPulse = l;
    }

    public int getStutter() {
        return stutter;
    }

    public long getLastSlowness() {
        return lastSlowness;
    }

    public long getMaxTimePassed() {
        return maxTimePassed;
    }

    public int getCleanCounter() {
        return cleanCounter;
    }

    public void render(long delta) {
        
        // clear the background
        graphicsContext.clearRect(0, 0, screenWidth, screenHeight);
        graphicsContext.setFillStyle(graphicsContext.getWebColor("#000000"));
        graphicsContext.fillRect(0, 0, screenWidth, screenHeight);
        // draw each individual layer
        for (Layer layer : layers) {
            if (layer.isVisible()) {
                layer.draw(graphicsContext, cameraX * layer.getParallaxFactor(), cameraY * layer.getParallaxFactor(), screenWidth, screenHeight);
            }
            if (layer.getName().equals("sprites")) {

                List<Sprite> values = new ArrayList<>(sprites.values());

                Collections.sort(values, comparator);
                for (Sprite sprite : values) {
                    double x = sprite.getX();
                    double y = sprite.getY();

                    if (isOnScreen(sprite)) {
                        graphicsContext.save();
                        graphicsContext.translate(x - cameraX,
                                y - cameraY);
                        sprite.drawSprite(graphicsContext, alpha, delta);
                        graphicsContext.restore();
                    }
                }

            }

        }
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    
    
    public void setCamera(Camera camera) {
        this.camera = camera;
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
        Set<Map.Entry<Behavior, Long>> entrySet = behaviours.entrySet();
        for (Map.Entry<Behavior, Long> entry : entrySet) {
            long evaluationInterval = entry.getKey().getEvaluationInterval();
            long currentTime = l;
            if ((currentTime - entry.getValue()) > evaluationInterval) {
                Behavior behavior = entry.getKey();
                if (!behavior.perform(this, l)) {
                    behaviours.remove(behavior);
                } else {
                    entry.setValue(currentTime);
                }
            }
        }
        ArrayList<Sprite> arrayList = new ArrayList<>(sprites.values());
        for (Sprite sprite : arrayList) {
            if (sprite.pulse(this, l)) {
                changed = true;
            }
        }
        // update the World, e.g. apply Physics
        if (updater.update(this, l)) {
            changed = true;
        }
        updateCamera();
        return changed;
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

    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    public void addLayer(int index, Layer layer) {
        layers.add(index, layer);
    }

    public Layer getLayer(String name) {
        for (Layer layer : layers) {
            if (layer.getName().equals(name)) {
                return layer;
            }
        }
        return null;
    }

    /**
     *
     * @param moveValidator
     * @deprecated use Physics instead or implement updater to constrain
     * movement
     */
    @Deprecated
    public void addMoveValidator(MoveValidator moveValidator) {
        if (updater instanceof DefaultUpdater) {
            ((DefaultUpdater) updater).addMoveValidator(moveValidator);
        }
    }

    public void setUpdater(Updater physicsEngine) {
        this.updater = physicsEngine;
    }

    public void removeSprite(final Sprite sprite) {
        Sprite get = sprites.get(sprite.getName());
        if (get == sprite) {
            sprites.remove(sprite.getName());
        }
    }

    public Collection<Sprite> getSprites() {
        return new ArrayList<>(sprites.values());
    }

    public void addSprite(Sprite sprite) {
        sprites.put(sprite.getName(), sprite);
    }

    public Sprite getSprite(String ball) {
        return sprites.get(ball);
    }

    public Collection<Collision> checkCollisions(Sprite sprite) {
        ArrayList<Collision> collisions = new ArrayList<>();
        ArrayList<Sprite> spriteList = new ArrayList<>(sprites.values());
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

    public void removeLayer(Layer layer) {
        layers.remove(layer);
    }

    protected abstract void initGame();

    /**
     * This can be used to plug in simple constraints or a physics engine
     */
    public interface Updater {

        public boolean update(Level aThis, long l);
    }

    private static class DefaultUpdater implements Updater {

        private long lastUpdate;
        private long delay = -1;
        private double factor;

        public DefaultUpdater() {
            this.moveValidators = new ArrayList<>();

        }

        @Override
        public boolean update(Level canvas, long l) {
            boolean dirty = false;
            if (delay == -1) {
                delay = 16666667;
            } else {
                delay = l - lastUpdate;
            }
            Collection<Sprite> sprites1 = canvas.getSprites();
            for (Sprite sprite : sprites1) {
                if (updateSpritePosition(sprite)) {
                    dirty = true;
                }
            }
            lastUpdate = l;
            return dirty;
        }

        private boolean updateSpritePosition(Sprite sprite) {
            boolean dirty = true;
            factor = (double) delay / 16666667;
            double velocityX = sprite.getVelocityX() * factor;
            double velocityY = sprite.getVelocityY() * factor;
            if (velocityX == 0 && velocityY == 0) {
                return false;
            }

            de.eppleton.fx2d.Rectangle2D moveBox = sprite.getMoveBox();
            double x = sprite.getX();
            double y = sprite.getY();
            double newX = velocityX + x;
            double newY = velocityY + y;
            if (isValidMove(newX + moveBox.getMinX(), newY + moveBox.getMinY(), moveBox.getWidth(), moveBox.getHeight())) {
                sprite.setX(newX);
                sprite.setY(newY);
            } else {
                sprite.invalidMove();
                dirty = false;
            }
            return dirty;
        }

        /**
         * Check if this is a valid move by calling all registered
         * {@link MoveValidator MoveValidators}
         *
         * @param xProperty
         * @param yProperty
         * @param width
         * @param height
         * @return true, if this is a valid move, false otherwise
         */
        private boolean isValidMove(double x, double y, double width, double height) {

            for (MoveValidator moveValidator : moveValidators) {
                if (!moveValidator.isValidMove(x, y, width, height)) {
                    return false;
                }
            }
            return true;
        }
        private ArrayList<MoveValidator> moveValidators;

        public void addMoveValidator(MoveValidator moveValidator) {
            moveValidators.add(moveValidator);
        }

        public void removeMoveValidator(MoveValidator moveValidator) {
            moveValidators.remove(moveValidator);
        }
    }

    public interface MoveValidator {
        public abstract boolean isValidMove(double x, double y, double width, double height);
    }
    
    
   
}