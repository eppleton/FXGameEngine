/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samples.spaceinvaders;

import de.eppleton.fx2d.Collision;
import de.eppleton.fx2d.Game;
import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.Layer;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.SpriteLayer;
import de.eppleton.fx2d.action.Action;
import de.eppleton.fx2d.action.ActionFactory;
import de.eppleton.fx2d.action.Animation;
import de.eppleton.fx2d.action.SpriteAction;
import de.eppleton.fx2d.tileengine.TileMapReader;
import de.eppleton.fx2d.tileengine.TileSet;
import de.eppleton.fx2d.tileengine.TileSetAnimation;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javax.xml.bind.JAXBException;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author antonepple
 */
public class SpaceInvaders extends Game {

    private Points TEN = new Points(10);
    private Points TWENTY = new Points(30);
    private Points THIRTY = new Points(40);
    private Animation invaderExplode;
    private Points MYSTERY = new Points(50) {
        @Override
        public int getPoints() {
            if (mysteryShots == 15 || mysteryShots == 23) {
                return 300;
            }
            return 100;
        }
    };
    int mysteryShots = 0;
    int score = 0;
    int lives = 3;
    int credits = 2;
    private String message;
    int[][] enemies = new int[][]{
        {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30},
        {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20},
        {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20},
        {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
        {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}
    };

    @Override
    protected void initGame() {
        GameCanvas canvas = getCanvas();
        try {
            TileSet invaders = TileMapReader.readSet("/assets/graphics/invaders1.tsx");
            // add enemies
            for (int i = 0; i < enemies.length; i++) {
                int[] is = enemies[i];
                for (int j = 0; j < is.length; j++) {
                    int k = is[j];
                    Points points = k == 30 ? THIRTY : k == 20 ? TWENTY : TEN;

                    Sprite sprite = new Sprite(canvas, "" + ((j * 11) + i), 50 + (40 * j), 140 + (40 * i), 30, 20, Lookups.fixed(points));
                    sprite.setAnimation(k == 30 ? new TileSetAnimation(invaders, new int[]{0, 1}, 500) : k == 20 ? new TileSetAnimation(invaders, new int[]{2, 3}, 500) : new TileSetAnimation(invaders, new int[]{4, 5}, 500));
                    sprite.setVelocityX(.3);
                }
            }

            TileSet explodeTiles = TileMapReader.readSet("/assets/graphics/invaders_explode.tsx");
            invaderExplode = new TileSetAnimation(explodeTiles, new int[]{0}, 100_000);
            TileSet playerTiles = TileMapReader.readSet("/assets/graphics/player.tsx");
            TileSetAnimation playerAnimation = new TileSetAnimation(playerTiles, new int[]{0}, 100_000);
            Sprite player = new Sprite(canvas, playerAnimation, "Player", 350, 620, 40, 30, Lookup.EMPTY);
            player.setAnimation(new TileSetAnimation(playerTiles, new int[]{0}, 100_000));
            player.addAction(KeyCode.LEFT, ActionFactory.createMoveAction(playerAnimation, "left", -4, 0, 0, 0));
            player.addAction(KeyCode.RIGHT, ActionFactory.createMoveAction(playerAnimation, "left", 4, 0, 0, 0));
            player.addAction(KeyCode.UP, new SpriteAction(playerAnimation, "fire") {
                @Override
                public void started(Sprite sprite) {
                    if (sprite.getParent().getSprite("bullet") == null) {
                        Sprite bullet = new Sprite(sprite.getParent(), "bullet", sprite.getX(), sprite.getY() + 10, 10, 20, Lookup.EMPTY);
                        bullet.setVelocityY(-10);
                        SeekAndDestroyBehaviour action = new SeekAndDestroyBehaviour();
                        action.setEvaluationInterval(20_000_000);
                        bullet.addBehaviour(action);
                    }
                }

                class SeekAndDestroyBehaviour extends Action {

                    @Override
                    public boolean perform(Sprite sprite, GameCanvas playingField) {
                        if (sprite.getY() <= 0) {
                            playingField.removeSprite(sprite);
                            return false;
                        }
                        Collection<Collision> collisions = playingField.getCollisions(sprite);
                        for (Collision collision : collisions) {
                            Points points = collision.getSpriteTwo().getLookup().lookup(Points.class);
                            if (points != null) {
                                score += points.getPoints();
                                playingField.removeSprite(sprite);
                                playingField.removeSprite(collision.getSpriteTwo());
                                double x = collision.getSpriteTwo().getX();
                                double y = collision.getSpriteTwo().getY();
                                Sprite explosion = new Sprite(playingField, invaderExplode, "explode", x, y, 32, 32, Lookup.EMPTY);
                                Action remove = new Action() {
                                    @Override
                                    public boolean perform(Sprite sprite, GameCanvas playingField) {
                                        playingField.removeSprite(sprite);
                                        return false;
                                    }
                                };
                                remove.setEvaluationInterval(1000);
                                explosion.addBehaviour(remove);
                            }

                            return false;
                        }
                        return true;
                    }
                }
            });

        } catch (JAXBException ex) {
            Logger.getLogger(SpaceInvaders.class.getName()).log(Level.SEVERE, null, ex);
        }
        canvas.addLayer(new Layer() {
            @Override
            public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
                graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillRect(0, 0, width, height);
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.setFont(Font.font("OCR A Std", 20));
                graphicsContext.fillText("SCORE<1>    HI-SCORE    SCORE<2>", 30, 30);
                graphicsContext.fillText("" + score + "            9990   ", 30, 60);
                graphicsContext.fillText(message, 300, 400);
                graphicsContext.fillText("" + lives + "                   CREDIT " + credits, 30, 680);
                graphicsContext.setFill(Color.GREEN);
                graphicsContext.fillRect(30, 650, 640, 4);
            }
        });

        canvas.addBehaviour(new Action() {
            boolean rightward = true;

            @Override
            public boolean perform(Sprite sprite, GameCanvas playingField) {
                Collection<Sprite> sprites = playingField.getSprites();
                boolean change = false;
                boolean stop = false;
                if (sprites.isEmpty()) {
                    message = "You win!";
                    stop = true;
                }
                for (Sprite sprite1 : sprites) {
                    if (sprite1.getLookup().lookup(Points.class) != null) {
                        if (sprite1.getX() > 650 || sprite1.getX() < 50) {
                            rightward = !rightward;
                            change = true;
                            if (sprite1.getY() >= 600) {
                                message = "Game Over!";
                                stop = true;
                            }
                        }
                    }
                }
                

                if (change) {
                    for (Sprite sprite1 : sprites) {
                        if (sprite1.getLookup().lookup(Points.class) != null) {
                            sprite1.setVelocityX(-sprite1.getVelocityX() * (stop ? 0 : 1.3));
                            sprite1.setY(sprite1.getY() + 20);
                        }
                    }
                }
                return true;
            }
        });


        canvas.addLayer(new SpriteLayer());
    }

    @Override
    protected double getViewPortWidth() {
        return 700;
    }

    @Override
    protected double getViewPortHeight() {
        return 700;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class Points {

        int points;

        public Points(int points) {
            this.points = points;
        }

        public int getPoints() {
            return points;
        }
    }
}
