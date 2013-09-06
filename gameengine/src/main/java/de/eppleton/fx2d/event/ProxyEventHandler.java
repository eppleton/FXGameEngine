/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.event;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author antonepple
 */
public class ProxyEventHandler<T extends Event> {
    List <EventHandler<T>> handlers = new ArrayList<>();
    
    public void dispatchEvent(Event event){
        for (EventHandler<T> eventHandler : handlers) {
            eventHandler.handle((T) event);
        }
    }
    
    public void add(EventHandler<T> handler){
        handlers.add(handler);
    }
    
    public void remove(EventHandler<? super T> handler){
        handlers.remove(handler);
    }

   
}
