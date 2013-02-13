/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.action;

import de.eppleton.fx2d.GameCanvas;

/**
 *
 * @author antonepple
 */
public class Behavior {
    protected long evaluationInterval = 100_000_000;

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

    public boolean perform(GameCanvas canvas, long nanos) {
        return true;
    }
    
}
