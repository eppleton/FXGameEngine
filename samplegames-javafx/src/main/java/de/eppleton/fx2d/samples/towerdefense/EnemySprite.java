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
import de.eppleton.fx2d.beans.IntegerProperty;
import de.eppleton.fx2d.Rectangle2D;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.Renderer;
import de.eppleton.fx2d.action.SpriteBehavior;
import de.eppleton.fx2d.tileengine.TileMapException;
import de.eppleton.fx2d.tileengine.TileMapReader;
import de.eppleton.fx2d.tileengine.TileSet;
import de.eppleton.fx2d.tileengine.action.AnimationEvent;
import de.eppleton.fx2d.tileengine.action.AnimationEventHandler;
import de.eppleton.fx2d.tileengine.action.TileSetAnimation;
import de.eppleton.fx2d.tileengine.algorithms.AStar;
import java.util.Properties;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author antonepple
 */
public class EnemySprite extends Sprite {

    private double maxHealth = 50;
    private int killPoints = 50;
    private IntegerProperty score;
    private IntegerProperty hits;
    private boolean reachedTarget = false; 
    static TileSet explosion;

//    static {
//        try {
//            explosion = TileMapReader.readSet("/de/eppleton/fx2d/towerdefense/explosion.tsx");
//        } catch (TileMapException ex) {
//            Logger.getLogger(EnemySprite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//    }
    private TileSetAnimation explosionAnimation;

    public EnemySprite(Level parent, IntegerProperty score,IntegerProperty hits, Properties properties, Renderer animation, String name, double x, double y, final int width, final int height) {
        super(parent, animation, name, x, y, width, height);
        this.score = score;
        this.hits = hits;
        setCollisionBox(new Rectangle2D(10, 10, 26, 26));
        setEnergy(maxHealth);
        explosionAnimation = new TileSetAnimation(explosion, 100f);
        explosionAnimation.setRepeat(1);
        explosionAnimation.setOnFinished(new AnimationEventHandler() {
            @Override
            public void handleEvent(AnimationEvent event) {
                Sprite target = event.getTarget();
                target.getParent().removeSprite(target);
                getParent().removeSprite(EnemySprite.this);
            }
        });


    }

    public void setAttackPath(final  AStar.PathNode attackPath) {
        addBehaviour(new SpriteBehavior() {
            AStar.PathNode start = attackPath;

            @Override
            public boolean perform(Sprite sprite) {
                double x = sprite.getX();
                double y = sprite.getY();
                double pathX = start.getX() * getWidth();
                double pathY = start.getY() * getHeight();

                if (Math.abs(pathX - x) < 2 && Math.abs(pathY - y) < 2) {
                    start = start.getParent();
                    if (start == null) {
                        reachedTarget = true;
                        sprite.die();
                        return false;
                    }
                }
                if (pathX - x > 1) {
                    sprite.setVelocityX(.5);
                } else if (pathX - x < -1) {
                    sprite.setVelocityX(-.5);
                } else {
                    sprite.setVelocityX(0);
                }
                if (pathY - y > 1) {
                    sprite.setVelocityY(.5);
                } else if (pathY - y < -1) {
                    sprite.setVelocityY(-.5);
                } else {
                    sprite.setVelocityY(0);
                }
                setRotation(Math.toDegrees(Math.atan2(sprite.getVelocityY(), sprite.getVelocityX())));

                return true;
            }
        });

    }

    @Override
    public void die() {
        super.die(); //To change body of generated methods, choose Tools | Templates.
        getParent().addSprite(new Sprite(getParent(), explosionAnimation, "explosion", getX() - 30, getY() - 80, 128, 128));
        if (! reachedTarget)score.set(score.integerValue() + killPoints);
        else hits.set(hits.integerValue()+1);
    }

    public double getMaxHealth() {
        return maxHealth;
    }
}
