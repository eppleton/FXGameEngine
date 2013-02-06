/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d;

import de.eppleton.fx2d.action.Action;
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
import javax.media.j3d.Behavior;
import org.openide.util.Lookup;

/**
 * A Canvas used to render TileMaps.
 *
 * @author antonepple
 */
public final class GameCanvas extends Canvas {

    private ConcurrentHashMap<Action, Long> behaviours = new ConcurrentHashMap<>();
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
    private Sprite hero;
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
        timer.start();
        sprites = new HashMap<>();
    }

    public Sprite getHero() {
        return hero;
    }

    /**
     * add a {@link  Behavior}
     *
     * @param spriteBehaviour
     */
    public void addBehaviour(Action spriteBehaviour) {
        behaviours.put(spriteBehaviour, System.nanoTime());
    }

    public void render() {
        GraphicsContext graphicsContext2D = getGraphicsContext2D();
        // clear the background
        graphicsContext2D.clearRect(0, 0, screenWidth, screenHeight);
        // draw each individual layer
        for (Layer layer : layers) {
            if (layer.isVisible()) {
                layer.draw(graphicsContext2D, cameraX, cameraY, screenWidth, screenHeight);
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
                        sprite.drawSprite(graphicsContext2D);
                        graphicsContext2D.restore();
                    }
                }

            }

        }
    }

    private void updateCamera() {
        // the center of the screen is the preferred location of our hero
        double centerX = screenWidth / 2;
        double centerY = screenHeight / 2;
        if (hero == null) {
            hero = new Sprite(this, "center", centerX, centerY, 0, 0, Lookup.EMPTY);
        }
        cameraX = hero.getX() - centerX;
        cameraY = hero.getY() - centerY;

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

    public void pulse(long l) {
        Set<Map.Entry<Action, Long>> entrySet = behaviours.entrySet();
        for (Map.Entry<Action, Long> entry : entrySet) {
            long evaluationInterval = entry.getKey().getEvaluationInterval();
            long currentTime = System.nanoTime();
            if (currentTime - entry.getValue() > evaluationInterval) {
                Action behavior = entry.getKey();
                behavior.perform(null, this);
                entry.setValue(currentTime);
            }
        }
        update(l);
        render();
    }

    private void update(long l) {
        // invoke Behaviours, Animation, etc.
        ArrayList<Sprite> arrayList = new ArrayList<>(sprites.values());
        for (Sprite sprite : arrayList) {
            sprite.pulse(this, l);
        }
        // update the World, e.g. apply Physics
        updater.update(this, l);
        updateCamera();

    }

    /**
     * TODO this is currently required so the TileMap focuses the hero
     * character. Should propaply be schanged to add more flexibility, e.g. for
     * single-screen games.
     *
     * @param hero
     */
    public void setHero(Sprite hero) {
        this.hero = hero;
        sprites.put(hero.getName(), hero);
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
    
    public void addLayer(Layer tileMapLayer) {
        layers.add(tileMapLayer);
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

    public Collection<Collision> getCollisions(Sprite sprite) {
        ArrayList<Collision> collisions = new ArrayList<>();
        ArrayList<Sprite> spriteList = new ArrayList<>(sprites.values());
        for (Sprite sprite1 : spriteList) {
            if (sprite1 != sprite) {
                if (collision(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(),
                        sprite1.getX(), sprite1.getY(), sprite1.getWidth(), sprite1.getHeight())) {
                    collisions.add(new Collision(sprite, sprite1));
                }
            }
        }
        return collisions;
    }

    boolean collision(double x0, double y0, double w0, double h0, double x2, double y2, double w1, double h1) {
        double x1 = x0 + w0;
        double y1 = y0 + h0;

        double x3 = x2 + w1;
        double y3 = y2 + h1;

        return !(x1 < x2 || x3 < x0 || y1 < y2 || y3 < y0);
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
         * @param x
         * @param y
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
