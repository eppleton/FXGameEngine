/**
 * This file is part of FXGameEngine A Game Engine written in JavaFX Copyright
 * (C) 2012 Anton Epple <info@eppleton.de>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. Look for COPYING file in the top folder. If not, see
 * http://opensource.org/licenses/GPL-2.0.
 *
 * For alternative licensing or use in closed source projects contact Anton
 * Epple
 * <info@eppleton.de>
 */
package de.eppleton.fx2d;

import com.dukescript.api.canvas.GraphicsContext2D;
import com.dukescript.spi.canvas.GraphicsEnvironment;
import com.dukescript.spi.canvas.GraphicsUtils;
import java.util.ServiceLoader;

import net.java.html.js.JavaScriptBody;

/**
 *
 * @author antonepple
 */
public abstract class Layer {

    private String name;
    private double opacity;
    private boolean visible = true;
    private float parallaxFactor = 1;
    protected final GraphicsContext2D graphicsContext;

    // required for serialization
    public Layer() {
        this("");
    }

    public Layer(String name) {
        this.name = name;
        createCanvas(name);
        graphicsContext = GraphicsContext2D.getOrCreate(name);

    }

    public abstract void draw(double x, double y, double width, double height);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOpacity() {
        return opacity;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public float getParallaxFactor() {
        return parallaxFactor;
    }

    public void setParallaxFactor(float parallaxFactor) {
        this.parallaxFactor = parallaxFactor;
    }

    @JavaScriptBody(args = {"name"}, body
            = "var canvas = document.createElement('canvas');"
            + "canvas.id = name;"
            + "canvas.className += 'layer';"
            + "canvas.width = window.innerWidth;\n"
            + "canvas.height = window.innerHeight;"
            + "var gameDiv = document.getElementById('game-canvas');"
            + "gameDiv.appendChild(canvas);"
//            + "alert('created canvas '+canvas.width+', '+canvas.height);"
    )
    public static native void createCanvas(String name);

}
