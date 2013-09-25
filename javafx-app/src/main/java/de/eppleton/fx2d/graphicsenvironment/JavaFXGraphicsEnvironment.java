/**
 * This file is part of FXGameEngine 
 * A Game Engine written in JavaFX
 * Copyright (C) 2012 Anton Epple <info@eppleton.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://opensource.org/licenses/GPL-2.0.
 * 
 * For alternative licensing or use in closed source projects contact Anton Epple 
 * <info@eppleton.de>
 */
package de.eppleton.fx2d.graphicsenvironment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import net.java.html.canvas.Dimension;
import net.java.html.canvas.Image;
import net.java.html.canvas.ImageData;
import net.java.html.canvas.Style;
import net.java.html.canvas.spi.GraphicsEnvironment;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author antonepple
 */
@ServiceProvider(service = GraphicsEnvironment.class)
public class JavaFXGraphicsEnvironment implements GraphicsEnvironment {

    GraphicsContext gc;
    Canvas cnvs;

    public JavaFXGraphicsEnvironment() {
        this(new Canvas());
    }

    public JavaFXGraphicsEnvironment(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
        cnvs = canvas;
    }

    @Override
    public void arc(double centerX, double centerY, double startAngle, double radius, double endAngle, boolean ccw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void arcTo(double x1, double y1, double x2, double y2, double r) {
        gc.arcTo(x1, y1, x2, y2, r);
    }

    @Override
    public void beginPath() {
        gc.beginPath();
    }

    @Override
    public void bezierCurveTo(double cp1x, double cp1y, double cp2x, double cp2y, double x, double y) {
        gc.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y);
    }

    @Override
    public void clearRect(double x, double y, double width, double height) {
        gc.clearRect(x, y, width, height);
    }

    @Override
    public void clip() {
        gc.clip();
    }

    @Override
    public void closePath() {
        gc.closePath();
    }

    @Override
    public void fill() {
        gc.fill();
    }

    @Override
    public void fillRect(double x, double y, double width, double height) {
        gc.fillRect(x, y, width, height);
    }

    @Override
    public void fillText(String text, double x, double y) {
        gc.fillText(text, x, y);
    }

    @Override
    public void fillText(String text, double x, double y, double maxWidth) {
        gc.fillText(text, x, y, maxWidth);
    }

    @Override
    public String getFont() {
        return gc.getFont().toString();
    }

    @Override
    public double getGlobalAlpha() {
        return gc.getGlobalAlpha();
    }

    @Override
    public String getGlobalCompositeOperation() {
        return gc.getGlobalBlendMode().name();
    }

    @Override
    public String getLineCap() {
        return gc.getLineCap().name();
    }

    @Override
    public String getLineJoin() {
        return gc.getLineJoin().name();
    }

    @Override
    public double getLineWidth() {
        return gc.getLineWidth();
    }

