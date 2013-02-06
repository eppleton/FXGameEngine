/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.builders;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.JointDef;
import org.jbox2d.dynamics.joints.PrismaticJointDef;
import org.jbox2d.dynamics.joints.PulleyJointDef;

/**
 *
 * @author antonepple
 */
public class PulleyJointBuilder extends JointBuilder<PulleyJointBuilder, PulleyJointDef> {

    public PulleyJointBuilder(World world, Body a, Body b, Vec2 ga1, Vec2 ga2,Vec2 anchor1, Vec2 anchor2,float r) {
        super(world, new PulleyJointDef());
        jointDef.initialize(a, b, ga1, ga2, anchor1, anchor2, r);
    }
    
    
}
