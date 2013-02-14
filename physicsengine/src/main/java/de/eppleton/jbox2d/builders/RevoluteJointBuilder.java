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
