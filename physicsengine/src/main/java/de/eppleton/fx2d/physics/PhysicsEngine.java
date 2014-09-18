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

import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.Rectangle2D;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.Behavior;
import de.eppleton.jbox2d.builders.BoxBuilder;
import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

/**
 *
 * @author antonepple
 */
public class PhysicsEngine extends Behavior {

    private final World world;
    private long lastPulse;
    private WorldCam camera;

    public WorldCam getCamera() {
        return camera;
    }

    public void setCamera(WorldCam camera) {
        this.camera = camera;
    }
    
    ArrayList<Body> toKill;

    public PhysicsEngine(Vec2 gravity, Vec2 translate, float scale, Level canvas, boolean debug) {
        setEvaluationInterval(1);
        canvas.addBehaviour(this);
        if (debug) canvas.addLayer(new DebugLayer(this));
        camera = new WorldCam(translate, scale);
        toKill = new ArrayList<Body>();
        this.world = new World(gravity) {
            @Override
            public void step(float dt, int velocityIterations, int positionIterations) {
                super.step(dt, velocityIterations, positionIterations);
                ArrayList<Body> toDestroy = new ArrayList<Body>(toKill);
                for (Body body : toDestroy) {
                    destroyBody(body);
                    Object userData = body.getUserData();

                    if (userData instanceof Sprite) {
                        Sprite sprite = (Sprite) userData;
                        sprite.getParent().removeSprite(sprite);
                    }
                }
                toKill.clear();
            }
        };
//        initCamera(world, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public boolean perform(Level canvas, long l) {
        // timestep clamping
        float dt = (float) Math.min(((double) (l - lastPulse)) / 1000000000, 1.0 / 15.0);
        world.step(dt, 8, 2);
        lastPulse = l;
        updateSprites();
        return true;
    }

    public void remove(Body b) {
        toKill.add(b);
    }

    private void updateSprites() {
        Body nextBody = world.getBodyList();
        while (nextBody != null) {
            Sprite sprite = (Sprite) nextBody.getUserData();
            if (sprite != null) {
                Vec2 transformed = camera.worldToScreen(nextBody.getWorldCenter());
                double angle = -Math.toDegrees(nextBody.getAngle());
                sprite.setCenterX(transformed.x);
                sprite.setCenterY(transformed.y);
                sprite.setRotation(angle);
                
            }
            nextBody = nextBody.getNext();
        }
    }

//    private void initCamera(World world, double width, double height) {
//        Vec2 min = WorldMetrics.min(world);
//        Vec2 max = WorldMetrics.max(world);
//        float worldWidth = max.x - min.x;
//        float worldHeight = max.y - min.y;
//        float scaleX = (float) width / worldWidth;
//        float scaleY = (float) height / worldHeight;
//        float scale = scaleX > scaleY ? scaleY : scaleX; // fit screen
//        scale = scale * .8f; // add a little extra space 
//        float centerX = min.x + (worldWidth / 2);
//        float centerY = min.y + (worldHeight / 2);
//        float targetX = ((float) width / 2) / scale;
//        float targetY = ((float) height / 2) / scale;
//        this.camera = new WorldCam(new Vec2(targetX - centerX, max.y + (targetY - centerY)), scale);
//    }
    public Body createDefaultBody(Sprite sprite, BodyType type, float restitution, float friction, float density, boolean fixedRotation) {
        double x = sprite.getX();
        double y = sprite.getY();
        Rectangle2D collisionBox = sprite.getCollisionBox();
        float halfWidth = (float) (collisionBox.getWidth() / 2);
        float halfHeight = (float) (collisionBox.getHeight() / 2);
        float centerX = (float) (x + halfWidth);
        float centerY = (float) (y + halfHeight);

        Body body = new BoxBuilder(world).type(type)
                .halfHeight(camera.scaleToWorld(halfHeight))
                .halfWidth(camera.scaleToWorld(halfWidth))
                .position(camera.screenToWorld(centerX, centerY))
                .restitution(restitution)
                .friction(friction)
                .density(density)
                .fixedRotation(fixedRotation)
                .userData(sprite)
                .build();
        sprite.setUserObject(body);
        return body;
    }

    public void createWall(float x, float y, float width, float height) {
        float halfWidth = width / 2;
        float halfHeight = height / 2;
        float centerX = (float) (x + halfWidth);
        float centerY = (float) (y + halfHeight);

        Body body = new BoxBuilder(world).type(BodyType.STATIC)
                .halfHeight(camera.scaleToWorld(halfHeight))
                .halfWidth(camera.scaleToWorld(halfWidth))
                .position(camera.screenToWorld(centerX, centerY))
                .restitution(1)
                .friction(0f)
                .build();
    }

    World getWorld() {
        return world;
    }

    public Body createDefaultBody(Sprite ball, BodyType bodyType, int i, float f, float f0) {
        return this.createDefaultBody(ball, bodyType, i, f, f0, false);
    }
}
