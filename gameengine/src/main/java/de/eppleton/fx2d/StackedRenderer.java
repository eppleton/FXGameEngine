/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;

/**
 * A Renderer that allows you to stack renderers on top of each others. 
 * This creates a mini-Layer system. The benefit is that you can easily add more 
 * than one renderer to a sprite at the same time.
 * @author antonepple
 */
public class StackedRenderer implements Renderer {

    List<Renderer> renderers = new ArrayList<>();

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
