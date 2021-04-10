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
 * Epple <info@eppleton.de>
 */
package de.eppleton.fx2d;

import com.dukescript.api.canvas.GraphicsContext2D;
import de.eppleton.fx2d.beans.DoubleProperty;
import de.eppleton.fx2d.action.SpriteBehavior;
import de.eppleton.fx2d.action.SpriteAction;
import de.eppleton.fx2d.action.State;
import com.dukescript.api.events.EventHandler;
import com.dukescript.api.events.KeyCode;
import com.dukescript.api.events.KeyEvent;
import com.dukescript.api.events.MouseEvent;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
//import org.openide.util.Lookup;
//import org.openide.util.lookup.AbstractLookup;
//import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author antonepple
 */
public class Sprite {

    public static Comparator<Sprite> COMPARATOR = new Comparator<Sprite>() {
        @Override
        public int compare(Sprite o1, Sprite o2) {
            if (o1.getY() > o2.getY()) {
                return 1;
            } else if (o1.getY() == o2.getY()) {
                return 0;
            } else {
                return -1;
            }
        }
    };
    private HashMap<SpriteBehavior, Long> behaviours = new HashMap<SpriteBehavior, Long>();
    private String name;
    private DoubleProperty xProperty;
    private DoubleProperty yProperty;
    private DoubleProperty velocityXProperty;
    private DoubleProperty velocityYProperty;
    private final int width;
    private final int height;
    private double speed = 2;
    private double energy = 1000;
    private State currentState;
    private SpriteAction currentAction;
    private Renderer currentAnimation;
    private Rectangle2D moveBox;
    private Rectangle2D collisionBox;
    private Level parent;
//    private Lookup lookup = Lookup.EMPTY;
    public static Renderer NO_ANIMATION = new Renderer() {
        @Override
        public void render(Sprite sprite, GraphicsContext2D context, float alpha, long delta) {
            context.setFillStyle(context.getWebColor("#ff0000"));
            context.fillRect(0, 0, sprite.getWidth(), sprite.getHeight());
        }

        @Override
        public boolean prepare(Sprite sprite, long time) {
            return false;
        }
    };
    private KeyEventHandler keyEventHandler;
    private static State NO_STATE = new State(NO_ANIMATION, "No State");
//    private InstanceContent content;
    private double angle;
    private EventHandler<MouseEvent> mouseEventHandler;
    private Object userObject;

    public Sprite(Level parent, Renderer animation, String name, double x, double y, int width, int height) {
        this.parent = parent;
        this.currentState = new State(animation, "default");
        this.currentAnimation = animation;
        this.name = name;
        this.xProperty = new DoubleProperty(x);
        this.yProperty = new DoubleProperty(y);
        this.velocityXProperty = new DoubleProperty(0d);
        this.velocityYProperty = new DoubleProperty(0d);
        this.width = width;
        this.height = height;
    }

    public Sprite(Level canvas, String name, double x, double y, int width, int height) {
        this(canvas, NO_ANIMATION, name, x, y, width, height);
    }

    public Object getUserObject() {
        return userObject;
    }

//    public void addToLookup(Object o) {
//        if (lookup == Lookup.EMPTY) {
//            content = new InstanceContent();
//            lookup = new AbstractLookup(content);
//    }
//        content.add(o);
//    }

    public DoubleProperty getXProperty() {
        return xProperty;
    }

    public DoubleProperty getYProperty() {
        return yProperty;
    }

    public Level getParent() {
        return parent;
    }

