/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.action;

import de.eppleton.fx2d.Sprite;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author antonepple
 */
public interface Renderer  {

    public void render(Sprite sprite, GraphicsContext context, float alpha, long delta);
    
}
