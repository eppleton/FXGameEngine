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

import de.eppleton.fx2d.tileengine.Property;
import java.util.ArrayList;
import java.util.List;
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
        List<Property> propertiesList = v.getPropertiesList();
        for (Property property : propertiesList) {
            properties.put(property.getName(),property.getValue());
        }
        return properties;
    }

    @Override
    public Properties marshal(java.util.Properties v) throws Exception {
        
        Properties properties = new Properties();
            if (v==null)return properties;
        ArrayList<Property> propertyList = new ArrayList<Property>();
        
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
