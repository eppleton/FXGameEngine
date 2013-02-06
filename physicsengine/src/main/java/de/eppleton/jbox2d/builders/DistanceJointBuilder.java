/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.builders;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

/**
 *
 * @author antonepple
 */
public class DistanceJointBuilder extends JointBuilder<DistanceJointBuilder, DistanceJointDef> {

    public DistanceJointBuilder(World world, Body a, Body b, Vec2 anchor, Vec2 anchor2) {
        super(world, new DistanceJointDef());
        jointDef.initialize(a, b, anchor, anchor2);

    }

    public DistanceJointBuilder dampingRatio(float dampingRatio) {
        jointDef.dampingRatio = dampingRatio;
        return this;
    }

    public DistanceJointBuilder frequencyHz(float frequencyHz) {
        jointDef.frequencyHz = frequencyHz;
        return this;
    }

    
    public DistanceJointBuilder length(float length) {
        jointDef.length = length;
        return this;
    }
}
