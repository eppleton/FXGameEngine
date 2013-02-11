/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author antonepple
 */
public class Camera {
    DoubleProperty xProperty;
    DoubleProperty yProperty;

    public Camera(double x, double y) {

        this.xProperty = new SimpleDoubleProperty(x);
        this.yProperty = new SimpleDoubleProperty(y);
    }

    public Camera(DoubleProperty x, DoubleProperty y) {
        this.xProperty = x;
        this.yProperty = y;
    }

    public double getX() {
        return xProperty.doubleValue();
    }

    public double getY() {
        return yProperty.doubleValue();
    }
    
}
