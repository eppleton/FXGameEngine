/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.builders;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

/**
 *
 * @author eppleton
 */
public abstract class BodyBuilder<K extends BodyBuilder<K, T>, T extends Shape> {

    protected World world;
    protected BodyDef bodyDef;
    protected T shape;
    protected FixtureDef fixtureDef;

    protected BodyBuilder(World world, T shape) {
        this.world = world;
        bodyDef = new BodyDef();
        this.shape = shape;
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
    }
    
    public Body build() {
        Body createdBody = world.createBody(bodyDef);
        createdBody.createFixture(fixtureDef);
        return createdBody;
    }

    public K linearDamping(float linearDamping) {
        bodyDef.linearDamping = linearDamping;
        return (K) this;
    }

    public K angularDamping(float angularDamping) {
        bodyDef.angularDamping = angularDamping;
        return (K) this;
    }

    public K allowSleep(boolean allowSleep) {
        bodyDef.allowSleep = allowSleep;
        return (K) this;
    }
    
//    public K gravityScale(float scale){
//        bodyDef.gravityScale=scale;
//        return (K) this;
//    }

    public K active(boolean active) {
        bodyDef.active = active;
        return (K) this;
    }

    public K angle(float angle) {
        bodyDef.angle = angle;
        return (K) this;
    }

    public K angularVelocity(float angularVelocity) {
        bodyDef.angularVelocity = angularVelocity;
        return (K) this;
    }

    public K awake(boolean awake) {
        bodyDef.awake = awake;
        return (K) this;
    }

    public K bullet(boolean bullet) {
        bodyDef.bullet = bullet;
        return (K) this;
    }

    public K fixedRotation(boolean fixedRotation) {
        bodyDef.fixedRotation = fixedRotation;
        return (K) this;
    }

    public K userData(Object userData) {
        bodyDef.userData = userData;
        return (K) this;
    }

    public K sensor(boolean sensor) {
        fixtureDef.isSensor = sensor;
        return (K) this;
    }

    public K density(float density) {
        fixtureDef.density = density;
        return (K) this;
    }

    public K friction(float friction) {
        fixtureDef.friction = friction;
        return (K) this;
    }

    public K position(Vec2 position) {
        bodyDef.position = position;
        return (K) this;
    }

    public K position(float x, float y) {
        bodyDef.position = new Vec2(x, y);
        return (K) this;
    }

    public K restitution(float restitution) {
        fixtureDef.restitution = restitution;
        return (K) this;
    }

    public K type(BodyType type) {
        bodyDef.type = type;
        return (K) this;
    }
}
