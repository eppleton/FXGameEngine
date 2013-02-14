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

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author antonepple
 */
@XmlRootElement(name = "map")
public class TileMap {
    //The TMX format version, generally 1.0.

    private String version;
    //Map orientation. Tiled supports "orthogonal" and "isometric" at the moment.
    private String orientation;
    // The map width in tiles.
    private Integer width;
    // The map height in tiles.
    private Integer height;
    // The width of a tile.
    private int tilewidth;
    // The width of a tile.
    private int tileheight;
    private ArrayList<TileSet> tilesets;
    private ArrayList<TileMapLayer> layers;
    private ArrayList<ObjectGroup> objectGroups;
    private Properties properties;

    /**
     *
     * @return
     */
    @XmlElement(name = "tileset")
    public ArrayList<TileSet> getTileSetlist() {
        return tilesets;
    }
    
    
    public int getWidthInPixels(){
        return width*tilewidth;
    }
    
    public int getHeighInPixels(){
        return height*tileheight;
    }
    
    /** 
     * 
     * @param name
     * @return the TileSet with the given name, null if not available
     */
    public TileSet getTileSet(String name){
        for (TileSet tileSet : tilesets) {
            if (tileSet.getName().equals(name)) {
                return tileSet;
            }
        }
        return null;
    }

    /**
     *
     * @param tilesets
     */
    public void setTileSetlist(ArrayList<TileSet> tilesets) {
        this.tilesets = tilesets;
    }

    /**
     *
     * @return
     */
    @XmlElement(name = "layer")
    public ArrayList<TileMapLayer> getLayers() {
        return layers;
    }

    /**
     *
     * @param layers
     */
    public void setLayers(ArrayList<TileMapLayer> layers) {
        this.layers = layers;
    }

    /**
     *
     * @return
     */
    @XmlElement(name = "objectgroup")
    public ArrayList<ObjectGroup> getObjectGroups() {
        return objectGroups;
    }

    /**
     *
     * @param objectGroups
     */
    public void setObjectGroups(ArrayList<ObjectGroup> objectGroups) {
        this.objectGroups = objectGroups;
    }

    /**
     *
     * @return
     */
    @XmlJavaTypeAdapter(PropertyAdapter.class)
    public Properties getProperties() {
        return properties;
    }

    /**
     *
     * @param properties
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     *
     * @return
     */
    @XmlAttribute
    public String getVersion() {
        return version;
    }

    /**
     *
     * @param version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     *
     * @return
     */
    @XmlAttribute
    public String getOrientation() {
        return orientation;
    }

    /**
     *
     * @param orientation
     */
    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    /**
     *
     * @return
     */
    @XmlAttribute
    public Integer getWidth() {
        return width;
    }

    /**
     *
     * @param width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     *
     * @return
     */
    @XmlAttribute
    public Integer getHeight() {
        return height;
    }

    /**
     *
     * @param height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     *
     * @return
     */
    @XmlAttribute
    public int getTilewidth() {
        return tilewidth;
    }

    /**
     *
     * @param tilewidth
     */
    public void setTilewidth(int tilewidth) {
        this.tilewidth = tilewidth;
    }

    /**
     *
     * @return
     */
    @XmlAttribute
    public int getTileheight() {
        return tileheight;
    }

    /**
     *
     * @param tileheight
     */
    public void setTileheight(int tileheight) {
        this.tileheight = tileheight;
    }

    /**
     *
     */
    public TileMap() {
    }

//        
////        return tiles.getTile(mapping[row][col]);
//    int[][] mapping;
//
//
//    public void init() {
//        mapping = new int[width][height];
//        
//    }
//
//    public Image getTileAt(int row, int col) {
//        assert row < width && row > 0 : "Row " + row + " doesn't exist";
//        assert col < height && col > 0 : "Column " + row + " doesn't exist";
//        
////        return tiles.getTile(mapping[row][col]);
//    }
    /**
     *
     * @param gid
     * @return
     */
    public Tile getTile(int gid) {
        TileSet match = null;
        for (TileSet tileSet : tilesets) {
            if (gid + 1 >= tileSet.getFirstgid()) {
                match = tileSet;
            }
        }
        int tileIndex = gid - match.getFirstgid();
        ArrayList<Tile> tileList = match.getTileList();
        if (tileList == null) {
            return null;
        }
        for (Tile tile : tileList) {
            if (tile.getId() == tileIndex) {
                return tile;
            }
        }
        return null;
    }

//    public Image getTileImage(int gid) {
//        if (gid == 0) {
//            return tilesets.get(0).getTile(-1);
//        }
//
//        TileSet match = null;
//        for (TileSet tileSet : tilesets) {
//            if (gid >= tileSet.getFirstgid()) {
//                match = tileSet;
//            }
//        }
//        int tileIndex = gid - match.getFirstgid();
//
//        Image tile = match.getTile(tileIndex);
//
//        return match.getTile(gid - match.getFirstgid());
//    }

    /**
     *
     * @param graphicsContext2D
     * @param gid
     */
    public void drawTile(GraphicsContext graphicsContext2D, int gid) {
        if (gid == 0) {
            return;
        }
        TileSet match = null;
        for (TileSet tileSet : tilesets) {
            if (gid >= tileSet.getFirstgid()) {
                match = tileSet;
            }
        }
        int tileIndex = gid - match.getFirstgid();
        match.drawTile(graphicsContext2D, tileIndex);
    }
    private static final Logger LOG = Logger.getLogger(TileMap.class.getName());
}
