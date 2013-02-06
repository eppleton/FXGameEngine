/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Factory for TileMaps and TileSets to Unmarshal them from Files and initialize
 * the TileSet Images.
 *
 * @author antonepple
 */
public class TileMapReader {

    /**
     *
     * @param url the url as used for laoding resources from a jar
     * @return the initialized TileMap
     * @throws JAXBException
     */
    public static TileMap readMap(String url) throws JAXBException {
        String baseUrl = url.substring(0, url.lastIndexOf('/'));

        JAXBContext context = JAXBContext.newInstance(TileMap.class);
        Unmarshaller um = context.createUnmarshaller();
        TileMap map = (TileMap) um.unmarshal(TileMap.class.getResourceAsStream(url));
        ArrayList<TileSet> tileSetlist = map.getTileSetlist();
        JAXBContext tileSetContext = JAXBContext.newInstance(TileSet.class);
        Unmarshaller tsum = tileSetContext.createUnmarshaller();
        for (TileSet tileSet : tileSetlist) {
            if (tileSet.getSource() != null) {
                String resourcePath = resourcePath(tileSet.getSource(), baseUrl);
                TileSet set = (TileSet) tsum.unmarshal(TileMapReader.class.getResourceAsStream(resourcePath));
                tileSet.setBaseUrl(baseUrl);
                tileSet.setImage(set.getImage());
                tileSet.setMargin(set.getMargin());
                tileSet.setName(set.getName());
                tileSet.setSpacing(set.getSpacing());
                tileSet.setTileheight(set.getTileheight());
                tileSet.setTilewidth(set.getTilewidth());
            }
            String source = resourcePath(tileSet.getImage().getSource(), baseUrl);
            LOG.log(Level.INFO, "loading image " + source);
            Image image = new Image(TileMapReader.class.getResourceAsStream(source));
            tileSet.init(image);
        }
        return map;
    }

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
            result = relativePath.replaceAll("\\.\\./", parentDir + "/");
            result = result.replaceAll("\\./", baseURL + "/");
        }
        return result;
    }

    /**
     *
     * @param url the url formatted like it's used for loading resources from a
     * jar
     * @return the Initialized TileSet.
     * @throws JAXBException
     */
    public static TileSet readSet(String url) throws JAXBException {
        String baseUrl = url.substring(0, url.lastIndexOf('/'));

        JAXBContext tileSetContext = JAXBContext.newInstance(TileSet.class);
        Unmarshaller tsum = tileSetContext.createUnmarshaller();
        TileSet tileSet = null;
        try (InputStream resourceAsStream = TileSet.class.getResourceAsStream(url)) {
             tileSet = (TileSet) tsum.unmarshal(resourceAsStream);
        } catch (IOException iox){
            LOG.log(Level.ALL, "Unable to load tileset "+url, iox);
        }
        tileSet.setBaseUrl(baseUrl);
        String source = tileSet.getImage().getSource();
        String resourcePath = resourcePath(source, baseUrl);
        Image image = new Image(TileMapReader.class.getResourceAsStream(resourcePath));
        tileSet.init(image);
        return tileSet;
    }

    private TileMapReader() {
    }
    private static final Logger LOG = Logger.getLogger(TileMapReader.class.getName());
}