    @Override
    public double getMiterLimit() {
        return gc.getMiterLimit();
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
    public String getTextAlign() {
        return gc.getTextAlign().name();
    }

    @Override
    public String getTextBaseline() {
        return gc.getTextBaseline().name();
    }

    @Override
    public boolean isPointInPath(double x, double y) {
        return gc.isPointInPath(x, y);
    }

    @Override
    public void lineTo(double x, double y) {
        gc.lineTo(x, y);
    }

    @Override
    public void moveTo(double x, double y) {
        gc.moveTo(x, y);
    }

    @Override
    public void quadraticCurveTo(double cpx, double cpy, double x, double y) {
        gc.quadraticCurveTo(cpx, cpy, x, y);
    }

    @Override
    public void rect(double x, double y, double width, double height) {
        gc.rect(x, y, width, height);
    }

    @Override
    public void restore() {
        gc.restore();
    }

    @Override
    public void rotate(double angle) {
        gc.rotate(angle);
    }

    @Override
    public void save() {
        gc.save();
    }

    @Override
    public void scale(double x, double y) {
        gc.scale(x, y);
    }

    @Override
    public void setFont(String font) {
        gc.setFont(parseFont(font));
    }

    @Override
    public void setGlobalAlpha(double alpha) {
        gc.setGlobalAlpha(alpha);
    }

    @Override
    public void setGlobalCompositeOperation(String operation) {
        gc.setGlobalBlendMode(BlendMode.valueOf(operation));
    }

    @Override
    public void setLineCap(String style) {
        gc.setLineCap(StrokeLineCap.valueOf(style));
    }

    @Override
    public void setLineJoin(String style) {
        gc.setLineJoin(StrokeLineJoin.valueOf(style));
    }

    @Override
    public void setLineWidth(double width) {
        gc.setLineWidth(width);
    }

    @Override
    public void setMiterLimit(double limit) {
        gc.setMiterLimit(limit);
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
    public void setTextAlign(String textAlign) {
        gc.setTextAlign(TextAlignment.valueOf(textAlign));
    }

    @Override
    public void setTextBaseline(String textbaseline) {
        gc.setTextBaseline(VPos.valueOf(textbaseline));
    }

    @Override
    public void setTransform(double a, double b, double c, double d, double e, double f) {
        gc.setTransform(a, b, c, d, e, f);
    }

    @Override
    public void stroke() {
        gc.stroke();
    }

    @Override
    public void strokeRect(double x, double y, double width, double height) {
        gc.strokeRect(x, y, width, height);
    }

    @Override
    public void strokeText(String text, double x, double y) {
        gc.strokeText(text, x, y);
    }

    @Override
    public void strokeText(String text, double x, double y, double maxWidth) {
        gc.strokeText(text, x, y, maxWidth);
    }

    @Override
    public void transform(double a, double b, double c, double d, double e, double f) {
        gc.transform(a, b, c, d, e, f);
    }

    @Override
    public void translate(double x, double y) {
        gc.translate(x, y);
    }

    public Object drawImage(Image image, double x, double y, Object nativeImage) {
        if (nativeImage == null) {
            try {
                nativeImage = new javafx.scene.image.Image(image.getSrc());
            } catch (IllegalArgumentException iax) {
                try {
                    nativeImage = new javafx.scene.image.Image(new FileInputStream(image.getSrc()));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(JavaFXGraphicsEnvironment.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        gc.drawImage((javafx.scene.image.Image) nativeImage, x, y);
        return nativeImage;
    }

    public Object drawImage(Image image, double x, double y, double width, double height, Object nativeImage) {
        if (nativeImage == null) {
             try {
                nativeImage = new javafx.scene.image.Image(image.getSrc());
            } catch (IllegalArgumentException iax) {
                try {
                    nativeImage = new javafx.scene.image.Image(new FileInputStream(image.getSrc()));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(JavaFXGraphicsEnvironment.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        gc.drawImage((javafx.scene.image.Image) nativeImage, x, y, width, height);
        return nativeImage;
    }

    public Object drawImage(Image image, double sx, double sy, double sWidth, double sHeight, double x, double y, double width, double height, Object nativeImage) {
        if (nativeImage == null) {
            try {
                nativeImage = new javafx.scene.image.Image(image.getSrc());
            } catch (IllegalArgumentException iax) {
                try {
                    nativeImage = new javafx.scene.image.Image(new FileInputStream(image.getSrc()));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(JavaFXGraphicsEnvironment.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        gc.drawImage((javafx.scene.image.Image) nativeImage, sx, sy, sWidth, sHeight, x, y, width, height);
        return nativeImage;
    }

    public Object setFillStyle(Style style, Object nativeStyle) {
        if (nativeStyle == null) {
            if (style instanceof Style.LinearGradient) {
                Style.LinearGradient orig = (Style.LinearGradient) style;
                HashMap<Double, String> stops = orig.getStops();
            } else if (style instanceof Style.Color) {
                Style.Color originalColor = (Style.Color) style;
                nativeStyle = Color.web(originalColor.getAsString());
                gc.setFill((Paint) nativeStyle);
            } else if (style instanceof Style.Pattern) {
                Style.Pattern original = (Style.Pattern) style;
                Image imageResource = original.getImageResource();
                javafx.scene.image.Image image = new javafx.scene.image.Image(imageResource.getSrc());
                ImagePattern pattern = new ImagePattern(image, 0, 0, image.getWidth(), image.getHeight(), false);
                gc.setFill(pattern);
            }
        }

        return nativeStyle;
    }

    public Object setStrokeStyle(Style style, Object nativeStyle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ImageData createPixelMap(double x, double y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ImageData createPixelMap(ImageData imageData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ImageData getPixelMap(double x, double y, double width, double height) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void putPixelMap(ImageData imageData, double x, double y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void putPixelMap(ImageData imageData, double x, double y, double dirtyx, double dirtyy, double dirtywidth, double dirtyheight) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getHeight() {
        return (int) cnvs.getHeight();
    }

    public int getWidth() {
        return (int) cnvs.getWidth();
    }

    public void setHeight(int height) {
        cnvs.setHeight(height);
    }

    public void setWidth(int width) {
        cnvs.setWidth(width);
    }

    public Dimension measureText(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Font parseFont(String font) {
        List<String> list = new ArrayList<String>();
        Matcher m = Pattern.compile("([^']\\S*|'.+?')\\s*").matcher(font);
        while (m.find()) {
            list.add(m.group(1)); // Add .replace("\"", "") to remove surrounding quotes.
        }
        return Font.font(list.get(list.size() - 1), Double.parseDouble(list.get(list.size() - 2)));
    }

    public int getWidth(Image image, Object nativeImage) {
        if (nativeImage == null) {
            nativeImage = drawImage(image, 0, 0, nativeImage);
        }
        return (int) ((javafx.scene.image.Image) nativeImage).getWidth();

    }

    public int getHeight(Image image, Object nativeImage) {
        if (nativeImage == null) {
            nativeImage = drawImage(image, 0, 0, nativeImage);
        }
        return (int) ((javafx.scene.image.Image) nativeImage).getHeight();
    }

    public Object mergeImages(Image a, Image b, Object cachedA, Object cachedB) {
        if (cachedA == null) {
            cachedA = drawImage(a, 0, 0, cachedA);
        }
        if (cachedB == null) {
            cachedB = drawImage(b, 0, 0, cachedB);
        }
        return ImageUtilities.merge((javafx.scene.image.Image) cachedA, (javafx.scene.image.Image) cachedB);
    }
}