    public void setParent(Level parent) {
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

    public DoubleProperty getVelocityXProperty() {
        return velocityXProperty;
    }

    public void setVelocityXProperty(DoubleProperty velocityXProperty) {
        this.velocityXProperty = velocityXProperty;
    }

    public DoubleProperty getVelocityYProperty() {
        return velocityYProperty;
    }

    public void setVelocityYProperty(DoubleProperty velocityYProperty) {
        this.velocityYProperty = velocityYProperty;
    }

    public double getVelocityX() {
        return velocityXProperty.doubleValue();
    }

    public void setVelocityX(double velocityX) {
        this.velocityXProperty.set(velocityX);
    }

    public double getVelocityY() {
        return velocityYProperty.doubleValue();
    }

    public void setVelocityY(double velocityY) {
        this.velocityYProperty.set(velocityY);
    }

    /**
     * add a {@link  SpriteBehavior}
     *
     * @param spriteBehaviour
     */
    public void addBehaviour(SpriteBehavior spriteBehaviour) {
        behaviours.put(spriteBehaviour, System.nanoTime());
    }

    /**
     *
     * @return the speed in pixels per pulse
     */
    public double getSpeed() {
        return speed;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
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
    public void die() {
        getParent().removeSprite(this);
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

    public void drawSprite(GraphicsContext2D context, float alpha, long delta) {
        context.save();
        context.translate(getWidth() / 2, getHeight() / 2);
        context.rotate(angle);
        context.translate(-getWidth() / 2, -getHeight() / 2);
        currentAnimation.render(this, context, alpha, delta);
        context.restore();
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

    public final boolean pulse(Level field, long l) {
        boolean dirty = false;
        Set<Map.Entry<SpriteBehavior, Long>> entrySet = behaviours.entrySet();
        for (Map.Entry<SpriteBehavior, Long> entry : entrySet) {
            long evaluationInterval = entry.getKey().getEvaluationInterval();
            long currentTime = System.nanoTime();
            if (currentTime - entry.getValue() > evaluationInterval) {
                SpriteBehavior behavior = entry.getKey();
                if (!behavior.perform(this)) {
                    behaviours.remove(behavior);
                } else {
                    entry.setValue(currentTime);
                }
            }
        }
        if (currentAnimation.prepare(this, l)) {
            dirty = true;
        }
        return dirty;
    }

    public Rectangle2D getMoveBox() {
        if (moveBox == null) {
            moveBox = new Rectangle2D(0, 0, getWidth(), getHeight());
        }
        return moveBox;
    }

    /**
     * Define an area to be used for isCollision detection with background
     * tiles. Typically the area around the feet/legs, so th eupper part of the
     * body can pass in front of blocked tiles.
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
     * @param collisionBox the Rectangle used for isCollision detection with
     * other Sprites.
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

    /**
     * override this to do something more spectacular
     */
    public void remove() {
        getParent().removeSprite(this);
    }

    public void setCenterY(float y) {
        setY(y - (getHeight() / 2));
    }

    public void setCenterX(float x) {
        setX(x - getWidth() / 2);
    }

    public void setRotation(double angle) {
        this.angle = angle;
    }

    public double getRotation() {
        return angle;
    }

    public void invalidMove() {
    }

    boolean contains(double x1, double y1) {
        return (x1 > getX() && x1 < (getX() + getWidth()) && y1 > getY() && y1 < getY() + getHeight());
    }

    public void setOnMouseClicked(EventHandler<MouseEvent> clickHandler) {
        this.mouseEventHandler = clickHandler;
    }

    public EventHandler<MouseEvent> getMouseClickHandler() {
        return mouseEventHandler;
    }

//    public Lookup getLookup() {
//        return lookup;
//    }

    public void setUserObject(Object object) {
        this.userObject = object;
    }


    private class KeyEventHandler implements EventHandler<KeyEvent> {

        HashMap<KeyCode, SpriteAction> actionMap = new HashMap<KeyCode, SpriteAction>();

        @Override
        public void handle(KeyEvent t) {
            if (t.getType() == KeyEvent.KEY_PRESSED) {
                SpriteAction action = actionMap.get(((KeyEvent) t).getKeyCode());
                if (action != null) {
                    setAction(action);
                    action.started(Sprite.this);
                }
            } else if (t.getType() == KeyEvent.KEY_RELEASED) {
                SpriteAction action = actionMap.get(((KeyEvent) t).getKeyCode());
                if (action != null) {
                    if (currentAction == action) {
                        action.finished(Sprite.this);
                        setAnimation(currentState.getAnimation());
                    }
                }
            } else if (t.getType() == KeyEvent.KEY_TYPED) {
                SpriteAction action = actionMap.get(((KeyEvent) t).getKeyCode());
                if (action != null) {
                    setAction(action);
                    action.started(Sprite.this);
                }
            }
        }
    }
}
