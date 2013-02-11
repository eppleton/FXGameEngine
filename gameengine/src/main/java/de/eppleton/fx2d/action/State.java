/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.action;

/**
 * A State is an Action that never finishes. It's used for example to define
 * the direction
 */
public class State extends SpriteAction {

    public State(Renderer animation, String name) {
        super(animation, name);
    }
    
}
