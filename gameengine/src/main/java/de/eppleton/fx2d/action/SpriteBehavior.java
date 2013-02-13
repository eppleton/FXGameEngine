/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.action;

import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.Sprite;

/**
 * Implement this to add timed Behaviors to Sprites. Behaviors range from AI for
 * seeking an enemy, attacking to Animation, etc. Behaviors are a bit like
 * Keyframes. They are called at certain intervals set by the
 * {@link SpriteBehavior#setEvaluationInterval(long)} method. At each game pulse
 * the Sprite will check it's Behaviours and invoke the ones where the
 * EvaluationInterval is exceeded. So be aware that the resolution is limited by
 * the Framerate.
 *
 * Behaviors are intended to be stateless and reusable.
 *
 * @author antonepple
 */
public abstract class SpriteBehavior extends Behavior {

    /**
     * implement this to add your custom behavior.
     *
     * @param sprite
     * @return false if the behaviour is finished
     */
    public boolean perform(Sprite sprite) {
        return true;
    }

}
