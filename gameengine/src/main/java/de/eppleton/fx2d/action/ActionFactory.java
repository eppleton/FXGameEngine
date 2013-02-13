/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.action;

/**
 *
 * @author antonepple
 */
public class ActionFactory {

    /**
     * Helper Method to create a simple MoveAction. 
     * @param anim the Renderer
     * @param name a name
     * @param velocityX the speed in x direction while the action is executed 
     * @param velocityY the speed in y direction while the action is executed 
     * @param x2 the X speed after the action is finished
     * @param y2 the Y speed after the action is finished
     * @return the action
     */
    public static SpriteAction createMoveAction(Renderer anim, String name, double velocityX, double velocityY, double x2, double y2) {
        return new MoveAction(velocityX, velocityY, x2, y2, anim, name);
    }
    
    
}
