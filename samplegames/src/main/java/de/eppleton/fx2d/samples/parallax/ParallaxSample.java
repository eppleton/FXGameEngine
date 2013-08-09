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
package de.eppleton.fx2d.samples.parallax;

import de.eppleton.fx2d.Camera;
import de.eppleton.fx2d.Game;
import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.Layer;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.SpriteLayer;
import de.eppleton.fx2d.action.ActionFactory;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.ImagePattern;
import org.openide.util.Lookup;

/**
 *
 * @author antonepple
 */
public class ParallaxSample extends Game {

    @Override
    protected void initGame() {
        GameCanvas canvas = getCanvas();
        canvas.addLayer(
                new ImageLayer(new Image(ParallaxSample.class.getResourceAsStream("/assets/graphics/back.png")), 0.2f, "back"));
        canvas.addLayer(new ImageLayer(new Image(ParallaxSample.class.getResourceAsStream("/assets/graphics/middle.png")), 0.5f, "mid"));
        canvas.addLayer(new SpriteLayer());
        canvas.addLayer(new ImageLayer(new Image(ParallaxSample.class.getResourceAsStream("/assets/graphics/front.png")), 1.1f, "front"));
        Sprite player = new Sprite(canvas, Sprite.NO_ANIMATION, "Player", 1050, 240, 40, 30, Lookup.EMPTY);
        player.addAction(KeyCode.LEFT, ActionFactory.createMoveAction(Sprite.NO_ANIMATION, "left", -4, 0, 0, 0));
        player.addAction(KeyCode.RIGHT, ActionFactory.createMoveAction(Sprite.NO_ANIMATION, "right", 4, 0, 0, 0));
        player.addAction(KeyCode.UP, ActionFactory.createMoveAction(Sprite.NO_ANIMATION, "up", 0, -4, 0, 0));
        player.addAction(KeyCode.DOWN, ActionFactory.createMoveAction(Sprite.NO_ANIMATION, "down", 0, 4, 0, 0));
        canvas.setCamera(new Camera(player.getXProperty(), player.getYProperty()));
        canvas.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class ImageLayer extends Layer {

        Image image;

        public ImageLayer(Image image, float parallaxFactor, String name) {
            this.image = image;
            setName(name);
            setParallaxFactor(parallaxFactor);
        }

        @Override
        public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
            ImagePattern pattern = new ImagePattern(image, x, y, image.getWidth(), image.getHeight(), false);
            graphicsContext.setFill(pattern);
            graphicsContext.fillRect(0, 0, width, height);
            /*
             double startX = x;
             if (startX > 0) {
             startX = -x;
             } else {
             startX = (image.getWidth() - x) % image.getWidth();
             }
             double startY = y;

             graphicsContext.drawImage(image, startX, startY);
             while ((startX + image.getWidth()) < width) {
             startX = startX + image.getWidth();
             graphicsContext.drawImage(image, startX, startY);

             }
             */
        }
    }

    @Override
    protected double getViewPortHeight() {
        return 800;
    }

    @Override
    protected double getPlayfieldWidth() {
        return 5000;
    }
}
