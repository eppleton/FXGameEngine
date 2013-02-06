/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.builders;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.World;

/**
 *
 * @author eppleton
 */
public class CircleShapeBuilder extends BodyBuilder<CircleShapeBuilder,CircleShape>{


    public CircleShapeBuilder(World world) {
       super(world,new CircleShape());
    }

    public CircleShapeBuilder radius(float radius) {
        shape.m_radius = radius;
        return this;
    }
    
}
