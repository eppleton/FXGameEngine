/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.tileengine.sample.sprites;

import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.Action;
import de.eppleton.fx2d.action.Animation;
import de.eppleton.fx2d.tileengine.TileSet;
import de.eppleton.fx2d.tileengine.TileSetAnimation;
import java.util.logging.Logger;
import org.openide.util.Lookup;

/**
 *
 * @author antonepple
 */
public class Monster extends Sprite {

    private Animation monsterUp;
    private Animation monsterLeft;
    private Animation monsterDown;
    private Animation monsterRight;

    public Monster(final GameCanvas parent, String name, double x, double y, int width, int height, TileSet monster) {
        super(parent, name, x, y, width, height, Lookup.EMPTY);
        monsterUp = new TileSetAnimation(monster, new int[]{1, 2, 3, 4, 5, 6, 7, 8}, 75f);
        monsterLeft = new TileSetAnimation(monster, new int[]{10, 11, 12, 13, 14, 15, 16, 17}, 75f);
        monsterDown = new TileSetAnimation(monster, new int[]{19, 20, 21, 22, 23, 24, 25, 26}, 75f);
        monsterRight = new TileSetAnimation(monster, new int[]{28, 29, 30, 31, 32, 33, 34, 35}, 75);
        addBehaviour(new ActionImpl(parent));
        addBehaviour(new Action() {
            @Override
            public boolean perform(Sprite sprite, GameCanvas playingField) {
                double x1 = ((Hero) parent.getHero()).getX();
                double y1 = ((Hero) parent.getHero()).getY();
                double x2 = sprite.getX();
                double y2 = sprite.getY();
                double dx = x1 - x2;
                double dy = y1 - y2;

                if (Math.abs(dx) < Math.abs(dy)) {
                    if (dy > 0) {
                        sprite.setVelocityX(0);
                        sprite.setVelocityY(1.5);
                        sprite.setAnimation(monsterDown);
                        //                                            sprite.setDirection(AnimatedSprite.Direction.SOUTH);
                    } else {
                        sprite.setVelocityX(0);
                        sprite.setVelocityY(-1.5);
                        sprite.setAnimation(monsterUp);
                        //                                            sprite.setDirection(AnimatedSprite.Direction.NORTH);
                    }
                } else {
                    if (dx > 0) {
                        sprite.setVelocityX(1.5);
                        sprite.setVelocityY(0);
                        sprite.setAnimation(monsterRight);
                        //                                            sprite.setDirection(AnimatedSprite.Direction.EAST);
                    } else {
                        sprite.setVelocityX(-1.5);
                        sprite.setVelocityY(0);
                        sprite.setAnimation(monsterLeft);
                        //                                            sprite.setDirection(AnimatedSprite.Direction.WEST);
                    }
                }
                return true;
            }
        });

    }
    private static final Logger LOG = Logger.getLogger(Monster.class.getName());

    private static class ActionImpl extends Action {

        private final GameCanvas parent;

        public ActionImpl(GameCanvas parent) {
            this.parent = parent;
        }

        @Override
        public boolean perform(Sprite sprite, GameCanvas playingField) {
            if (sprite.getCollisionBox().intersects(((Hero) parent.getHero()).getCollisionBox())) {
                ((Hero) parent.getHero()).hurt(1);
            }
            return true;
        }
    }
}
