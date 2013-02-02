/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.tileengine.sample.sprites.actions;

import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.State;
import de.eppleton.fx2d.tileengine.TileSet;
import de.eppleton.fx2d.tileengine.TileSetAnimation;
import java.util.HashMap;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author antonepple
 */
public class StateAnimation extends TileSetAnimation {
    HashMap<State, int[]> state2Indices;
    
    
    public StateAnimation(TileSet set, int[] indices, HashMap<State, int[]>state2Indices ,float speed) {
        super(set, indices, speed);
        this.state2Indices = state2Indices;
    }

    @Override
    public void drawFrame(Sprite sprite, GraphicsContext context) {
        State state = sprite.getState();
        indices = state2Indices.get(state);
        super.drawFrame(sprite, context); //To change body of generated methods, choose Tools | Templates.
    }

   
}
