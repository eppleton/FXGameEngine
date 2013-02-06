/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.builders;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.*;

/**
 *
 * @author eppleton
 */
public class BoxBuilder extends BodyBuilder<BoxBuilder, PolygonShape> {

    float halfWidth;
    float halfHeight;

    public BoxBuilder(World world) {
        super(world, new PolygonShape());
    }
    
    public BoxBuilder halfWidth(float halfWidth){
       this.halfWidth = halfWidth;
       return this;
    }
    
    public BoxBuilder halfHeight(float halfHeight){
       this.halfHeight = halfHeight;
       return this;
    }

    @Override
    public Body build() {
        shape.setAsBox(halfWidth, halfHeight);
        shape.m_count=4;
        return super.build();
    }
    
}
