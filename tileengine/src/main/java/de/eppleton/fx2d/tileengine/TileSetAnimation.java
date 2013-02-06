/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine;

import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.Animation;
import java.util.Arrays;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author antonepple
 */
public class TileSetAnimation extends Animation {

    protected int[] indices;
    final float speed;
    final TileSet tileSet;
    private int index;
    private int tile;


    public TileSetAnimation(TileSet set, final int[] indices, float speed) {
        for (int i : indices) {
            if (i >= set.getNumTiles()) {
                throw new IllegalArgumentException("No tile with index " + i + " in this TileSet");
            }
        }
        this.indices = Arrays.copyOf(indices, indices.length);
        this.speed = speed;
        this.tileSet = set;
        setEvaluationInterval((long) (speed * 1_000_000));
    }

    int[] getIndices() {
        return Arrays.copyOf(indices, indices.length);
    }

    @Override
    public void drawFrame(Sprite sprite, GraphicsContext context) {
        tileSet.drawTile(context, tile);
    }

    @Override
    public boolean perform(Sprite sprite, GameCanvas playingField) {
        index++;
        if (index >= indices.length ) {
            index = 0;
        }
        tile = indices[index];
        return true;
    }

    @Override
    public void onFinish(Sprite aThis, GameCanvas field) {
        super.onFinish(aThis, field); //To change body of generated methods, choose Tools | Templates.
        aThis.setState(aThis.getState());
    }
    
    /** 
     * You can decorate an animation in order to play a sound at a certain frame, or invoke some collision 
     * @param index
     * @param handler 
     */
    public void addFrameDecorator(int index, FrameDecorator handler){
    
    }
    
    public static interface FrameDecorator{
        public void decorate(Sprite sprite, GameCanvas playingField);  
    }
    private static final Logger LOG = Logger.getLogger(TileSetAnimation.class.getName());

}
