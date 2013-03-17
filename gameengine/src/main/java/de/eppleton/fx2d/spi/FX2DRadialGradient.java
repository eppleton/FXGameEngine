package de.eppleton.fx2d.spi;

public interface FX2DRadialGradient {

    public void addColorStop(double position, String color);

    public Object getNative();
}
