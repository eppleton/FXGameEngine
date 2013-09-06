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
package de.eppleton.fx2d.event;

/**
 *
 * @author antonepple
 */
public class KeyEvent extends Event{

    public static final Event.Type<KeyEvent> ANY =
            new Event.Type<KeyEvent>("KEY", Event.ANY);

    public static final Event.Type<KeyEvent> KEY_PRESSED =
            new Event.Type<KeyEvent>("KEY_PRESSED",ANY);

    public static final Event.Type<KeyEvent> KEY_RELEASED =
            new Event.Type<KeyEvent>( "KEY_RELEASED",ANY);

    public static final Event.Type<KeyEvent> KEY_TYPED =
            new Event.Type<KeyEvent>("KEY_TYPED",ANY);

    
    private final KeyCode keyCode;
    public KeyEvent(Object source, Type type, KeyCode keyCode) {
        super(source, type);
        this.keyCode = keyCode;
    }

    public KeyCode getKeyCode() {
        return keyCode;
    }

    
     
   
    
}
