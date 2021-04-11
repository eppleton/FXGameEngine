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
package de.eppleton.fx2d.tileengine;

import com.dukescript.api.canvas.GraphicsContext2D;
import com.dukescript.api.canvas.Image;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author antonepple
 */
public class TileSet {
    // The first global tile ID of this tileset (this global ID maps to the first tile in this tileset).

    private int firstgid;
    //If this tileset is stored in an external TSX (Tile Set XML) file, this attribute refers to that file. That TSX file has the same structure as the attribute as described here. (There is the firstgid attribute missing and this source attribute is also not there. These two attributes are kept in the TMX map, since they are map specific.)
    private String source;
    // The name of this tileset.
    private String name;
    // The (maximum) width of the tiles in this tileset.
    private int tilewidth;
    // The (maximum) height of the tiles in this tileset.
    private int tileheight;
    // The spacing in pixels between the tiles in this tileset (applies to the tileset image).
    private int spacing;
    // The margin around the tiles in this tileset (applies to the tileset image).
    private int margin;
    // The image that contains the tiles
    private SourceImage image;
    // Tiles can have properties, if so they are stored like this
    private ArrayList<Tile> tileList;
    // the actual Image
    private Image tileImage;
    // number of columns
    private int cols;
    private int rows;
    private int numTiles;
    private String baseUrl;
    private int imageWidth;
    private int imageHeight;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public ArrayList<Tile> getTileList() {

        return tileList;
    }

    public Image getTileImage() {
        return tileImage;
    }

    public void setTileList(ArrayList<Tile> tileList) {
        this.tileList = tileList;
    }

//    public HashMap<Integer, Image> getTiles() {
//        return tiles;
//    }
//    public void setTiles(HashMap<Integer, Image> tiles) {
//        this.tiles = tiles;
//    }
    public SourceImage getImage() {
        return image;
    }

    public void setImage(SourceImage image) {
        this.image = image;
    }

    public int getFirstgid() {
        return firstgid;
    }

    public void setFirstgid(int firstgid) {
        this.firstgid = firstgid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTilewidth() {
        return tilewidth;
    }

    public void setTilewidth(int tilewidth) {
        this.tilewidth = tilewidth;
    }

    public int getTileheight() {
        return tileheight;
    }

    public void setTileheight(int tileheight) {
        this.tileheight = tileheight;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }
//    HashMap<Integer, Image> tiles;

    public int getNumColumns() {
        return cols;
    }

    public int getNumRows() {
        return rows;
    }

    public void merge(GraphicsContext2D gc, List<TileSet> other) {
        System.out.println("tilesets to merge "+other.size());
        List<Image> images = new ArrayList<>();
        for (TileSet set : other) {
               images.add(set.getTileImage());
        }
        System.out.println("images to merge "+other.size());
        Image merge = gc.merge(this.getTileImage(),images );
        init(gc, merge);
    }

    public void init(GraphicsContext2D g2d, Image image) {
        this.tileImage = image;
        cols = (int) (imageWidth / tilewidth);
        rows = (int) (imageHeight / tileheight);
        numTiles = rows * cols;
    }

    public void drawTile(GraphicsContext2D graphicsContext2D, int tileIndex) {
        int x = tileIndex % cols;

        int y = tileIndex / cols;
        graphicsContext2D.drawImage(tileImage, x * tilewidth, y * tileheight, tilewidth, tileheight, 0, 0, tilewidth, tileheight);
    }

    public int getNumTiles() {
        return numTiles;
    }

    public void setImageHeight(int asInt) {
        this.imageHeight = asInt;
    }

    public void setImageWidth(int asInt) {
        this.imageWidth = asInt;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    
}
