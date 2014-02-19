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
package de.eppleton.fx2d.samples.test;

import de.eppleton.fx2d.Layer;
import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.SpriteLayer;
import net.java.html.canvas.GraphicsContext;

/**
 *
 * @author antonepple
 */
public class HelloWorldLevel extends Level {

    public HelloWorldLevel(double playfieldWidth, double playfieldHeight, double viewPortWidth, double viewPortHeight) {
        super(playfieldWidth, playfieldHeight, viewPortWidth, viewPortHeight);
    }

    @Override
    protected void initGame() {
        addLayer(new SpriteLayer());

        final Sprite sprite = new Sprite(this, "Test", 100, 100, 42, 42);
        sprite.setVelocityX(1);
        addLayer(new Layer("") {
            int i = 1;

            @Override
            public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
                graphicsContext.setFillStyle(graphicsContext.getWebColor("#ffffff"));
                getGraphicsContext().setFont("italic 40 Calibri");
                getGraphicsContext().fillText("I=" + (i++), 100, 100);
            }
        });
        addSprite(sprite);

    }
}
