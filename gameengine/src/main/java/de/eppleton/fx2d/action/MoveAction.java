/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.action;

import de.eppleton.fx2d.Sprite;

/**
 *
 * @author antonepple
 */
public class MoveAction extends SpriteAction {

    double velocityX;
    double velocityY;
    double endX;
    double endY;

    public MoveAction(double velocityX, double velocityY, double endX, double endY, Animation animation, String name) {
        super(animation, name);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public void started(Sprite sprite) {
        if (velocityX != 0) {
            sprite.setVelocityX(velocityX);
        }
        if (velocityY != 0) {
            sprite.setVelocityY(velocityY);
        }

    }

    @Override
    public void finished(Sprite sprite) {
        if (velocityX != 0) {
            sprite.setVelocityX(endX);
        }
        if (velocityY != 0) {
            sprite.setVelocityY(endY);
        }

    }
}
