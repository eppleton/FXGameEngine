/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d;

import de.eppleton.fx2d.action.Behavior;
import de.eppleton.fx2d.action.Renderer;
import de.eppleton.fx2d.action.SpriteAction;
import de.eppleton.fx2d.action.State;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author antonepple
 */
public class Sprite {

    private ConcurrentHashMap<Behavior, Long> behaviours = new ConcurrentHashMap<>();
    private String name;
    private DoubleProperty xProperty;
    private DoubleProperty yProperty;
    private double velocityX = 0;
    private double velocityY = 0;
    private final int width;
    private final int height;
    private double speed = 2;
    private double energy = 1_000;
    private State currentState;
    private SpriteAction currentAction;
    private Renderer currentAnimation;
    private Rectangle2D moveBox;
    private Rectangle2D collisionBox;
    private GameCanvas parent;
    private Lookup lookup = Lookup.EMPTY;
    public static Renderer NO_ANIMATION = new Renderer() {
        @Override
        public void render(Sprite sprite, GraphicsContext context, float alpha, long delta) {
            context.setFill(Color.RED);
            context.fillRect(0, 0, sprite.getWidth(), sprite.getHeight());
        }
    };
    private KeyEventHandler keyEventHandler;
    private static State NO_STATE = new State(NO_ANIMATION, "No State");
    private InstanceContent content;

    public Sprite(GameCanvas parent, Renderer animation, String name, double x, double y, int width, int height, Lookup lookup) {
        this.parent = parent;
        this.currentState = new State(animation, "default");
        this.currentAnimation = animation;
        this.name = name;
        this.xProperty = new SimpleDoubleProperty(x);
        this.yProperty = new SimpleDoubleProperty(y);
        this.width = width;
        this.height = height;
        this.lookup = lookup;
        parent.addSprite(this);
    }

    public Sprite(GameCanvas canvas, String name, double x, double y, int width, int height, Lookup lookup) {
        this(canvas, NO_ANIMATION, name, x, y, width, height, lookup);
    }

    
    
    public void addToLookup(Object o) {
        if (lookup == Lookup.EMPTY) {
            content = new InstanceContent();
            lookup = new AbstractLookup(content);
        }
        content.add(o);
    }

    public DoubleProperty getXProperty() {
        return xProperty;
    }


    public DoubleProperty getYProperty() {
        return yProperty;
    }
    
    public GameCanvas getParent() {
        return parent;
    }

    public Lookup getLookup() {
        return lookup;
    }

    public void setParent(GameCanvas parent) {
        this.parent = parent;
    }

    public void addAction(KeyCode code, SpriteAction action) {
        if (keyEventHandler == null) {
            keyEventHandler = new KeyEventHandler();
            parent.addEventHandler(KeyEvent.ANY, keyEventHandler);
        }
        keyEventHandler.actionMap.put(code, action);
    }

    public State getState() {
        return currentState;
    }

    public void setState(State state) {
        currentState = state;
    }

