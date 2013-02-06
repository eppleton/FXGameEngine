/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * ObjectGroup is similar to layer. Tiled uses it as a layer, but technically 
 * it isn't a subclass. Tiled also sorts Layers and ObjectGroups so their sort order
 * reflects the Z-order. This information is lost, when unmarshalling the TMX-file.
 * Since ObjectGroups are meant for custom handling anyway, you can modify them 
 * with a property that reflects their Z-order. One way to do it is to add a layer 
 * with the same name at the position where you want your ObjectGroup or Objects 
 * created from it to be "displayed".
 * 
 * Currently ObjectGroups visibility is ignored and they're not rendered, since 
 * I see no usecase for rendering besides in an editor.Let me know your usecase
 * if you disagree.
 * 
 * ObjectGroups main purpose as I see them is to send the Application Information 
 * for custom Handling, like spawning Sprites. Defining collision areas, Exits, 
 * Doors and Portals, etc.. Use {@link de.eppleton.tileengine.ObjectGroupHandler} to implement your custom
 * Handler.
 * 
 * @author antonepple
 */
public class ObjectGroup {

    private String name;
    private String color;
    private double opacity;
    private boolean visible;
    private ArrayList<TObject> objectLIst;
    private TileMap tileMap;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @XmlAttribute
    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    @XmlAttribute
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @XmlElement(name = "object")
    public ArrayList<TObject> getObjectLIst() {
        return objectLIst;
    }

    public void setObjectLIst(ArrayList<TObject> objectLIst) {
        this.objectLIst = objectLIst;
    }

    private Properties properties;

    @XmlJavaTypeAdapter(PropertyAdapter.class)
    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public TileMap getTileMap() {
        return tileMap;
    }
    
    
     public void afterUnmarshal(Unmarshaller u, Object parent) {
        this.tileMap = (TileMap) parent;
    }
    private static final Logger LOG = Logger.getLogger(ObjectGroup.class.getName());
}