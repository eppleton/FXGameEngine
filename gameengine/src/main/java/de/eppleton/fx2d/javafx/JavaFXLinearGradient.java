/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.javafx;

import de.eppleton.fx2d.spi.FX2DLinearGradient;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

/**
 *
 * @author antonepple
 */
class JavaFXLinearGradient implements FX2DLinearGradient {

    LinearGradient delegate;

    public JavaFXLinearGradient(LinearGradient linearGradient) {
        delegate = linearGradient;
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
