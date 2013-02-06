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
class PolyLine {

    @XmlValue
    private String point;
    private static final Logger LOG = Logger.getLogger(PolyLine.class.getName());
}
