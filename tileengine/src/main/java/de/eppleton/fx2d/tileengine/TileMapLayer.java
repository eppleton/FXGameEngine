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
package de.eppleton.fx2d.tileengine;

import de.eppleton.fx2d.Layer;
import java.util.logging.Logger;


/**
 *
 * @author antonepple
 */
public class TileMapLayer extends Layer {
    private Data data;
    private TileMap tileMap;

    public TileMapLayer() {
    }

    public TileMapLayer(String asString) {
       super(asString);
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
   
    public int getGid(int idx) {
        
        return data.getGid(idx);
    }

    @Override
    public void draw(double cameraX, double cameraY, double screenWidth, double screenHeight) {
        // x,y index of first tile to be shown
        int startX = (int) (cameraX / tileMap.getTilewidth());
        int startY = (int) (cameraY / tileMap.getTileheight());

        // the offset in pixels
        int offX = (int) (cameraX % tileMap.getTilewidth());
        int offY = (int) (cameraY % tileMap.getTileheight());

        int screenHeightInTiles = (int) screenHeight / tileMap.getTileheight();
        int screenWidthInTiles = (int) screenWidth / tileMap.getTilewidth();

        double cameraMaxX = (tileMap.getWidth() * tileMap.getTilewidth()) - screenWidth;
        double cameraMaxY = (tileMap.getHeight() * tileMap.getTileheight()) - screenHeight;
       
        for (int y = 0; y < screenHeightInTiles
                // render an extra row to make up for the offset
                + (startY >= (int) (cameraMaxY / tileMap.getTileheight()) ? 1 : 2); y++) {
            for (int x = 0; x < screenWidthInTiles
                    // render an extra row to make up for the offset
                    + (startX >= (int) (cameraMaxX / tileMap.getTilewidth()) ? 1 : 2); x++) {
                int idx = (x + startX) + ((y + startY) * tileMap.getWidth());
                if (idx < data.getMaxGid()){
                int gid = getGid(idx);

                graphicsContext.save();
                graphicsContext.translate((x * tileMap.getTilewidth()) - offX,
                        (y * tileMap.getTileheight()) - offY);
                tileMap.drawTile(graphicsContext, gid);
                graphicsContext.restore();}
            }
        }
    }


    public void afterUnmarshal( Object parent) {
        this.tileMap = (TileMap) parent;
    }
    private static final Logger LOG = Logger.getLogger(TileMapLayer.class.getName());

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }
    
    
}
