/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.physics;

import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.Renderer;
import de.eppleton.fx2d.Sprite;

/**
 *
 * @author antonepple
 */
public class B2DSprite extends Sprite{

    public B2DSprite(Level parent, Renderer animation, String name, double x, double y, int width, int height) {
        super(parent, animation, name, x, y, width, height);
    }
    
}
