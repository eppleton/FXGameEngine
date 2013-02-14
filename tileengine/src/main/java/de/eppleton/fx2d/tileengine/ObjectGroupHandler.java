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
package de.eppleton.fx2d.tileengine;

import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.tileengine.ObjectGroup;

/**
 * ObjectGroupHandlers take care of ObjectGroups. ObjectGroups can be used for
 * any custom registrations, like spawnpoints for the game character or monsters.
 *
 * You can plugin your own Handlers by calling addObjectGroupHandler() on the
 * PlayingField. The PlayingField will call each Handler after it has been
 * registered. The rest of the PlayingField will be initialized by then.
 *
 * @author antonepple
 */
public interface ObjectGroupHandler {

    /**
     * implement this to plugin your custom functionality. E.g. read the group 
     * information, create sprites from it and add them to the field.
     * 
     * @param group
     * @param field 
     */
    public void handle(ObjectGroup group, GameCanvas field);
}
