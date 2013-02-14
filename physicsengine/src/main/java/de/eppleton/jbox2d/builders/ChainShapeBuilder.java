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
