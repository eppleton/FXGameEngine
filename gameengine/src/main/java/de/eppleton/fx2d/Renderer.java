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

/**
 * A class used to render a Sprite.
 *
 * @author antonepple
 */
public interface Renderer {

    
     public boolean prepare(Sprite sprite, long time);
    /**
     * Implement this to render a Sprite. For animations it's suggested to use 
     * the alpha and time value to calculate the progress.
     * @param sprite the Sprite to be rendererd
     * @param context the GraphiscContext used to render
     * @param alpha a value between 0 and 1 allowing to draw interpolated values
     * if framerate is faster than update rate. not supported yet, currently always 1
     * @param time the nano time delivered by the pulse 
     */
    public void render(Sprite sprite, GraphicsContext2D context, float alpha, long time);
}
