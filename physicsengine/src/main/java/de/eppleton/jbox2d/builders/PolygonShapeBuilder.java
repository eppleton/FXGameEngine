/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.builders;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 *
 * @author eppleton
 */
public class PolygonShapeBuilder extends BodyBuilder<PolygonShapeBuilder, PolygonShape> {

    public PolygonShapeBuilder(World world) {
        super(world, new PolygonShape());
    }

    public PolygonShapeBuilder vertices(Vec2[] vertices) {
        shape.set(vertices, vertices.length) ;
        
        return this;
    }
    
    public PolygonShapeBuilder vertices(float... coordinates) {
        Vec2 [] vertices = new Vec2[coordinates.length/2];
        for (int i = 0; i < coordinates.length; i+=2) {
            float x = coordinates[i];
            float y = coordinates[i+1];
            vertices [i/2] = new Vec2(x,y);
        }
        return vertices(vertices) ;
    }
}
