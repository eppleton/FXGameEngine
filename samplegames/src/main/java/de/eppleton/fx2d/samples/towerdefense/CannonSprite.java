/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samples.towerdefense;

import de.eppleton.fx2d.tileengine.action.RotatingTileSetAnimation;
import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.SpriteBehavior;
import de.eppleton.fx2d.collision.Collision;
import de.eppleton.fx2d.tileengine.action.TileSetAnimation;
import java.util.Collection;
import org.openide.util.Lookup;

/**
 *
 * @author antonepple
 */
public class CannonSprite extends Sprite {

    RotatingTileSetAnimation rotateAnimation;
    private static int bulletCounter = 0;

    public CannonSprite(GameCanvas parent, RotatingTileSetAnimation animation, final TileSetAnimation shoot, String name, final double x, final double y, final int width, final int height) {
        super(parent, animation, name, x, y, width, height, Lookup.EMPTY);
        this.rotateAnimation = animation;
        addBehaviour(new SpriteBehavior() {
            @Override
            public boolean perform(Sprite sprite) {
                double angle = rotateAnimation.getAngle();
                double xVelocity = Math.cos(Math.toRadians(angle));
                double yVelocity = Math.sin(Math.toRadians(angle));
                final double centerX = x + (width / 2);
                final double centerY = y + (height / 2);
                double startX = centerX + (xVelocity * (width / 2)) - 4;
                double startY = centerY + (yVelocity * (height / 2)) - 4;
                Sprite bullet = new Sprite(getParent(), shoot, "bullet" + (bulletCounter++), startX, startY,
                        8, 8, Lookup.EMPTY);
                bullet.setVelocityX(xVelocity);
                bullet.setVelocityY(yVelocity);
                bullet.addBehaviour(new SpriteBehavior() {
                    private double range = 75;

                    @Override
                    public boolean perform(Sprite sprite) {
                        Collection<Collision> checkCollisions = sprite.getParent().checkCollisions(sprite);
                        for (Collision collision : checkCollisions) {
                            if (collision.getSpriteOne() instanceof EnemySprite) {
                                sprite.getParent().removeSprite(sprite);
                                ((EnemySprite) collision.getSpriteOne()).hit(6);
                                return false;
                            } else if (collision.getSpriteTwo() instanceof EnemySprite) {
                                sprite.getParent().removeSprite(sprite);
                                ((EnemySprite) collision.getSpriteTwo()).hit(6);
                                return false;
                            }
                        }
                        if (distance(sprite.getX(), sprite.getY(), centerX, centerY) > range) {
                            sprite.getParent().removeSprite(sprite);
                            return false;
                        }
                        return true;
                    }
                });

                return true;
            }

            @Override
            public long getEvaluationInterval() {
                return 2000000000; //To change body of generated methods, choose Tools | Templates.
            }
        });

        addBehaviour(new SpriteBehavior() {
            @Override
            public boolean perform(Sprite sprite) {
                Sprite closest = null;
                double dist = Double.MAX_VALUE;
                Collection<Sprite> sprites = sprite.getParent().getSprites();
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
                    rotateAnimation.setAngle(Math.toDegrees(Math.atan2(closest.getY() - sprite.getY(), closest.getX() - sprite.getX())));
                }

                return true;
            }
        });
    }

    public double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(
                (x1 - x2) * (x1 - x2)
                + (y1 - y2) * (y1 - y2));
    }
}
