/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
