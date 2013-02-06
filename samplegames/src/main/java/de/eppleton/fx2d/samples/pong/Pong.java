package de.eppleton.fx2d.samples.pong;

import de.eppleton.fx2d.*;
import de.eppleton.fx2d.action.*;
import de.eppleton.fx2d.physics.*;
import de.eppleton.fx2d.physics.action.PhysicsActionFactory;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.openide.util.Lookup;

public class Pong extends Game {

    int scorePlayer, scoreComputer = 0;

    @Override
    protected void initGame() {
        GameCanvas canvas = getCanvas();
        final PhysicsEngine physicsEngine = new PhysicsEngine(new Vec2(0, 0), new Vec2(0, 6), 100, canvas, true);
        canvas.addLayer(new Layer() {
            @Override
            public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
                graphicsContext.setFont(Font.font("Verdana", 36));
                graphicsContext.fillText("" + scorePlayer + "   " + scoreComputer, 380, 60);
            }
        });
        physicsEngine.createWall(0, 580, 800, 10);
        physicsEngine.createWall(0, 10, 800, 10);
        addBall(canvas, physicsEngine, 400, 300);
        canvas.addBehaviour(new Action() {
            @Override
            public boolean perform(Sprite sprite, GameCanvas playingField) {
                Sprite sprite1 = playingField.getSprite("ball");
                if (sprite1 != null) {
                    Body ball = sprite1.getLookup().lookup(Body.class);
                    if (ball.getPosition().x <= 0) {
                        scoreComputer++;
                        physicsEngine.remove(ball);
                        addBall(playingField, physicsEngine, 400, 300);
                    } else if (ball.getPosition().x >= 8) {
                        scorePlayer++;
                        physicsEngine.remove(ball);
                        addBall(playingField, physicsEngine, 400, 300);
                    }
                }
                return true;
            }
        });
        Sprite bat = new Sprite(canvas, "Player", 10, 262, 30, 75, Lookup.EMPTY);
        physicsEngine.createDefaultBody(bat, BodyType.KINEMATIC, 1, .4f, 0);
        bat.addAction(KeyCode.A, PhysicsActionFactory.createLinearMoveAction(null, "up", new Vec2(0, 4), new Vec2(0, 0)));
        bat.addAction(KeyCode.Z, PhysicsActionFactory.createLinearMoveAction(null, "down", new Vec2(0, -4), new Vec2(0, 0)));
        Sprite computer = new Sprite(canvas, "Computer", 750, 262, 30, 75, Lookup.EMPTY);
        physicsEngine.createDefaultBody(computer, BodyType.KINEMATIC, 1, .3f, 0);
        computer.addBehaviour(new Action() {
            @Override
            public boolean perform(Sprite sprite, GameCanvas playingField) {
                Body me = sprite.getLookup().lookup(Body.class);
                Sprite ball = playingField.getSprite("ball");
                if (ball != null) {
                    Body lookup = ball.getLookup().lookup(Body.class);
                    me.setLinearVelocity(lookup.getPosition().y < me.getPosition().y
                            ? new Vec2(0, -1.5f) : new Vec2(0, 1.5f));
                }
                return true;
            }
        });
    }

    private void addBall(GameCanvas canvas, PhysicsEngine physicsEngine, int x, int y) {
        Sprite ball = new Sprite(canvas, "ball", x, y, 20, 20, Lookup.EMPTY);
        Body ballBody = physicsEngine.createDefaultBody(ball, BodyType.DYNAMIC, 1, .4f, .2f, true);
        ballBody.setLinearVelocity(new Vec2(4, 1));
    }

    public static void main(String[] args) {
        launch(args);
    }
}