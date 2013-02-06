/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.builders;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 *
 * @author eppleton
 */
public class ChainShapeBuilder extends BodyBuilder<ChainShapeBuilder,ChainShape>{
    
    
    public ChainShapeBuilder(World world) {
        super(world, new ChainShape());
    }
    
    public ChainShapeBuilder chain(Vec2[] vertices) {
        shape.createChain(vertices, vertices.length);
        return this;
    }
    
    public ChainShapeBuilder loop(Vec2[] vertices) {
        shape.createLoop(vertices, vertices.length);
        return this;
    }
    
    public ChainShapeBuilder chain(float... coordinates) {
         Vec2 [] vertices = new Vec2[coordinates.length/2];
        for (int i = 0; i < coordinates.length; i+=2) {
            float x = coordinates[i];
            float y = coordinates[i+1];
            vertices [i/2] = new Vec2(x,y);
            
        }
        
        return chain(vertices) ;
    }
    
    public ChainShapeBuilder loop(float... coordinates) {
        Vec2 [] vertices = new Vec2[coordinates.length/2];
        for (int i = 0; i < coordinates.length; i+=2) {
            float x = coordinates[i];
            float y = coordinates[i+1];
            vertices [i/2] = new Vec2(x,y);
        }
        
        return loop(vertices) ;
    }
    
    
    

}
