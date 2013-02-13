/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.action;

import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.SpriteProvider;

/**
 *
 * @author antonepple
 */
public class SpriteAction {

    private final Renderer animation;
   
    

    public SpriteAction(Renderer animation, String name) {
        this.animation = animation == null? Sprite.NO_ANIMATION:animation;
       
    }

    public Renderer getAnimation() {
        return animation;
    }

    
    public void started( Sprite sprite ){
        // by default do nothing
    }

    public void finished( Sprite aThis ) {
        // by default do nothing
    }
   
}