    private void setAction(SpriteAction action) {
        setAnimation(action.getAnimation());
        this.currentAction = action;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    /**
     * add a {@link  Behavior}
     *
     * @param spriteBehaviour
     */
    public void addBehaviour(Behavior spriteBehaviour) {
        behaviours.put(spriteBehaviour, System.nanoTime());
    }

    /**
     *
     * @return the speed in pixels per pulse
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Hurt the Sprite. This will decrease the Sprites Energy by the specified
     * value. Probably too simple if you want to use armor, shields or RPG
     * characters show different vulnerability to different kinds of weapons...
     *
     * @param i
     */
    public void hurt(int i) {
        energy -= i;
        if (energy <= 0) {
            die();
        }
    }

    /**
     * Does nothing very useful yet....
     */
    private void die() {
    }

    public void setAnimation(Renderer animation) {
        if (animation == null) {
            return;
        }
//        if (behaviours.containsKey(currentAnimation)) {
//            behaviours.remove(currentAnimation);
//        }
//        behaviours.put(animation, animation.getEvaluationInterval());
        if (currentState == null) {
            currentState = new State(animation, "default");
        }
        this.currentAnimation = animation;
    }

    public void drawSprite(GraphicsContext context, float alpha, long delta) {
        currentAnimation.render(this, context, alpha, delta);
    }

    /**
     *
     * @return a human readble name
     */
    public String getName() {
        if (name == null) {
            return "";
        }
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     *
     * @return x position of the Sprites upper left corner
     */
    public double getX() {
        return xProperty.doubleValue();
    }

    /**
     *
     * @return y position of the Sprites upper left corner
     */
    public double getY() {
        return yProperty.doubleValue();
    }

    public void pulse(GameCanvas field, long l) {

        Set<Map.Entry<Behavior, Long>> entrySet = behaviours.entrySet();
        for (Map.Entry<Behavior, Long> entry : entrySet) {
            long evaluationInterval = entry.getKey().getEvaluationInterval();
            long currentTime = System.nanoTime();
            if (currentTime - entry.getValue() > evaluationInterval) {
                Behavior behavior = entry.getKey();
                behavior.perform(this, field);
                entry.setValue(currentTime);
            }
        }
    }

    public Rectangle2D getMoveBox() {
        if (moveBox == null) {
            moveBox = new Rectangle2D(0, 0, getWidth(), getHeight());
        }
        return moveBox;
    }

    /**
     * Define an area to be used for isCollision detection with background tiles.
     * Typically the area around the feet/legs, so th eupper part of the body
     * can pass in front of blocked tiles.
     *
     * @param moveBoundingBox
     */
    public void setMoveBox(Rectangle2D moveBoundingBox) {
        this.moveBox = moveBoundingBox;
    }

    /**
     * This defines the part of the body you can interact with. TileSets
     * typically leave a lot of free space araound the body. Use this to check
     * for isCollision with a part of the body instead of the free space around
     * it.
     *
     * @return the Rectangle used for isCollision detection with other Sprites.
     */
    public Rectangle2D getCollisionBox() {
        if (collisionBox == null) {
            collisionBox = new Rectangle2D(0, 0, getWidth(), getHeight());
        }
        return new Rectangle2D(xProperty.doubleValue() + collisionBox.getMinX(), yProperty.doubleValue() + collisionBox.getMinY(), collisionBox.getWidth(), collisionBox.getHeight());
    }

    /**
     * This defines the part of the body you can interact with. TileSets
     * typically leave a lot of free space around the body.
     *
     * @param collisionBox the Rectangle used for isCollision detection with other
     * Sprites.
     */
    public void setCollisionBox(Rectangle2D collisionBox) {
        this.collisionBox = collisionBox;
    }

    /**
     * @param x position of the Sprites upper left corner
     */
    public void setX(double x) {
        this.xProperty.set(x);
    }

    /**
     *
     * @param y position of the Sprites upper left corner
     */
    public void setY(double y) {
        this.yProperty.set(y);
    }

    private class KeyEventHandler implements EventHandler<KeyEvent> {

        HashMap<KeyCode, SpriteAction> actionMap = new HashMap<>();

        @Override
        public void handle(KeyEvent t) {
            if (t.getEventType() == KeyEvent.KEY_PRESSED) {
                SpriteAction action = actionMap.get(((KeyEvent) t).getCode());
                if (action != null) {
                    setAction(action);
                    action.started(Sprite.this);
                }
            } else if (t.getEventType() == KeyEvent.KEY_RELEASED) {
                SpriteAction action = actionMap.get(((KeyEvent) t).getCode());
                if (action != null) {
                    if (currentAction == action) {
                        action.finished(Sprite.this);
                        setAnimation(currentState.getAnimation());
                    }
                }
            } else if (t.getEventType() == KeyEvent.KEY_TYPED) {
                SpriteAction action = actionMap.get(((KeyEvent) t).getCode());
                if (action != null) {
                    setAction(action);
                    action.started(Sprite.this);
                }
            }
        }
    }
}
