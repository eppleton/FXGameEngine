/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine;

import java.util.ArrayList;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author antonepple
 */
class Properties {
    private ArrayList<Property> propertiesList;
    private java.util.Properties properties = new java.util.Properties();
    
    @XmlElement(name="property")
    public ArrayList<Property> getPropertiesList() {
        return propertiesList;
    }

    public void setPropertiesList(ArrayList<Property> propertiesList) {
        this.propertiesList = propertiesList;
    }
    private static final Logger LOG = Logger.getLogger(Properties.class.getName());
     
     
   
}
