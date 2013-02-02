/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine;

import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author antonepple
 */
class Polygon {
    @XmlValue
    private String points;
    private static final Logger LOG = Logger.getLogger(Polygon.class.getName());
}
