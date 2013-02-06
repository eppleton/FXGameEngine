/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.physics.action;

import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.Animation;
import de.eppleton.fx2d.action.SpriteAction;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 *
 * @author antonepple
 */
class LinearMoveAction extends SpriteAction {
    private final Vec2 reset;
    private final Vec2 force;

    public LinearMoveAction(Animation animation, String name, Vec2 onStart, Vec2 onFinish) {
        super(animation, name);
        this.force = onStart;
        this.reset = onFinish;
    }

    @Override
    public void started(Sprite sprite) {
        Body lookup = sprite.getLookup().lookup(Body.class);
        lookup.setLinearVelocity(force);
    }

    @Override
    public void finished(Sprite sprite) {
        Body lookup = sprite.getLookup().lookup(Body.class);
        lookup.setLinearVelocity(reset);
    }
    
}
