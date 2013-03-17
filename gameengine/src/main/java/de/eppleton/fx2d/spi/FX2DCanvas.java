package de.eppleton.fx2d.spi;

public interface FX2DCanvas {

    public void setHeight(int height);

    public int getHeight() ;
    
    public void setWidth(int width) ;

    public int getWidth() ;

    public FX2DGraphicsContext getContext();

}
