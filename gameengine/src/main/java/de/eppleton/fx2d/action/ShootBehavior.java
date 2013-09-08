/**
 * This file is part of FXGameEngine 
 * A Game Engine written in JavaFX
 * Copyright (C) 2012 Anton Epple <info@eppleton.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://opensource.org/licenses/GPL-2.0.
 * 
 * For alternative licensing or use in closed source projects contact Anton Epple 
 * <info@eppleton.de>
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
        this.listeners = new ArrayList<CollisionHandler>();
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
