/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.builders;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.GearJointDef;
import org.jbox2d.dynamics.joints.Joint;

/**
 *
 * @author antonepple
 */
public class GearJointBuilder extends JointBuilder<GearJointBuilder, GearJointDef> {

    public GearJointBuilder(World world, Body a, Body b, Vec2 anchor, Vec2 axis) {
        super(world, new GearJointDef());
    }

    public GearJointBuilder joint1(Joint joint1) {
        jointDef.joint1 = joint1;
        return this;
    }
    public GearJointBuilder joint2(Joint joint2) {
        jointDef.joint2 = joint2;
        return this;
    }
}
