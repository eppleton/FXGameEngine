/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d;

/**
 *
 * @author antonepple
 */
public class Rectangle2D {
    private final double x;
    private final double y;
    private final double width;
    private final double height;

    public Rectangle2D(double x, double y, double width, double height) {
       this.x = x;
       this.y = y;
       this.width = width;
       this.height = height;
    }

    public double getMinX() {
        return x;
    }

    public double getMinY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    boolean isCollision(double x0, double y0, double w0, double h0, double x2, double y2, double w1, double h1) {
        double x1 = x0 + w0;
        double y1 = y0 + h0;

        double x3 = x2 + w1;
        double y3 = y2 + h1;

        return !(x1 < x2 || x3 < x0 || y1 < y2 || y3 < y0);
    }

    public boolean intersects(Rectangle2D collisionBox) {
        return isCollision(x, y, width, height, collisionBox.getMinX(), collisionBox.getMinY(), collisionBox.getWidth(), collisionBox.getHeight());
    }
    
    
    
}
