/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.event;

/**
 *
 * @author antonepple
 */
public interface EventHandler<T extends Event> {
    public void handle (T event);
}
