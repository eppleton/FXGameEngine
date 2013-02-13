/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.action;

import de.eppleton.fx2d.Sprite;
import javafx.scene.canvas.GraphicsContext;

/**
 * A class used to render a Sprite.
 *
 * @author antonepple
 */
public interface Renderer {

    /**
     * Implement this to render a Sprite. For animations it's suggested to use 
     * the alpha and time value to calculate the progress.
     * @param sprite the Sprite to be rendererd
     * @param context the GraphiscContext used to render
     * @param alpha a value between 0 and 1 allowing to draw interpolated values
     * if framerate is faster than update rate. not supported yet, currently always 1
     * @param time the nano time delivered by the pulse 
     */
    public void render(Sprite sprite, GraphicsContext context, float alpha, long time);
}
