/**
 * This file is part of FXGameEngine A Game Engine written in JavaFX Copyright
 * (C) 2012 Anton Epple <info@eppleton.de>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. Look for COPYING file in the top folder. If not, see
 * http://opensource.org/licenses/GPL-2.0.
 *
 * For alternative licensing or use in closed source projects contact Anton
 * Epple
 * <info@eppleton.de>
 */package de.eppleton.fx2d.samples.pong;

import de.eppleton.fx2d.*;
import de.eppleton.fx2d.action.*;
import de.eppleton.fx2d.event.KeyCode;
import de.eppleton.fx2d.physics.PhysicsEngine;
import de.eppleton.fx2d.physics.action.PhysicsActionFactory;
import java.util.logging.Logger;
import net.java.html.canvas.GraphicsContext;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class Pong extends Level {

    int scorePlayer, scoreComputer = 0;

    public Pong(GraphicsContext graphicsContext, double playfieldWidth, double playfieldHeight, double viewPortWidth, double viewPortHeight) {
        super(graphicsContext, playfieldWidth, playfieldHeight, viewPortWidth, viewPortHeight);
    }

    @Override
    protected void initGame() {
        final PhysicsEngine physicsEngine = new PhysicsEngine(new Vec2(0, -4), new Vec2(0, 6), 100, this, true);

        this.addLayer(new Layer("dummy") {
            @Override
            public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
                graphicsContext.setFont("36 Verdana");
                graphicsContext.fillText("" + scorePlayer + "   " + scoreComputer, 380, 60);
            }
        });
        // convenience methods
        physicsEngine.createWall(0, 580, 800, 10);
        physicsEngine.createWall(0, 10, 800, 10);
        
        // 
        this.addLayer(new de.eppleton.fx2d.physics.DebugLayer(physicsEngine));
        
        
        this.addBehaviour(new Behavior() {
            @Override
            public boolean perform(Level canvas, long nanos) {
                Sprite sprite1 = canvas.getSprite("ball");
                if (sprite1 != null) {
                    Body ball = (Body) sprite1.getUserObject();
                    if (ball.getPosition().x <= 0) {
                        scoreComputer++;
                        physicsEngine.remove(ball);
                        addBall(Pong.this, physicsEngine, 400, 300);
                    } else if (ball.getPosition().x >= 8) {
                        scorePlayer++;
                        physicsEngine.remove(ball);
                        addBall(Pong.this, physicsEngine, 400, 300);
                    }
                }
                return true;
            }
        });
        
        // Our Hero
        Sprite bat = new Sprite(this, "Player", 10, 262, 30, 75);       
        physicsEngine.createDefaultBody(bat, BodyType.KINEMATIC, 1, .4f, 0);
        // movement
        bat.addAction(KeyCode.S, PhysicsActionFactory.createLinearMoveAction(null, "up", new Vec2(0, 4), new Vec2(0, 0)));
        bat.addAction(KeyCode.X, PhysicsActionFactory.createLinearMoveAction(null, "down", new Vec2(0, -4), new Vec2(0, 0)));
        
        // the enemy
        Sprite computer = new Sprite(this, "Computer", 750, 262, 30, 75);
        this.addSprite(computer);
        physicsEngine.createDefaultBody(computer, BodyType.KINEMATIC, 1, .3f, 0);
        
        // Enemy "AI" 
        computer.addBehaviour(new SpriteBehavior() {
            @Override
            public boolean perform(Sprite sprite) {
                Body me = (Body) sprite.getUserObject();
                Sprite ball = sprite.getParent().getSprite("ball");
                if (ball != null) {
                    Body lookup = (Body) ball.getUserObject();
                    me.setLinearVelocity(lookup.getPosition().y < me.getPosition().y
                            ? new Vec2(0, -1.5f) : new Vec2(0, 1.5f));
                }
                return true;
            }
        });
        
        // let the game begin
        addBall(this, physicsEngine, 400, 300);
    }

    private void addBall(Level canvas, PhysicsEngine physicsEngine, int x, int y) {
        Sprite ball = new Sprite(canvas, "ball", x, y, 20, 20);  
        Body ballBody = physicsEngine.createDefaultBody(ball, BodyType.DYNAMIC, 1, .4f, .2f, true);
        ballBody.setLinearVelocity(new Vec2(4, 1));
        canvas.addSprite(ball);
    }
}