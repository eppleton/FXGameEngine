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
package de.eppleton.fx2d.action;

import de.eppleton.fx2d.Level;

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

    public boolean perform(Level canvas, long nanos) {
        return true;
    }
    
}
