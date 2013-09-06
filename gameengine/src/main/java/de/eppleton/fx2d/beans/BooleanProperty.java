/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.beans;

/**
 *
 * @author antonepple
 */
public class BooleanProperty {
    private boolean value;

    public BooleanProperty(boolean value) {
        this.value = value;
    }
    
    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
    
    
}
