/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.action;

import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.Sprite;

/**
 * Implement this to add timed Behaviors to Sprites. Behaviors range from AI
 * for seeking an enemy, attacking to Animation, etc. Behaviors are a bit
 * like Keyframes. They are called at certain intervals set by the
 * {@link Behavior#setEvaluationInterval(long)} method. At each game pulse
 * the Sprite will check it's Behaviours and invoke the ones where the
 * EvaluationInterval is exceeded. So be aware that the resolution is
 * limited by the Framerate.
 *
 * Behaviors are intended to be stateless and reusable.
 *
 * @author antonepple
 */
public abstract class Behavior {
    private long evaluationInterval = 100_000_000;

    /**
     * implement this to add your custom behavior. 
     * @param sprite
     * @param playingField
     * @return false if the behaviour is finished
     */
    public boolean perform(Sprite sprite, GameCanvas playingField){
        return true;
    }

    /**
     *
     * @return the evaluation interval in nanos
     */
    public long getEvaluationInterval() {
        return evaluationInterval;
    }

    /**
     * Set the EvaluationInterval in Nanos. At each game pulse the Sprite
     * will check it's Behaviours and invoke the ones where the
     * EvaluationInterval is exceeded. The resolution is limited by the
     * Framerate.
     *
     */
    public void setEvaluationInterval(long evaluationInterval) {
        this.evaluationInterval = evaluationInterval;
    }

    /**
     * This method will be called when the Behaviour is added to a Sprite.
     * Override this in order to do something on start.
     *
     * @param sprite
     * @param playingField
     */
    public void onStart(Sprite sprite, GameCanvas playingField) {
    }

    /**
     * This method will be called when the Behaviour is removed from a
     * Sprite. Override this in order to do something on start.
     *
     * @param sprite
     * @param playingField
     */
    public void onFinish(Sprite sprite, GameCanvas playingField) {
    }
    
}
