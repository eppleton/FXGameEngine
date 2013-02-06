/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
