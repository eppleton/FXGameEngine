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

/**
 *
 * @author antonepple
 */
import org.jbox2d.common.Vec2;

/**
 *
 * @author antonepple
 */
public class WorldCam {

    private float scale;
    private Vec2 translate;

    public WorldCam(Vec2 translate, float scale) {
        this.scale = scale;
        this.translate = translate;
    }

    public Vec2 worldToScreen(float x, float y) {
        return worldToScreen(new Vec2(x, y));
    }

    public Vec2 worldToScreen(Vec2 world) {
        Vec2 screen = new Vec2();
        screen.x = (world.x + translate.x) * scale;
        screen.y = (-world.y + translate.y) * scale;

        return screen;
    }

    public float scale(float x) {
        return x * scale;
    }

    public float getScale() {
        return scale;
    }

    public float scaleToWorld(float screen) {
        return screen / scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vec2 getTranslate() {
        return translate;
    }

    public void setTranslate(Vec2 translate) {
        this.translate = translate;
    }

    public Vec2 screenToWorld(float x, float y) {
        return screenToWorld(new Vec2(x, y));
    }

    public Vec2 screenToWorld(Vec2 screen) {

        Vec2 worldVec = new Vec2();
        worldVec.x = (screen.x / scale) - translate.x;
        worldVec.y = (-screen.y / scale) + translate.y;
        return worldVec;
    }
}
