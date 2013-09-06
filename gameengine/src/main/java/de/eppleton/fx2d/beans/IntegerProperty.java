/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.beans;

/**
 * This is a replacement for the features of JavaFX Properties I used here.
 * This is required to make the move to the #bck2brwsr Canvas API
 * @author antonepple
 */
public class IntegerProperty {
    
    private int value;

    public IntegerProperty(int value) {
        this.value = value;
    }

    public int integerValue() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ""+value; //To change body of generated methods, choose Tools | Templates.
    }
   
}
