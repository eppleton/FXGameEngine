/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.builders;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.PrismaticJointDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

/**
 *
 * @author antonepple
 */
public class PrismaticJointBuilder extends JointBuilder<PrismaticJointBuilder, PrismaticJointDef> {

    public PrismaticJointBuilder(World world, Body a, Body b, Vec2 anchor, Vec2 axis) {
        super(world, new PrismaticJointDef());
        jointDef.initialize(a, b, anchor, axis);
    }

    public PrismaticJointBuilder enableLimit(boolean enable) {
        jointDef.enableLimit = enable;
        return this;
    }

    public PrismaticJointBuilder enableMotor(boolean motor) {
        jointDef.enableMotor = motor;
        return this;
    }

    public PrismaticJointBuilder motorSpeed(float motorSpeed) {
        jointDef.motorSpeed = motorSpeed;
        return this;
    }

    public PrismaticJointBuilder maxMotorForce(float motorForce) {
        jointDef.maxMotorForce = motorForce;
        return this;
    }

    public PrismaticJointBuilder lowerTranslation(float lowerTranslation) {
        jointDef.lowerTranslation = lowerTranslation;
        return this;
    }

    public PrismaticJointBuilder upperTranslation(float upperTranslation) {
        jointDef.upperTranslation = upperTranslation;
        return this;
    }
}
