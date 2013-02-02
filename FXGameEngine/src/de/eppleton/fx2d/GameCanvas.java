/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

/**
 * A Canvas used to render TileMaps.
 *
 * @author antonepple
 */
public final class GameCanvas extends Canvas {

    private int FPS = 30;

    public int getFPS() {
        return FPS;
    }

    public void setFPS(int FPS) {
        this.FPS = FPS;
    }
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
    private ArrayList<MoveValidator> moveValidators;
    private final List<Sprite> sprites;
    public double cameraX;
    public double cameraY;
    public final double cameraMaxX;
    public final double cameraMaxY;

    public GameCanvas(double totalWidth, double totalHeight, double width, double height) {
        super(width, height);
        this.screenWidth = width;
        this.screenHeight = height;
        this.cameraMaxX = totalWidth - width;
        this.cameraMaxY = totalHeight - height;
        this.layers = new ArrayList<>();
        this.moveValidators = new ArrayList<>();

        final Duration oneFrameAmt = Duration.millis(1_000 / FPS);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
                new EventHandler() {
                    @Override
                    public void handle(Event t) {
                        pulse();
                    }
                });

        TimelineBuilder.create()
                .targetFramerate(FPS)
                .cycleCount(Animation.INDEFINITE)
                .keyFrames(oneFrame)
                .build()
                .play();

        sprites = new ArrayList<>();
    }

    public Sprite getHero() {
        return hero;
    }

    public void addMoveValidator(MoveValidator moveValidator) {
        moveValidators.add(moveValidator);
    }

    public void removeMoveValidator(MoveValidator moveValidator) {
        moveValidators.remove(moveValidator);
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
                Collections.sort(sprites, comparator);
                for (Sprite sprite : sprites) {
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
            hero = new Sprite(this, "center", centerX, centerY, 0, 0);
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

    public void pulse() {
        update();
        render();
    }

    private void update() {

        for (Sprite sprite : sprites) {
            sprite.pulse(this);
            updateSpritePosition(sprite);
        }
        updateCamera();
        for (Layer layer : layers) {
            layer.pulse();
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
    public boolean isValidMove(double x, double y, double width, double height) {

        for (MoveValidator moveValidator : moveValidators) {
            if (!moveValidator.isValidMove(x, y, width, height)) {
                return false;
            }
        }
        return true;
    }

    public void addSprite(Sprite monsterSprite) {
        sprites.add(monsterSprite);
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
        sprites.add(hero);
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

    public void removeSprite(final Sprite sprite) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sprites.remove(sprite);
            }
        });
    }

    public interface MoveValidator {

        public abstract boolean isValidMove(double x, double y, double width, double height);
    }
    private static final Logger LOG = Logger.getLogger(GameCanvas.class.getName());
}
