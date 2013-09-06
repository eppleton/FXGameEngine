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
