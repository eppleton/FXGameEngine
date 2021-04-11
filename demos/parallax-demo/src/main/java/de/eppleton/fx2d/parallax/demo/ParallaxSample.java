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
package de.eppleton.fx2d.parallax.demo;

import com.dukescript.api.canvas.Image;
import com.dukescript.api.canvas.Style;
import de.eppleton.fx2d.Camera;
import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.Layer;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.SpriteLayer;
import de.eppleton.fx2d.action.ActionFactory;
import com.dukescript.api.events.KeyCode;
import org.openide.util.lookup.ServiceProvider;


/**
 *
 * @author antonepple
 */
@ServiceProvider(service = Level.class)
public class ParallaxSample extends Level {

    public ParallaxSample() {
        super(600, 800, 600, 800);
    }

    @Override
    protected void initGame() {
  
        this.addLayer(
                new ImageLayer(Image.create(ParallaxSample.class.getResource("/assets/graphics/back.png").toExternalForm()), 0.2f, "back"));
        this.addLayer(new ImageLayer(Image.create(ParallaxSample.class.getResource("/assets/graphics/middle.png").toExternalForm()), 0.5f, "mid"));
        this.addLayer(new SpriteLayer());
        this.addLayer(new ImageLayer( Image.create(ParallaxSample.class.getResource("/assets/graphics/front.png").toExternalForm()), 1.1f, "front"));
        Sprite player = new Sprite(this, Sprite.NO_ANIMATION, "Player", 100, 240, 40, 30);
        player.addAction(KeyCode.A, ActionFactory.createMoveAction(Sprite.NO_ANIMATION, "left", -4, 0, 0, 0));
        player.addAction(KeyCode.D, ActionFactory.createMoveAction(Sprite.NO_ANIMATION, "right", 4, 0, 0, 0));
        player.addAction(KeyCode.S, ActionFactory.createMoveAction(Sprite.NO_ANIMATION, "up", 0, -4, 0, 0));
        player.addAction(KeyCode.X, ActionFactory.createMoveAction(Sprite.NO_ANIMATION, "down", 0, 4, 0, 0));
        this.addSprite(player);
        this.setCamera(new Camera(player.getXProperty(), player.getYProperty()));
    }


    private static class ImageLayer extends Layer {

        Image image;

        public ImageLayer(Image image, float parallaxFactor, String name) {
            super(name);
            this.image = image;
            setParallaxFactor(parallaxFactor);
        }

        @Override
        public void draw( double x, double y, double width, double height) {
            Style pattern = new Style.Pattern(image, "repeat-x");
//            ImagePattern pattern = new ImagePattern(image, x, y, image.getWidth(), image.getHeight(), false);
            graphicsContext.setFillStyle(pattern);
            graphicsContext.fillRect(0, 0, width, height);

        }
    }

}
