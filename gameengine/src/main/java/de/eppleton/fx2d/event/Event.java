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

import java.util.EventObject;

/**
 *
 * @author antonepple
 */
public class Event extends EventObject{
    private final Type type;
    public static Type ANY = new Type("Event", null);

    public Event(Object source, Type type) {
        super(source);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
    
    public static class Type<T extends Event>{
        private final String name;
        private final Type superType;

        public Type(String name, Type superType) {
            this.name = name;
            this.superType = superType;
        }

        public String getName() {
            return name;
        }

        public Type getSuperType() {
            return superType;
        }
       
        
        
    }
    
}
