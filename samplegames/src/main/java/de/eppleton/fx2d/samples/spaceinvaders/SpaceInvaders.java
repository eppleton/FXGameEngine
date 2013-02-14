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
 */package de.eppleton.fx2d.samples.spaceinvaders;

import de.eppleton.fx2d.collision.*;
import de.eppleton.fx2d.*;
import de.eppleton.fx2d.action.*;
import de.eppleton.fx2d.tileengine.*;
import java.util.Collection;
import java.util.logging.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javax.xml.bind.JAXBException;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

public class SpaceInvaders extends Game {
    
    Points TEN = new Points(10);
    Points TWENTY = new Points(30);
    Points THIRTY = new Points(40);
    DoubleProperty invaderXVelocity = new SimpleDoubleProperty(0.3);
    AudioClip shootSound = new AudioClip(SpaceInvaders.class.getResource("/assets/sound/shoot.wav").toString());
    AudioClip invaderKilledSound = new AudioClip(SpaceInvaders.class.getResource("/assets/sound/invaderkilled.wav").toString());
    MediaPlayer mediaPlayer = new MediaPlayer(new Media(SpaceInvaders.class.getResource("/assets/sound/invader_loop1.mp3").toString()));
    int score = 0;
    String message;
    int[][] enemies = new int[][]{
        {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30},
        {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20},
        {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20},
        {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
        {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}
    };
    
    @Override
    protected void initGame() {
        final GameCanvas canvas = getCanvas();
        try {
            TileSet invaders = TileMapReader.readSet("/assets/graphics/invaders1.tsx");
            TileSet playerTiles = TileMapReader.readSet("/assets/graphics/player.tsx");
            final TileSetAnimation animation30 = new TileSetAnimation(invaders, new int[]{0, 1}, 2);
            final TileSetAnimation animation10 = new TileSetAnimation(invaders, new int[]{4, 5}, 2);
            final TileSetAnimation animation20 = new TileSetAnimation(invaders, new int[]{2, 3}, 2);
            final TileSetAnimation playerAnimation = new TileSetAnimation(playerTiles, new int[]{0}, 100_000);
            for (int i = 0; i < enemies.length; i++) {
                int[] is = enemies[i];
                for (int j = 0; j < is.length; j++) {
                    Points points = is[j] == 30 ? THIRTY : is[j] == 20 ? TWENTY : TEN;
                    Sprite sprite = new Sprite(canvas, "" + ((j * 11) + i), 50 + (40 * j), 140 + (40 * i), 30, 20, Lookups.fixed(points));
                    sprite.setAnimation(is[j] == 30 ? animation30 : is[j] == 20 ? animation20 : animation10);
                    sprite.setVelocityXProperty(invaderXVelocity);
                }
            }
            Sprite player = new Sprite(canvas, playerAnimation, "Player", 350, 620, 40, 30, Lookup.EMPTY);
            player.setAnimation(playerAnimation);
            player.addAction(KeyCode.LEFT, ActionFactory.createMoveAction(playerAnimation, "left", -4, 0, 0, 0));
            player.addAction(KeyCode.RIGHT, ActionFactory.createMoveAction(playerAnimation, "right", 4, 0, 0, 0));
            player.addAction(KeyCode.UP, new ShootAction(playerAnimation, "fire", new BulletProvider(), new HitHandler(), shootSound));
        } catch (JAXBException ex) {
            Logger.getLogger(SpaceInvaders.class.getName()).log(Level.SEVERE, null, ex);
        }
        canvas.addLayer(new Background());
        canvas.addBehaviour(new MoveInvadersBehavior());
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        canvas.addLayer(new SpriteLayer());
        canvas.start();
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
    
    static class BulletProvider implements SpriteProvider {
        
        @Override
        public Sprite getSprite(GameCanvas parent, double x, double y) {
            return new Sprite(parent, "bullet", x, y + 10, 10, 20, Lookup.EMPTY);
        }
    }
    
    class HitHandler implements CollisionHandler {
        
        @Override
        public void handleCollision(Collision collision) {
            Points points = collision.getSpriteTwo().getLookup().lookup(Points.class);
            if (points != null) {
                score += points.getPoints();
                invaderKilledSound.play();
                collision.getSpriteOne().remove();
                collision.getSpriteTwo().remove();
            }
        }
    }
    
    class MoveInvadersBehavior extends Behavior {
        
        @Override
        public boolean perform(GameCanvas canvas, long nanos) {
            Collection<Sprite> sprites = canvas.getSprites();
            boolean stop = false;
            boolean win = true;
            for (Sprite sprite1 : sprites) {
                if (sprite1.getLookup().lookup(Points.class) != null) {
                    win = false;
                    if (sprite1.getX() > 650 || sprite1.getX() < 50) {
                        invaderXVelocity.set(-invaderXVelocity.doubleValue() * (stop ? 0 : 1.3));
                        if (sprite1.getY() >= 600) {
                            message = "Game Over!";
                            stop = true;
                            mediaPlayer.stop();
                        }
                        for (Sprite sprite2 : sprites) {
                            if (sprite2.getLookup().lookup(Points.class) != null) {
                                sprite2.setY(sprite2.getY() + (stop ? 0 : 20));
                            }
                        }
                        break;
                    }
                }
            }
            if (win) {
                message = "You win!";
                canvas.stop();
                mediaPlayer.stop();
            }
            return true;
        }
    }
    
    class Background extends Layer {        
        
        @Override
        public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillRect(0, 0, width, height);
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.setFont(Font.font("OCR A Std", 20));
            graphicsContext.fillText("SCORE<1>    HI-SCORE    SCORE<2>", 30, 30);
            graphicsContext.fillText("" + score + "            9990   ", 30, 60);
            graphicsContext.fillText(message, 300, 400);
            graphicsContext.fillText("" + 3 + "                   CREDIT " + 1, 30, 680);
            graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillRect(30, 650, 640, 4);
        }
    }
}
