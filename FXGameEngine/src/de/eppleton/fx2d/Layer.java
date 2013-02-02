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
public interface Layer {

    public abstract void draw(GraphicsContext graphicsContext, double x, double y, double width, double height);

    public abstract String getName();

    public abstract double getOpacity();

    public abstract boolean isVisible();

    public abstract void setName(String name);

    public abstract void setOpacity(double opacity);

    public abstract void setVisible(boolean visible);
    
    public abstract void pulse();
    
    
}
