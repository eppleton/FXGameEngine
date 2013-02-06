/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.tileengine.sample.sprites.actions;

import de.eppleton.fx2d.action.Animation;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.State;
import de.eppleton.fx2d.action.SpriteAction;

/**
 *
 * @author antonepple
 */
public class MoveAction extends SpriteAction {

    double dX = 0;
    double dY = 0;
    State state;
    
    public MoveAction(Animation animation, String name, double dX, double dY, State finalState) {
        super(animation, name);
        this.dX = dX;
        this.dY = dY;
        this.state = finalState;
    }

    @Override
    public void started( Sprite sprite) {
        sprite.setVelocityX(dX);
        sprite.setVelocityY(dY);
        sprite.setState(state);
    }

    @Override
    public void finished(Sprite sprite) {
        if (dX != 0) {
            sprite.setVelocityX(0);
        }
        if (dY != 0) {
            sprite.setVelocityY(0);
        }
        
    }
}
