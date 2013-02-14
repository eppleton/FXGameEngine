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
package de.eppleton.tileengine.sample.sprites.actions;

import de.eppleton.fx2d.action.Renderer;
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
    
    public MoveAction(Renderer animation, String name, double dX, double dY, State finalState) {
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
