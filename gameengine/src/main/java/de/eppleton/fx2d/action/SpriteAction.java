/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.action;

import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.Sprite;

/**
 *
 * @author antonepple
 */
public class SpriteAction {

    private Renderer animation;

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
    
    /**
     * This is the real "action" method that is performed.
     * @param aThis
     * @param canvas 
     */
    public void perform(Sprite aThis, GameCanvas canvas){
            // by default do nothing
    }

   
}
