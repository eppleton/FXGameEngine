/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.javafx;

import de.eppleton.fx2d.spi.FX2DRadialGradient;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

/**
 *
 * @author antonepple
 */
class JavaFXRadialGradient implements FX2DRadialGradient {

    RadialGradient delegate;

    public JavaFXRadialGradient(RadialGradient radialGradient) {
        this.delegate = radialGradient;
    }

    @Override
    public void addColorStop(double position, String color) {
        delegate.getStops().add(new Stop(position, Color.valueOf(color)));
    }

    @Override
    public Object getNative() {
        return delegate;
    }
}
