/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine;

import com.dukescript.api.canvas.GraphicsContext2D;
import com.eclipsesource.json.DefaultTileMapSerializationEnvironment;
import javax.xml.bind.JAXBException;

/**
 *
 * @author antonepple
 */
public class TileMapReader {

    private static TileMapSerializationEnvironment READER;

    /**
     *
     * @param g2d
     * @param url the url as used for loading resources from a jar
     * 
     * @return the initialized TileMap
     * @throws TileMapException
     */
    public static TileMap readMap(GraphicsContext2D g2d, String url) throws TileMapException {
        return getReader().readMap(g2d, url);
    }

     /**
     *
     * @param url the url formatted like it's used for loading resources from a
     * jar
     * @return the Initialized TileSet.
     * @throws JAXBException
     */
//    public static TileSet readSet(String url) throws TileMapException {
//        return getReader().readSet(url);
//    }
/**
     * only resolves relative path in the same dir or one dir up like they're
     * usually defined by Tiled.
     *
     * @param relativePath
     * @param baseURL
     * @return the path formatted to use for class.getResource...
     */
    public static String resourcePath(String relativePath, String baseURL) {
        String parentDir = baseURL.substring(0, baseURL.lastIndexOf('/'));
        String result;
        if (relativePath.indexOf("./") == -1) {
            result = baseURL + "/" + relativePath;
        } else {
            result = relativePath.replace("../", parentDir + "/");
            result = result.replace("./", baseURL + "/");
        }
        return result;
    }

    private static TileMapSerializationEnvironment getReader() {
        if (READER == null) {
            READER = new DefaultTileMapSerializationEnvironment();
        }
        return READER;
    }

//    public static TileMap readMapFromFile(String fileURL) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
