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
