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

import de.eppleton.fx2d.Layer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

/**
 *
 * @author antonepple
 */
public class DebugLayer extends Layer {

    private PhysicsEngine engine;

    public DebugLayer(PhysicsEngine engine) {
        this.engine = engine;
        setName("debug");
        setVisible(true);
    }

    @Override
    public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
        WorldCam camera = engine.getCamera();
        World world = engine.getWorld();
        Body nextBody = world.getBodyList();
        final Transform xf = nextBody.getTransform();
        while (nextBody != null) {
            Fixture nextFixture = nextBody.getFixtureList();
            while (nextFixture != null) {
                switch (nextFixture.getType()) {
                    case CIRCLE: {
                        graphicsContext.setFill(Color.RED);
                        CircleShape circle = (CircleShape) nextFixture.getShape();
                        float radius = camera.scale(circle.m_radius);
                        Vec2 worldToScreen = camera.worldToScreen(circle.m_p);
                        graphicsContext.fillOval(worldToScreen.x - radius, worldToScreen.y - radius, radius * 2, radius * 2);
                    }
                    case POLYGON: {
                        PolygonShape polygon = (PolygonShape) nextFixture.getShape();
                        double[] x_coord = new double[polygon.getVertexCount()];
                        double[] y_coord = new double[polygon.getVertexCount()];
                        graphicsContext.setFill(Color.BLUE);

                        for (int i = 0; i < polygon.getVertexCount(); i++) {
                            Vec2 vec2 = polygon.getVertex(i);
                            Vec2 transformed = camera.worldToScreen(org.jbox2d.common.Transform.mul(nextBody.m_xf, vec2));
                            x_coord[i] = (double) transformed.x;
                            y_coord[i] = (double) transformed.y;
                        }
                        graphicsContext.fillPolygon(x_coord, y_coord, polygon.getVertexCount());
                    }
                }
                nextFixture = nextFixture.getNext();
            }
            nextBody = nextBody.getNext();
        }
    }
    
}
