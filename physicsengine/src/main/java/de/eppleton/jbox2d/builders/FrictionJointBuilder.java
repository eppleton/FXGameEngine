/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.builders;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.FrictionJointDef;

/**
 *
 * @author antonepple
 */
public class FrictionJointBuilder extends JointBuilder<FrictionJointBuilder, FrictionJointDef> {

    public FrictionJointBuilder(World world, Body bodyA, Body bodyB, Vec2 anchor) {
        super(world, new FrictionJointDef());
        jointDef.initialize(bodyA, bodyB, anchor);
        
    }
    
    public FrictionJointBuilder maxForce(float maxForce){
        jointDef.maxForce = maxForce;
        return this;
    }
    
    public FrictionJointBuilder maxTorque (float maxTorque){
        jointDef.maxTorque = maxTorque;
        return this;
    }
   
}
