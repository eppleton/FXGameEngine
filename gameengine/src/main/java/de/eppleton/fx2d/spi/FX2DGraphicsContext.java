/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.spi;

public interface FX2DGraphicsContext {

    public void arc(double centerX, double centerY, double startAngle, double radius, double endAngle, boolean ccw);

    public void arcTo(double x1, double y1, double x2, double y2, double r);

    public void beginPath();

    public void bezierCurveTo(double cp1x, double cp1y, double cp2x, double cp2y, double x, double y);

    public void clearRect(double x, double y, double width, double height);

    public void clip();

    public void closePath();

    public FX2DLinearGradient createLinearGradient(double x0, double y0, double x1, double y1);

    public FX2DPattern createPattern(FX2DImage image, String repeat);

    public FX2DRadialGradient createRadialGradient(double x0, double y0, double r0, double x1, double y1, double r1);

    public void drawImage(FX2DImage image, double x, double y);

    public void drawImage(FX2DImage image, double x, double y, double width, double height);

    public void drawImage(FX2DImage image, double sx, double sy, double sWidth, double sHeight, double x, double y, double width, double height);

    public void fill();

    public void fillRect(double x, double y, double width, double height);

    public void fillText(String text, double x, double y);

    public void fillText(String text, double x, double y, double maxWidth);

    public String getFillStyle();

    public String getFont();

    public double getGlobalAlpha();

    public String getGlobalCompositeOperation();

    public String getLineCap();

    public String getLineJoin();

    public double getLineWidth();

    public double getMiterLimit();

    public double getShadowBlur();

    public String getShadowColor();

    public double getShadowOffsetX();

    public double getShadowOffsetY();

    public String getStrokeStyle();

    public String getTextAlign();

    public String getTextBaseline();

    public boolean isPointInPath(double x, double y);

    public void lineTo(double x, double y);

    public FX2DTextMetrics measureText(String text);

    public void moveTo(double x, double y);

    public void quadraticCurveTo(double cpx, double cpy, double x, double y);

    public void rect(double x, double y, double width, double height);

    public void restore();

    public void rotate(double angle);

    public void save();

    public void scale(double x, double y);

    public void setFillStyle(String style);

    public void setFillStyle(FX2DLinearGradient style);

    public void setFillStyle(FX2DRadialGradient style);

    public void setFillStyle(FX2DPattern style);

    public void setFont(String font);

    public void setGlobalAlpha(double alpha);

    public void setGlobalCompositeOperation(String operation);

    public void setLineCap(String style);

    public void setLineJoin(String style);

    public void setLineWidth(double width);

    public void setMiterLimit(double limit);

    public void setShadowBlur(double blur);

    public void setShadowColor(String color);

    public void setShadowOffsetX(double x);

    public void setShadowOffsetY(double y);

    public void setStrokeStyle(String style);

    void setStrokeStyle(FX2DLinearGradient style);

    void setStrokeStyle(FX2DRadialGradient style);

    public void setStrokeStyle(FX2DPattern style);

    public void setTextAlign(String textAlign);

    public void setTextBaseline(String textbaseline);

    public void setTransform(double a, double b, double c, double d, double e, double f);

    public void stroke();

    public void strokeRect(double x, double y, double width, double height);

    public void strokeText(String text, double x, double y);

    public void strokeText(String text, double x, double y, double maxWidth);

    public void transform(double a, double b, double c, double d, double e, double f);

    public void translate(double x, double y);
}
