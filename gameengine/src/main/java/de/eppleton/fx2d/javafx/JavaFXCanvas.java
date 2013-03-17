/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.javafx;

import de.eppleton.fx2d.spi.FX2DCanvas;
import de.eppleton.fx2d.spi.FX2DGraphicsContext;
import javafx.scene.canvas.Canvas;

/**
 *
 * @author antonepple
 */
public class JavaFXCanvas implements FX2DCanvas {

    Canvas delegate = new Canvas();
    FX2DGraphicsContext context = new JavaFXGraphicsContext(delegate.getGraphicsContext2D());

    @Override
    public void setHeight(int height) {
        delegate.setHeight(height);
    }

    @Override
    public int getHeight() {
        return (int) delegate.getHeight();
    }

    @Override
    public void setWidth(int width) {
        delegate.setWidth(width);
    }

    @Override
    public int getWidth() {
        return (int) delegate.getWidth();
    }

    @Override
    public FX2DGraphicsContext getContext() {
        return context;
    }
}
