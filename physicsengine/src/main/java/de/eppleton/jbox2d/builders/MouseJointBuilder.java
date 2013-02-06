/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.builders;

import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.JointDef;
import org.jbox2d.dynamics.joints.MouseJointDef;

/**
 *
 * @author antonepple
 */
public class MouseJointBuilder extends JointBuilder<MouseJointBuilder, MouseJointDef> {

    public MouseJointBuilder(World world) {
        super(world, new MouseJointDef());
    }
    
}
