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
