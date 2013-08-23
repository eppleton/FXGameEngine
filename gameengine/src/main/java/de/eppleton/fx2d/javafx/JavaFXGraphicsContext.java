/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.javafx;

import de.eppleton.fx2d.spi.FX2DGraphicsContext;
import de.eppleton.fx2d.spi.FX2DImage;
import de.eppleton.fx2d.spi.FX2DLinearGradient;
import de.eppleton.fx2d.spi.FX2DPattern;
import de.eppleton.fx2d.spi.FX2DRadialGradient;
import de.eppleton.fx2d.spi.FX2DTextMetrics;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author antonepple
 */
class JavaFXGraphicsContext implements FX2DGraphicsContext {

    GraphicsContext delegate;

    public JavaFXGraphicsContext(GraphicsContext graphicsContext2D) {
        delegate = graphicsContext2D;
    }

    @Override
    public void arc(double centerX, double centerY, double startAngle, double radius, double endAngle, boolean ccw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void arcTo(double x1, double y1, double x2, double y2, double r) {
        delegate.arcTo(x1, y1, x2, y2, r);
    }

    @Override
    public void beginPath() {
        delegate.beginPath();
    }

    @Override
    public void bezierCurveTo(double cp1x, double cp1y, double cp2x, double cp2y, double x, double y) {
        delegate.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y);
    }

    @Override
    public void clearRect(double x, double y, double width, double height) {
        delegate.clearRect(x, y, width, height);
    }

    @Override
    public void clip() {
        delegate.clip();
    }

    @Override
    public void closePath() {
        delegate.closePath();
    }

    @Override
    public FX2DLinearGradient createLinearGradient(double x0, double y0, double x1, double y1) {
        return new JavaFXLinearGradient(new LinearGradient(x0, y0, x1, y1, true, CycleMethod.REPEAT));
    }

    @Override
    public FX2DRadialGradient createRadialGradient(double x0, double y0, double r0, double x1, double y1, double r1) {
        return new JavaFXRadialGradient(new RadialGradient(x0, y0, x1, y1, r1, true, CycleMethod.REPEAT));
    }

    @Override
    public void drawImage(FX2DImage image, double x, double y) {
        delegate.drawImage((Image) image.getNative(), x, y);
    }

    @Override
    public void drawImage(FX2DImage image, double x, double y, double width, double height) {
        delegate.drawImage((Image) image.getNative(), x, y, width, height);
    }

    @Override
    public void drawImage(FX2DImage image, double sx, double sy, double sWidth, double sHeight, double x, double y, double width, double height) {
        delegate.drawImage((Image) image.getNative(), sx, sy, sWidth, sHeight, x, y, width, height);
    }

    @Override
    public void fill() {
        delegate.fill();
    }

    @Override
    public void fillRect(double x, double y, double width, double height) {
        delegate.fillRect(x, y, width, height);
    }

    @Override
    public void fillText(String text, double x, double y) {
        delegate.fillText(text, x, y);
    }

    @Override
    public void fillText(String text, double x, double y, double maxWidth) {
        delegate.fillText(text, x, y, maxWidth);
    }

    @Override
    public String getFillStyle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getFont() {
        return delegate.getFont().toString();
    }

    @Override
    public double getGlobalAlpha() {
        return delegate.getGlobalAlpha();
    }

    @Override
    public String getGlobalCompositeOperation() {
        return delegate.getGlobalBlendMode().name();
    }

    @Override
    public String getLineCap() {
        return delegate.getLineCap().name();
    }

    @Override
    public String getLineJoin() {
        return delegate.getLineJoin().name();
    }

    @Override
    public double getLineWidth() {
        return delegate.getLineWidth();
    }

    @Override
    public double getMiterLimit() {
        return delegate.getMiterLimit();
    }

    @Override
    public double getShadowBlur() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getShadowColor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getShadowOffsetX() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getShadowOffsetY() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getStrokeStyle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTextAlign() {
        return delegate.getTextAlign().name();
    }

