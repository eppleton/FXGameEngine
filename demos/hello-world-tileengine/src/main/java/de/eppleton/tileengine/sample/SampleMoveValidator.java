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
package de.eppleton.tileengine.sample;

import de.eppleton.fx2d.Level.MoveValidator;
import de.eppleton.fx2d.tileengine.Tile;
import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileMapLayer;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;


/**
 *
 * @author antonepple
 */
public class SampleMoveValidator implements MoveValidator {

    TileMap tileMap;
    private double tileWidth;
    private double tileHeight;
    TileMapLayer collision;
    
    public SampleMoveValidator(TileMap map) {
        this.tileMap = map;
        List<TileMapLayer> layers = map.getLayers();
        for (TileMapLayer tileMapLayer : layers) {
            if (tileMapLayer.getName().equals("meta")) {
                collision = tileMapLayer;
            }
        }
        this.tileHeight = tileMap.getTileheight();
        this.tileWidth = tileMap.getTilewidth();
//        for (int i = 0; i < tileHeight; i++) {
//            for (int j = 0; j < tileWidth; j++) {
//                
//                System.out.println("ti "+tileIndex(j, i)+" gid "+collision.getGid(tileIndex(j, i))+" tile "+tileMap.getTile(collision.getGid(tileIndex(j, i))));
//                
//            }
//            
//        }
    }

    @Override
    public boolean isValidMove(double x, double y, double width, double height) {
        int minX = tileXforPosition(x);
        int minY = tileYforPosition(y);
        int maxX = tileXforPosition(x + width);
        int maxY = tileYforPosition(y + height);

        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; j <= maxX; j++) {
                if (isBlocked(tileIndex(j, i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public int tileXforPosition(double x) {
        return (int) (x / tileWidth);
    }

    public int tileYforPosition(double y) {
        return (int) (y / tileHeight);
    }

    public int tileIndex(int x, int y) {
        return x + (y * tileMap.getWidth());
    }

    public int tileIndexForPosition(double x, double y) {
        return tileIndex(tileXforPosition(x), tileYforPosition(y));
    }

    public boolean isBlocked(int tileIndex) {

        if (collision == null) {
            return false;
        }
        Tile tile = tileMap.getTile(collision.getGid(tileIndex));
        if (tile == null) {
            return false;
        }
        Properties properties = tile.getProperties();
        if (properties == null) {
            return false;
        }
        String collidable = tile.getProperties().getProperty("collidable", "false");

        if (collidable.equals("true")) {
            return true;
        }
        String hatRequired = tile.getProperties().getProperty("hatRequired", "false");
        if (hatRequired.equals("true")){
            return !heroHasHat;
        }
        return false;
    }

    private boolean heroHasHat = false;
    private static final Logger LOG = Logger.getLogger(SampleMoveValidator.class.getName());
    
    
}
