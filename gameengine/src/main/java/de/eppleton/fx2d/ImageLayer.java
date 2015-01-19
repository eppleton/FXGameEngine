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

import net.java.html.canvas.GraphicsContext2D;
import net.java.html.canvas.Image;

/**
 *
 * @author antonepple
 */
public class ImageLayer extends Layer {

    private Image image;

    public ImageLayer(String name, Image image) {
        super(name);
        this.image = image;
    }

    @Override
    public void draw( double x, double y, double width, double height) {
        graphicsContext.drawImage(image, 0, 0);
    }
}
