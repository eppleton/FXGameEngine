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

import de.eppleton.fx2d.Renderer;

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