    @Override
    public String getTextBaseline() {
        return delegate.getTextBaseline().name();
    }

    @Override
    public boolean isPointInPath(double x, double y) {
        return delegate.isPointInPath(x, y);
    }

    @Override
    public void lineTo(double x, double y) {
        delegate.lineTo(x, y);
    }

    @Override
    public FX2DTextMetrics measureText(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void moveTo(double x, double y) {
        delegate.moveTo(x, y);
    }

    @Override
    public void quadraticCurveTo(double cpx, double cpy, double x, double y) {
        delegate.quadraticCurveTo(cpx, cpy, x, y);
    }

    @Override
    public void rect(double x, double y, double width, double height) {
        delegate.rect(x, y, width, height);
    }

    @Override
    public void restore() {
        delegate.restore();
    }

    @Override
    public void rotate(double angle) {
        delegate.rotate(angle);
    }

    @Override
    public void save() {
        delegate.save();
    }

    @Override
    public void scale(double x, double y) {
        delegate.scale(x, y);
    }

    @Override
    public void setFillStyle(String style) {
        delegate.setFill(Color.valueOf(style));
    }

    @Override
    public void setFillStyle(FX2DLinearGradient style) {
        delegate.setFill((LinearGradient) style.getNative());
    }

    @Override
    public void setFillStyle(FX2DRadialGradient style) {
        delegate.setFill((RadialGradient) style.getNative());
    }

    @Override
    public void setFillStyle(FX2DPattern style) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setFont(String font) {
        delegate.setFont(Font.font(font, 12));
    }

    @Override
    public void setGlobalAlpha(double alpha) {
        delegate.setGlobalAlpha(alpha);
    }

    @Override
    public void setGlobalCompositeOperation(String operation) {
        delegate.setGlobalBlendMode(BlendMode.valueOf(operation));
    }

    @Override
    public void setLineCap(String style) {
        delegate.setLineCap(StrokeLineCap.valueOf(style));
    }

    @Override
    public void setLineJoin(String style) {
        delegate.setLineJoin(StrokeLineJoin.valueOf(style));
    }

    @Override
    public void setLineWidth(double width) {
        delegate.setLineWidth(width);
    }

    @Override
    public void setMiterLimit(double limit) {
        delegate.setMiterLimit(limit);
    }

    @Override
    public void setShadowBlur(double blur) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setShadowColor(String color) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setShadowOffsetX(double x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setShadowOffsetY(double y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setStrokeStyle(String style) {
        delegate.setStroke(Color.valueOf(style));
    }

    @Override
    public void setStrokeStyle(FX2DLinearGradient style) {
        delegate.setStroke((LinearGradient) style.getNative());

    }

    @Override
    public void setStrokeStyle(FX2DRadialGradient style) {
        delegate.setStroke((RadialGradient) style.getNative());
    }

    @Override
    public void setStrokeStyle(FX2DPattern style) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTextAlign(String textAlign) {
        delegate.setTextAlign(TextAlignment.valueOf(textAlign));
    }

    @Override
    public void setTextBaseline(String textbaseline) {
        delegate.setTextBaseline(VPos.valueOf(textbaseline));
    }

    @Override
    public void setTransform(double a, double b, double c, double d, double e, double f) {
        delegate.setTransform(a, b, c, d, e, f);
    }

    @Override
    public void stroke() {
        delegate.stroke();
    }

    @Override
    public void strokeRect(double x, double y, double width, double height) {
        delegate.strokeRect(x, y, width, height);
    }

    @Override
    public void strokeText(String text, double x, double y) {
        delegate.strokeText(text, x, y);
    }

    @Override
    public void strokeText(String text, double x, double y, double maxWidth) {
        delegate.strokeText(text, x, y, maxWidth);
    }

    @Override
    public void transform(double a, double b, double c, double d, double e, double f) {
        delegate.transform(a, b, c, d, e, f);
    }

    @Override
    public void translate(double x, double y) {
        delegate.translate(x, y);
    }

    @Override
    public FX2DPattern createPattern(FX2DImage image, String repeat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
