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
package de.eppleton.fx2d.physics;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

/**
 *
 * @author antonepple
 */
public class WorldMetrics {

    public static Vec2 max(World world) {
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;

        Body nextBody = world.getBodyList();
        while (nextBody != null) {
            Fixture fixture = nextBody.getFixtureList();
            while (fixture != null) {
                Shape shape = fixture.getShape();
//                if (shape.getType() == ShapeType.CHAIN) {
//
//                    for (int i = 0; i < ((ChainShape) shape).m_vertices.length; i++) {
//                        Vec2 vec2 = ((ChainShape) shape).m_vertices[i];
//                        Vec2 transformed = org.jbox2d.common.Transform.mul(nextBody.m_xf, vec2);
//                        if (transformed.x > maxX) {
//                            maxX = transformed.x;
//                        }
//                        if (transformed.y > maxY) {
//                            maxY = transformed.y;
//                        }
//                    }
//                } else 
                    if (shape.getType() == ShapeType.POLYGON) {
                    for (int i = 0; i < ((PolygonShape) shape).getVertexCount(); i++) {
                        Vec2 vec2 = ((PolygonShape) shape).getVertex(i);
                        Vec2 transformed = org.jbox2d.common.Transform.mul(nextBody.m_xf, vec2);
                        if (transformed.x > maxX) {
                            maxX = transformed.x;
                        }
                        if (transformed.y > maxY) {
                            maxY = transformed.y;
                        }
                    }
                } else if (shape.getType() == ShapeType.CIRCLE) {
                    if ( (nextBody.getPosition().x + ((CircleShape) shape).m_radius) > maxX) {
                        maxX = nextBody.getPosition().x + ((CircleShape) shape).m_radius;
                    }
                    if ( (nextBody.getPosition().y + ((CircleShape) shape).m_radius) > maxY) {
                        maxY = nextBody.getPosition().y + ((CircleShape) shape).m_radius;
                    }
                }
                fixture = fixture.getNext();
            }
            nextBody = nextBody.getNext();
        }
        return new Vec2(maxX, maxY);
    }

    public static Vec2 min(World world) {
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        
        Body nextBody = world.getBodyList();
        while (nextBody != null) {
            Fixture fixture = nextBody.getFixtureList();
            while (fixture != null) {
                Shape shape = fixture.getShape();
//                if (shape.getType() == ShapeType.CHAIN) {
//
//                    for (int i = 0; i < ((ChainShape) shape).m_vertices.length; i++) {
//                        Vec2 vec2 = ((ChainShape) shape).m_vertices[i];
//                        Vec2 transformed = org.jbox2d.common.Transform.mul(nextBody.m_xf, vec2);
//                        if (transformed.x < minX) {
//                            minX = transformed.x;
//                        }
//                        if (transformed.y < minY) {
//                            minY = transformed.y;
//                        }
//                    }
//                } else
                if (shape.getType() == ShapeType.POLYGON) {
                    for (int i = 0; i < ((PolygonShape) shape).getVertexCount(); i++) {
                        Vec2 vec2 = ((PolygonShape) shape).getVertex(i);
                        Vec2 transformed = org.jbox2d.common.Transform.mul(nextBody.m_xf, vec2);
                        if (transformed.x < minX) {
                            minX = transformed.x;
                        }
                        if (transformed.y < minY) {
                            minY = transformed.y;
                        }
                    }
                } else if (shape.getType() == ShapeType.CIRCLE) {
                    if ( (nextBody.getPosition().x - ((CircleShape) shape).m_radius) < minX) {
                        minX = nextBody.getPosition().x - ((CircleShape) shape).m_radius;
                    }
                    if ( (nextBody.getPosition().y - ((CircleShape) shape).m_radius) < minX) {
                        minX = nextBody.getPosition().y - ((CircleShape) shape).m_radius;
                    }
                }
                fixture = fixture.getNext();
            }
            nextBody = nextBody.getNext();
        }
        return new Vec2(minX, minY);
    }
}
