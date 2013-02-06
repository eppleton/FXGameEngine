/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author antonepple
 */
public class Layer {

    private String name = "";
    private double opacity;
    private boolean visible = true;

    public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
    }

    public String getName() {
        return name;
    }

    public double getOpacity() {
        return opacity;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
