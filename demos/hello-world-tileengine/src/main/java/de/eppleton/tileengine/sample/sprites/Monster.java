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
package de.eppleton.tileengine.sample.sprites;

import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.SpriteBehavior;
import de.eppleton.fx2d.tileengine.TileSet;
import de.eppleton.fx2d.tileengine.action.TileSetAnimation;
import java.util.logging.Logger;
import org.openide.util.Lookup;

/**
 *
 * @author antonepple
 */
public class Monster extends Sprite {

    private TileSetAnimation monsterUp;
    private TileSetAnimation monsterLeft;
    private TileSetAnimation monsterDown;
    private TileSetAnimation monsterRight;

    public Monster(final Level parent, String name, double x, double y, int width, int height, TileSet monster) {
        super(parent, name, x, y, width, height);
        parent.addSprite(this);
        monsterUp = new TileSetAnimation(monster, new int[]{1, 2, 3, 4, 5, 6, 7, 8}, 10f);
        monsterLeft = new TileSetAnimation(monster, new int[]{10, 11, 12, 13, 14, 15, 16, 17}, 10f);
        monsterDown = new TileSetAnimation(monster, new int[]{19, 20, 21, 22, 23, 24, 25, 26}, 10f);
        monsterRight = new TileSetAnimation(monster, new int[]{28, 29, 30, 31, 32, 33, 34, 35}, 10);
        addBehaviour(new ActionImpl(parent));
        addBehaviour(new SpriteBehavior() {
            @Override
            public boolean perform(Sprite sprite) {
                double x1 = ((Hero) parent.getSprite("hero")).getX();
                double y1 = ((Hero) parent.getSprite("hero")).getY();
                double x2 = sprite.getX();
                double y2 = sprite.getY();
                double dx = x1 - x2;
                double dy = y1 - y2;

                if (Math.abs(dx) < Math.abs(dy)) {
                    if (dy > 0) {
                        sprite.setVelocityX(0);
                        sprite.setVelocityY(.8);
                        sprite.setAnimation(monsterDown);
                        //                                            sprite.setDirection(AnimatedSprite.Direction.SOUTH);
                    } else {
                        sprite.setVelocityX(0);
                        sprite.setVelocityY(-.8);
                        sprite.setAnimation(monsterUp);

                        //                                            sprite.setDirection(AnimatedSprite.Direction.NORTH);
                    }
                } else {
                    if (dx > 0) {
                        sprite.setVelocityX(.8);
                        sprite.setVelocityY(0);
                        sprite.setAnimation(monsterRight);

                        //                                            sprite.setDirection(AnimatedSprite.Direction.EAST);
                    } else {
                        sprite.setVelocityX(-.8);
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

    private static class ActionImpl extends SpriteBehavior {

        private final Level parent;

        public ActionImpl(Level parent) {
            this.parent = parent;
        }

        @Override
        public boolean perform(Sprite sprite) {
            if (sprite.getCollisionBox().intersects(((Hero) parent.getSprite("hero")).getCollisionBox())) {
                ((Hero) parent.getSprite("hero")).hurt(1);
            }
            return true;
        }
    }
}
