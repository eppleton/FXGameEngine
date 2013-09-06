/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
