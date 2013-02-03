/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samples;

import de.eppleton.fx2d.Layer;
import javafx.scene.canvas.GraphicsContext;


/**
 *
 * @author antonepple
 */
public class SpriteLayer implements Layer{

    @Override
    public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
        
    }

    @Override
    public String getName() {
        return "sprites";
    }

    @Override
    public double getOpacity() {
        return 0;
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setOpacity(double opacity) {
    }

    @Override
    public void setVisible(boolean visible) {
    }

    @Override
    public void pulse() {
    }
    
}
