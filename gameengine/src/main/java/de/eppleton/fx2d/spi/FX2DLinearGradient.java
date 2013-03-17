package de.eppleton.fx2d.spi;

public interface FX2DLinearGradient {

    public void addColorStop(double position, String color);

    public Object getNative();

}
