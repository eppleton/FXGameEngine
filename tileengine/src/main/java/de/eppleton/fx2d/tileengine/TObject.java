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
package de.eppleton.fx2d.tileengine;

import java.util.Properties;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author antonepple
 */
public class TObject {

    private String name;// The name of the object. An arbitrary string.
    private String type ="UNDEFINED";// The type of the object. An arbitrary string.
    private double x;// The x coordinate of the object in pixels.
    private double y;// The y coordinate of the object in pixels.
    private double width;// The width of the object in pixels (defaults to 0).
    private double height;// The height of the object in pixels (defaults to 0).
    private double gid;// An reference to a tile (optional).
    private boolean visible;// Whether the object is shown (1) or hidden (0). Defaults to 1. (will come in 0.9.0)
    private Properties properties;
    private Polygon polygon;
    private PolyLine polyline;
    private SourceImage image;

    @XmlJavaTypeAdapter(PropertyAdapter.class)
    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public PolyLine getPolyline() {
        return polyline;
    }

    public void setPolyline(PolyLine polyline) {
        this.polyline = polyline;
    }

    public SourceImage getImage() {
        return image;
    }

    public void setImage(SourceImage image) {
        this.image = image;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlAttribute
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @XmlAttribute
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @XmlAttribute
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @XmlAttribute
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @XmlAttribute
    public double getGid() {
        return gid;
    }

    public void setGid(double gid) {
        this.gid = gid;
    }

    @XmlAttribute
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    private static final Logger LOG = Logger.getLogger(TObject.class.getName());
}
