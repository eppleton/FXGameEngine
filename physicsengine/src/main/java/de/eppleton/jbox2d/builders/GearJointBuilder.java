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
