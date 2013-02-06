/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.builders;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

/**
 *
 * @author eppleton
 */
public class RevoluteJointBuilder extends JointBuilder<RevoluteJointBuilder, RevoluteJointDef> {

    public RevoluteJointBuilder(World world, Body a, Body b, Vec2 anchor) {
        super(world, new RevoluteJointDef());
        jointDef.initialize(a, b, anchor);
    }

    public RevoluteJointBuilder enableLimit(boolean enable) {
        jointDef.enableLimit = enable;
        return this;
    }

    public RevoluteJointBuilder enableMotor(boolean motor) {
        jointDef.enableMotor = motor;
        return this;
    }

    public RevoluteJointBuilder localAnchorA(Vec2 localAnchorA) {
        jointDef.localAnchorA = localAnchorA;
        return this;
    }

    public RevoluteJointBuilder localAnchorB(Vec2 localAnchorB) {
        jointDef.localAnchorB = localAnchorB;
        return this;
    }

    public RevoluteJointBuilder lowerAngle(float lowerAngle) {
        jointDef.lowerAngle = lowerAngle;
        return this;
    }

    public RevoluteJointBuilder maxMotorTorque(float maxMotorTorque) {
        jointDef.maxMotorTorque = maxMotorTorque;
        return this;
    }

    public RevoluteJointBuilder motorSpeed(float motorSpeed) {
        jointDef.motorSpeed = motorSpeed;
        return this;
    }

    public RevoluteJointBuilder referenceAngle(float referenceAngle) {
        jointDef.referenceAngle = referenceAngle;
        return this;
    }

    public RevoluteJointBuilder upperAngle(float upperAngle) {
        jointDef.upperAngle = upperAngle;
        return this;
    }
    
}
