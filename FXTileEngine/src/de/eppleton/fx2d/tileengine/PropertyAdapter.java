/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author antonepple
 */
public class PropertyAdapter extends XmlAdapter<Properties, java.util.Properties> {

    @Override
    public java.util.Properties unmarshal(Properties v) throws Exception {
        java.util.Properties properties = new java.util.Properties();
        ArrayList<Property> propertiesList = v.getPropertiesList();
        for (Property property : propertiesList) {
            properties.put(property.getName(),property.getValue());
        }
        return properties;
    }

    @Override
    public Properties marshal(java.util.Properties v) throws Exception {
        Properties properties = new Properties();
        ArrayList<Property> propertyList = new ArrayList<>();
        Set<Object> keySet = v.keySet();
        for (Object object : keySet) {
            Property current = new Property();
            current.setName((String)object);
            current.setValue((String)v.getProperty((String)object));
            propertyList.add(current);
        }
        properties.setPropertiesList(propertyList);
        return properties;
    }
    private static final Logger LOG = Logger.getLogger(PropertyAdapter.class.getName());
    
}
