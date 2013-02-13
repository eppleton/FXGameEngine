/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine;

import java.util.ArrayList;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonepple
 */
@XmlRootElement(name = "tileset")
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

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @XmlElement(name = "tile")
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

    @XmlAttribute
    public int getFirstgid() {
        return firstgid;
    }

    public void setFirstgid(int firstgid) {
        this.firstgid = firstgid;
    }

    @XmlAttribute
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;

    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public int getTilewidth() {
        return tilewidth;
    }

    public void setTilewidth(int tilewidth) {
        this.tilewidth = tilewidth;
    }

    @XmlAttribute
    public int getTileheight() {
        return tileheight;
    }

    public void setTileheight(int tileheight) {
        this.tileheight = tileheight;
    }

    @XmlAttribute
    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    @XmlAttribute
    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }
//    HashMap<Integer, Image> tiles;

    public int getNumColumns(){
        return cols;
    }
    
        public int getNumRows(){
        return rows;
    }
    
    public void merge(TileSet other){
        Image merge = ImageUtilities.merge(this.getTileImage(), other.getTileImage());
        init(merge);
    }
    
    
    public void init(Image tileSet) {
        this.tileImage = tileSet;
        cols = (int) (tileSet.getWidth() / tilewidth);
        rows = (int) (tileSet.getHeight() / tileheight);
        numTiles = rows * cols;
    }

   public void drawTile(GraphicsContext graphicsContext2D, int tileIndex) {
        int x = tileIndex % cols;
        int y = tileIndex / cols;
        graphicsContext2D.drawImage(tileImage, x * tilewidth, y* tileheight, tilewidth, tileheight, 0, 0, tilewidth, tileheight);
    }
   
   public int getNumTiles() {
        return numTiles;
    }
    private static final Logger LOG = Logger.getLogger(TileSet.class.getName());
}
