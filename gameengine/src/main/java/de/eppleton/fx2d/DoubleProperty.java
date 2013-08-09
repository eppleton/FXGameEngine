/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d;

/**
 * This is a replacement for the features of JavaFX Properties I used here.
 * This is required to make the move to the #bck2brwsr Canvas API
 * @author antonepple
 */
public class DoubleProperty {
    
    private Double value;

    public DoubleProperty(Double value) {
        this.value = value;
    }

    public double doubleValue() {
        return value;
    }

    public void set(Double value) {
        this.value = value;
    }
    
    
    
    
    
}
