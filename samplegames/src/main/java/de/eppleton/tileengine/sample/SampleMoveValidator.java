/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.tileengine.sample;

import de.eppleton.fx2d.GameCanvas.MoveValidator;
import de.eppleton.fx2d.tileengine.Tile;
import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileMapLayer;
import java.util.ArrayList;
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
        ArrayList<TileMapLayer> layers = map.getLayers();
        for (TileMapLayer tileMapLayer : layers) {
            if (tileMapLayer.getName().equals("meta")) {
                collision = tileMapLayer;
            }
        }
        this.tileHeight = tileMap.getTileheight();
        this.tileWidth = tileMap.getTilewidth();
       
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
