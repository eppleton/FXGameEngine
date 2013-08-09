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
package de.eppleton.fx2d.action;

import de.eppleton.fx2d.Renderer;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.SpriteProvider;
import de.eppleton.fx2d.collision.CollisionHandler;
import javafx.scene.media.AudioClip;

/**
 *
 * @author antonepple
 */
public class ShootAction extends SpriteAction {

    private final SpriteProvider spriteProvider;
    private final AudioClip sound;
    private final CollisionHandler collisionHandler;

    public ShootAction(Renderer animation, String name, SpriteProvider provider, CollisionHandler collisionHandler, AudioClip sound) {
        super(animation, name);
        this.sound = sound;
        this.spriteProvider = provider;
        this.collisionHandler = collisionHandler;
    }

    @Override
    public void started(Sprite sprite) {
        if (sound != null) {
            sound.play();
        }
        Sprite bullet = spriteProvider.getSprite(sprite.getParent(), sprite.getX(), sprite.getY());
        bullet.setVelocityY(-10);
        ShootBehavior behavior = new ShootBehavior(collisionHandler);
        behavior.setEvaluationInterval(20_000_000);
        bullet.addBehaviour(behavior);
    }
}
