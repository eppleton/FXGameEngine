/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samples;

import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.Action;

/**
 *
 * @author antonepple
 */
public class Ball extends Sprite {

    public Ball(GameCanvas parent, String name, double x, double y, int width, int height) {
        super(parent, name, x, y, width, height);
        addBehaviour(new Action() {
            @Override
            public boolean perform(Sprite sprite, GameCanvas playingField) {
               
                double nextX = sprite.getVelocityX() + sprite.getX();
                double nextY = sprite.getVelocityY() + sprite.getY();
                if (nextX < 0 || nextX + sprite.getWidth() >= playingField.getWidth()) {
                    sprite.setVelocityX(-sprite.getVelocityX());
                }
                if (nextY < 0 || nextY + sprite.getHeight() >= playingField.getHeight()) {
                    sprite.setVelocityY(-sprite.getVelocityY());
                }
                return true;
            }
        });

    }
}
