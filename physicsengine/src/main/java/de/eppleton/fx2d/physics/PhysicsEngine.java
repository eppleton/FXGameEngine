/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.physics;

import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.Sprite;
import de.eppleton.jbox2d.builders.BoxBuilder;
import java.util.ArrayList;
import javafx.geometry.Rectangle2D;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

/**
 *
 * @author antonepple
 */
public class PhysicsEngine implements GameCanvas.Updater {

    private World world;
    private long lastPulse;
    private WorldCam camera;

    public WorldCam getCamera() {
        return camera;
    }

    public void setCamera(WorldCam camera) {
        this.camera = camera;
    }
    ArrayList<Body> toKill;

    public PhysicsEngine(Vec2 gravity, Vec2 translate, float scale, GameCanvas canvas, boolean debug) {
        canvas.setUpdater(this);
        if (debug) canvas.addLayer(new DebugLayer(this));
        camera = new WorldCam(translate, scale);
        toKill = new ArrayList<>();
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
    public void update(GameCanvas canvas, long l) {
        // timestep clamping
        float dt = (float) Math.min(((double) (l - lastPulse)) / 1_000_000_000, 1.0 / 15.0);
        world.step(dt, 8, 2);
        lastPulse = l;
        updateSprites();
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

                sprite.setX(transformed.x);
                sprite.setY(transformed.y);
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
        sprite.addToLookup(body);
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
