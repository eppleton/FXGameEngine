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
package de.eppleton.fx2d.physics.action;

import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.Renderer;
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

    public LinearMoveAction(Renderer animation, String name, Vec2 onStart, Vec2 onFinish) {
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
