/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.physics.action;

import de.eppleton.fx2d.action.Renderer;
import de.eppleton.fx2d.action.SpriteAction;
import org.jbox2d.common.Vec2;

/**
 *
 * @author antonepple
 */
public class PhysicsActionFactory {
    
    public static SpriteAction createLinearMoveAction( Renderer anim, String name, Vec2 onstart, Vec2 onFinish ){
        return new LinearMoveAction(anim, name, onstart, onFinish);
    }
}
