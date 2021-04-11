/**
 * This file is part of FXGameEngine A Game Engine written in JavaFX Copyright
 * (C) 2012 Anton Epple <info@eppleton.de>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. Look for COPYING file in the top folder. If not, see
 * http://opensource.org/licenses/GPL-2.0.
 *
 * For alternative licensing or use in closed source projects contact Anton
 * Epple
 * <info@eppleton.de>
 */
package de.eppleton.fx2d.tileengine.action;

import com.dukescript.api.canvas.GraphicsContext2D;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.Renderer;
import de.eppleton.fx2d.tileengine.TileSet;
import java.util.Arrays;

/**
 *
 * @author antonepple
 */
public class TileSetAnimation implements Renderer {

    private static int INDEFINITE = -1;
    protected int[] indices;
    final TileSet tileSet;
    private int index;
    private int tile;
    private float frameDelta;
    private long lastFrameSwitch;
    private int repeat = INDEFINITE;
    private AnimationEventHandler finishedHandler;
    private AnimationEventHandler cyclefinishedHandler;
    private AnimationEventHandler startedHandler;
    private boolean playing;
    private boolean started;
    private int repeats;

    public TileSetAnimation(TileSet set, float speed) {
        this(set, null, speed);
    }

    public TileSetAnimation(TileSet set, final int[] indices, float speed) {
        if (indices != null) {
            this.indices = Arrays.copyOf(indices, indices.length);
        } else {
            this.indices = new int[set.getNumTiles()];
            for (int i = 0; i < this.indices.length; i++) {
                this.indices[i] = i;
            }
        }
        for (int i : this.indices) {
            if (i >= set.getNumTiles()) {
                throw new IllegalArgumentException("No tile with index " + i + " in this TileSet");
            }
        }
        this.tileSet = set;
        frameDelta = 1000 / speed;
        play();
    }

    public final int getRepeat() {
        return repeat;
    }

    public final void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public final void setOnFinished(AnimationEventHandler finishedHandler) {
        this.finishedHandler = finishedHandler;
    }

    public final void setOnCyclefinished(AnimationEventHandler cyclefinishedHandler) {
        this.cyclefinishedHandler = cyclefinishedHandler;
    }

    public final void setOnStarted(AnimationEventHandler startedHandler) {
        this.startedHandler = startedHandler;
    }

    final int[] getIndices() {
        return Arrays.copyOf(indices, indices.length);
    }

    public final void play() {
        playing = true;
    }

    public final void pause() {
        playing = false;
    }

    public final void restart() {
        repeats = 0;
        index = 0;
        play();
    }

    @Override
    public boolean prepare(Sprite sprite, long delta) {
        boolean dirty = false;
        if (started && index == 0) {
            if (cyclefinishedHandler != null && (repeats != repeat)) {
                cyclefinishedHandler.handleEvent(new AnimationEvent(sprite, this, AnimationEvent.EventType.CYCLE_FINISHED));
            }
        } else {
            if (startedHandler != null) {
                startedHandler.handleEvent(new AnimationEvent(sprite, this, AnimationEvent.EventType.STARTED));
            }
            started = true;
            dirty = true;
        }
        if (playing && (repeats != repeat) && (delta - lastFrameSwitch) > frameDelta) {
            lastFrameSwitch = delta;
            index++;
            if (indices.length > 0) { // no need to repaint, if it's a single tile
                dirty = true;
            }

            if (index >= indices.length) {
                repeats++;

                index = 0;
                if (repeats == repeat) {
                    if (finishedHandler != null) {
                        finishedHandler.handleEvent(new AnimationEvent(sprite, this, AnimationEvent.EventType.FINISHED));
                    }
                    playing = false;
                }
            }
        }
        tile = indices[index];
        return dirty;
    }

    public TileSet getTileSet() {
        return tileSet;
    }
    
    @Override
    public void render(Sprite sprite, GraphicsContext2D context, float alpha, long delta) {
        tileSet.drawTile(context, tile);
    }
}
