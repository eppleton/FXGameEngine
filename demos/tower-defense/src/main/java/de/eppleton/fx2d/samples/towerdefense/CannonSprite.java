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
package de.eppleton.fx2d.samples.towerdefense;

import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.SpriteBehavior;
import de.eppleton.fx2d.collision.Collision;
import de.eppleton.fx2d.tileengine.action.TileSetAnimation;
import java.util.Collection;
import java.util.Properties;

/**
 *
 * @author antonepple
 */
public class CannonSprite extends Sprite {

    private static int bulletCounter = 0;
    private int damage = 0;
    private final double centerX;
    private final double centerY;
    private final int width;
    private final int height;
    private final TileSetAnimation shoot;
    private double range = 0;
    private float rate;
    private Sprite closest = null;

    public CannonSprite(Level parent, Properties properties, TileSetAnimation animation, final TileSetAnimation shoot, String name, final double x, final double y, final int width, final int height) {
        super(parent, animation, name, x, y, width, height);

        this.width = width;
        this.height = height;
        this.shoot = shoot;
        centerX = x + (width / 2);
        centerY = y + (height / 2);
        addBehaviour(new FireBehavior());
        addBehaviour(new AimBehavior());
        String rangeProperty = properties.getProperty("range");
        if (rangeProperty != null) {
            range = Integer.parseInt(rangeProperty);
        }
        String damageProperty = properties.getProperty("damage");
        if (damageProperty != null) {
            damage = Integer.parseInt(damageProperty);
        }
        String rateProperty = properties.getProperty("firerate");
        if (rateProperty != null) {
            rate = Float.parseFloat(rateProperty);
        }
        parent.addSprite(this);
    }

    public double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(
                (x1 - x2) * (x1 - x2)
                + (y1 - y2) * (y1 - y2));
    }

    private class FireBehavior extends SpriteBehavior {

        @Override
        public boolean perform(Sprite sprite) {
            if (closest == null) {
                return true;
            }
            double angle = getRotation();
            double xVelocity = Math.cos(Math.toRadians(angle));
            double yVelocity = Math.sin(Math.toRadians(angle));
            double startX = centerX + (xVelocity * (width / 2)) - 4;
            double startY = centerY + (yVelocity * (height / 2)) - 4;
            Sprite bullet = new Sprite(getParent(), shoot, "bullet" + (bulletCounter++), startX, startY,
                    8, 8);
            bullet.setVelocityX(xVelocity);
            bullet.setVelocityY(yVelocity);
            bullet.addBehaviour(new BulletBehavior());
            bullet.getParent().addSprite(bullet);
            return true;
        }

        @Override
        public long getEvaluationInterval() {
            return (long) (2000000000 * rate);
        }

        private class BulletBehavior extends SpriteBehavior {

            @Override
            public boolean perform(Sprite sprite) {
                Collection<Collision> checkCollisions = sprite.getParent().checkCollisions(sprite);
                for (Collision collision : checkCollisions) {
                    if (collision.getSpriteOne() instanceof EnemySprite) {
                        sprite.getParent().removeSprite(sprite);
                        ((EnemySprite) collision.getSpriteOne()).hurt(damage);
                        return false;
                    } else if (collision.getSpriteTwo() instanceof EnemySprite) {
                        sprite.getParent().removeSprite(sprite);
                        ((EnemySprite) collision.getSpriteTwo()).hurt(damage);
                        return false;
                    }
                }
                if (distance(sprite.getX(), sprite.getY(), centerX, centerY) > range) {
                    sprite.getParent().removeSprite(sprite);
                    return false;
                }
                return true;
            }
        }
    }

    private class AimBehavior extends SpriteBehavior {

        @Override
        public boolean perform(Sprite sprite) {
            double dist = Double.MAX_VALUE;
            Collection<Sprite> sprites = sprite.getParent().getSprites();
            closest = null;
            for (Sprite sprite1 : sprites) {
                if (sprite1 instanceof EnemySprite) {
                    double distance = distance(getX(), getY(), sprite1.getX(), sprite1.getY());
                    if (distance < dist) {
                        dist = distance;
                        closest = sprite1;
                    }
                }
            }
            if (closest != null) {
                sprite.setRotation(Math.toDegrees(Math.atan2(closest.getY() - sprite.getY(), closest.getX() - sprite.getX())));
            }
            return true;
        }
    }
}