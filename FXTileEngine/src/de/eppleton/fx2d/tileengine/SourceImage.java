/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine;

import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonepple
 */
@XmlRootElement(name = "image")
public class SourceImage {

    // The reference to the tileset image file (Tiled supports most common image formats).
    private String source;
    //  Defines a specific color that is treated as transparent (example value: "FF00FF" for magenta).
    private String trans;
    //  The image width in pixels (optional, used for tile index correction when the image changes)
    private int width;
    // The image height in pixels (optional)
    private int height;
    
    @XmlAttribute
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @XmlAttribute
    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    @XmlAttribute
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @XmlAttribute
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public SourceImage() {
    }
    private static final Logger LOG = Logger.getLogger(SourceImage.class.getName());
}
