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
package de.eppleton.tileengine.sample.sprites.actions;

import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.State;
import de.eppleton.fx2d.tileengine.TileSet;
import de.eppleton.fx2d.tileengine.action.TileSetAnimation;
import java.util.HashMap;
import net.java.html.canvas.GraphicsContext;

/**
 *
 * @author antonepple
 */
public class StateAnimation extends TileSetAnimation {

    HashMap<State, int[]> state2Indices;

    public StateAnimation(TileSet set, int[] indices, HashMap<State, int[]> state2Indices, float speed) {
        super(set, indices, speed);
        this.state2Indices = state2Indices;
    }

    @Override
    public void render(Sprite sprite, GraphicsContext context, float alpha, long delta) {
        State state = sprite.getState();
        indices = state2Indices.get(state);
        super.render(sprite, context, alpha, delta);
    }

    
}
