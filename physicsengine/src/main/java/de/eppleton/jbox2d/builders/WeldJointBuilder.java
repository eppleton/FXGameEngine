/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.builders;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.WeldJointDef;

/**
 *
 * @author antonepple
 */
public class WeldJointBuilder extends JointBuilder<WeldJointBuilder, WeldJointDef> {

    public WeldJointBuilder(World world, Body bodyA, Body bodyB, Vec2 anchor) {
        super(world, new WeldJointDef());
        jointDef.initialize(bodyA, bodyB, anchor);
       

    }

    public WeldJointBuilder dampingRatio(float dampingRatio) {
        jointDef.dampingRatio = dampingRatio;
        return this;
    }

    public WeldJointBuilder frequencyHz(float frequencyHz) {
        jointDef.frequencyHz = frequencyHz;
        return this;
    }

    public WeldJointBuilder referenceAngle(float referenceAngle) {
        jointDef.referenceAngle = referenceAngle;
        return this;
    }
}
