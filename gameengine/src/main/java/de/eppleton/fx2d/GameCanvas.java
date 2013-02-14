/**
 * This file is part of FXGameEngine 
 * A Game Engine written in JavaFX
 * Copyright (C) 2012 Anton Epple <info@eppleton.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://opensource.org/licenses/GPL-2.0.
 * 
 * For alternative licensing or use in closed source projects contact Anton Epple 
 * <info@eppleton.de>
 */
package de.eppleton.fx2d;

import de.eppleton.fx2d.collision.Collision;
import de.eppleton.fx2d.action.Behavior;
import de.eppleton.fx2d.action.SpriteBehavior;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * A Canvas used to render TileMaps.
 *
 * @author antonepple
 */
public final class GameCanvas extends Canvas {

    private Camera camera;
    private float alpha = 1;
    private ConcurrentHashMap<Behavior, Long> behaviours = new ConcurrentHashMap<>();
    private Updater updater = new DefaultUpdater();
    private AnimationTimer timer;
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

    public GameCanvas(double playfieldWidth, double playfieldHeight, double viewPortWidth, double viewPortHeight) {
        super(viewPortWidth, viewPortHeight);
        this.screenWidth = viewPortWidth;
        this.screenHeight = viewPortHeight;
        this.cameraMaxX = playfieldWidth - viewPortWidth;
        this.cameraMaxY = playfieldHeight - viewPortHeight;
        this.layers = new ArrayList<>();

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                pulse(l);
            }
        };
        sprites = new HashMap<>();
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    /**
     * add a {@link  SpriteBehavior}
     *
     * @param spriteBehaviour
     */
    public void addBehaviour(Behavior behaviour) {
        behaviours.put(behaviour, System.nanoTime());
    }

    public void pulse(long l) {
        update(l);
        render(l);
    }

    public void render(long delta) {
        GraphicsContext graphicsContext2D = getGraphicsContext2D();
        // clear the background
        graphicsContext2D.clearRect(0, 0, screenWidth, screenHeight);
        
        // draw each individual layer
        for (Layer layer : layers) {
//            System.out.println("render layer "+layer.getName()+" "+layer.isVisible());
//                System.out.println("camera "+cameraX+", "+cameraY );
            if (layer.isVisible()) {
                layer.draw(graphicsContext2D, cameraX * layer.getParallaxFactor(), cameraY * layer.getParallaxFactor(), screenWidth, screenHeight);
            }

            if (layer.getName().equals("sprites")) {

                List<Sprite> values = new ArrayList<>(sprites.values());

                Collections.sort(values, comparator);
                for (Sprite sprite : values) {
                    double x = sprite.getX();
                    double y = sprite.getY();

                    if (isOnScreen(sprite)) {
                        graphicsContext2D.save();
                        graphicsContext2D.translate(x - cameraX,
                                y - cameraY);
                        sprite.drawSprite(graphicsContext2D, alpha, delta);
                        graphicsContext2D.restore();
                    }
                }

            }

        }
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

    private void update(long l) {
        // invoke Behaviours, Animation, etc.
        Set<Map.Entry<Behavior, Long>> entrySet = behaviours.entrySet();
        for (Map.Entry<Behavior, Long> entry : entrySet) {
            long evaluationInterval = entry.getKey().getEvaluationInterval();
            long currentTime = l;
            if (currentTime - entry.getValue() > evaluationInterval) {
                Behavior behavior = entry.getKey();
                behavior.perform(this, l);
                entry.setValue(currentTime);
            }
        }
        ArrayList<Sprite> arrayList = new ArrayList<>(sprites.values());
        for (Sprite sprite : arrayList) {
            sprite.pulse(this, l);
        }
        // update the World, e.g. apply Physics
        updater.update(this, l);
        updateCamera();
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
    
    public void addLayer(int index, Layer layer){
        layers.add(index, layer);
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
        return sprites.values();
    }

    public void addSprite(Sprite sprite) {
        System.out.println("adding sprite " + sprite.getName());
        sprites.put(sprite.getName(), sprite);
        for (String name : sprites.keySet()) {
            System.out.println(">> " + name);
        }
    }

    public Sprite getSprite(String ball) {
        Sprite get = sprites.get(ball);
        if (get == null) {
            for (String name : sprites.keySet()) {
                System.out.println("> " + name);
            }
        }
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

    /**
     * This can be used to plug in simple constraints or a physics engine
     */
    public interface Updater {

        public void update(GameCanvas aThis, long l);
    }

    private static class DefaultUpdater implements Updater {

        public DefaultUpdater() {
            this.moveValidators = new ArrayList<>();

        }

        @Override
        public void update(GameCanvas canvas, long l) {
            Collection<Sprite> sprites1 = canvas.getSprites();
            for (Sprite sprite : sprites1) {
                updateSpritePosition(sprite);
            }
        }

        private void updateSpritePosition(Sprite sprite) {
            double velocityX = sprite.getVelocityX();
            double velocityY = sprite.getVelocityY();
            Rectangle2D moveBox = sprite.getMoveBox();
            double x = sprite.getX();
            double y = sprite.getY();
            double newX = velocityX + x;
            double newY = velocityY + y;
            if (isValidMove(newX + moveBox.getMinX(), newY + moveBox.getMinY(), moveBox.getWidth(), moveBox.getHeight())) {
                sprite.setX(newX);
                sprite.setY(newY);
            }
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
    private static final Logger LOG = Logger.getLogger(GameCanvas.class.getName());
}
