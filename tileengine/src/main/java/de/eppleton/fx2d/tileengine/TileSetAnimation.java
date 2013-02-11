/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine;

import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.Renderer;
import java.util.Arrays;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author antonepple
 */
public class TileSetAnimation implements Renderer {

    protected int[] indices;
    final TileSet tileSet;
    private int index;
    private int tile;
    private float frameDelta;
    private long lastFrameSwitch;

    public TileSetAnimation(TileSet set, final int[] indices, float speed) {
        for (int i : indices) {
            if (i >= set.getNumTiles()) {
                throw new IllegalArgumentException("No tile with index " + i + " in this TileSet");
            }
        }
        this.indices = Arrays.copyOf(indices, indices.length);
        this.tileSet = set;
        frameDelta = 1_000_000_000 / speed;
        
    }

    int[] getIndices() {
        return Arrays.copyOf(indices, indices.length);
    }

    @Override
    public void render(Sprite sprite, GraphicsContext context, float alpha, long delta) {
        if ((delta - lastFrameSwitch) > frameDelta) {
            lastFrameSwitch = delta;
            index++;
            if (index >= indices.length) {
                index = 0;
            }
        }
        tile = indices[index];
        tileSet.drawTile(context, tile);
    }
}
