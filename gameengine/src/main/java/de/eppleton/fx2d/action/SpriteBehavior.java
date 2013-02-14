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
