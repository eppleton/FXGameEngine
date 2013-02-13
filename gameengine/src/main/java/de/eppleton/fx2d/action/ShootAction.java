/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.action;

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
