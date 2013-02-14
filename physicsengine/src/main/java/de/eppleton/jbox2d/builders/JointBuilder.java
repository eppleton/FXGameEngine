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

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;
import org.jbox2d.dynamics.joints.JointType;

/**
 *
 * @author eppleton
 */
public abstract class JointBuilder<K extends JointBuilder<K, T>, T extends JointDef> {

    protected World world;
    protected T jointDef;

    protected JointBuilder(World world, T jointDef) {
        this.world = world;
        this.jointDef = jointDef;
    }

    public K bodyA(Body a) {
        jointDef.bodyA = a;
        return (K) this;
    }

    public K bodyB(Body b) {
        jointDef.bodyB = b;
        return (K) this;
    }

    public K userData(Object userData) {
        jointDef.userData = userData;
        return (K) this;
    }

    public K type(JointType type) {
        jointDef.type = type;
        return (K) this;
    }

    public K collideConnected(boolean coco) {
        jointDef.collideConnected = coco;
        return (K) this;
    }

    public Joint build() {
        return world.createJoint(jointDef);
    }
}
