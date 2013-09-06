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
package de.eppleton.fx2d;

import java.util.ArrayList;
import java.util.List;
import net.java.html.canvas.GraphicsContext;

/**
 * A Renderer that allows you to stack renderers on top of each others. 
 * This creates a mini-Layer system. The benefit is that you can easily add more 
 * than one renderer to a sprite at the same time, e.g. to decorate a sprite.
 * @author antonepple
 */
public class StackedRenderer implements Renderer {

    List<Renderer> renderers = new ArrayList<>();

    public StackedRenderer(Renderer ... stack) {
        for (Renderer renderer : stack) {
            this.renderers.add(renderer);
        }
    }

    
    
    @Override
    public boolean prepare(Sprite sprite, long time) {
        boolean dirty = false;
        for (Renderer renderer : renderers) {
            renderer.prepare(sprite, time);
        }
        return dirty;
    }

    @Override
    public void render(Sprite sprite, GraphicsContext context, float alpha, long time) {
        for (Renderer renderer : renderers) {
            renderer.render(sprite, context, alpha, time);
        }
    }

    public void removeRenderer(Renderer renderer){
        renderers.remove(renderer);
    }
    
    public final void addRenderer(int position, Renderer renderer) {
        renderers.add(position, renderer);
    }

    public final void addRenderer(Renderer renderer) {
        renderers.add(renderer);
    }
}
