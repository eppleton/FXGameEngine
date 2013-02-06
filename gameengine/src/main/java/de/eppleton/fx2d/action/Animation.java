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
public abstract class Animation extends Action {

    public abstract void drawFrame(Sprite sprite, GraphicsContext context);
    
}
