/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.action;

import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.collision.Collision;
import de.eppleton.fx2d.collision.CollisionHandler;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author antonepple
 */
public class ShootBehavior extends SpriteBehavior {
    Collection<CollisionHandler> listeners;

    public ShootBehavior(CollisionHandler listener) {
        this.listeners = new ArrayList<>();
        listeners.add(listener);
    }
     
    @Override
    public boolean perform(Sprite sprite) {
        if (sprite.getY() <= 0) {
            sprite.getParent().removeSprite(sprite);
            return false;
        }
        Collection<Collision> collisions = sprite.getParent().checkCollisions(sprite);
        for (Collision collision : collisions) {
            for (CollisionHandler handler : listeners) {
                handler.handleCollision(collision);
            }
        }
        return true;
    }
    
    
}
