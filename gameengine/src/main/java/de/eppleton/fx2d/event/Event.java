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

    public Event(Object source, Type type) {
        super(source);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
    
    public static class Type<T extends Event>{
        private final String name;

        public Type(String name) {
            this.name = name;
        }
        
        
    }
    
}
