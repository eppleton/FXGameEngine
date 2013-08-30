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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import net.java.html.canvas.Image;

/**
 * Factory for TileMaps and TileSets to Unmarshal them from Files and initialize
 * the TileSet Images.
 *
 * @author antonepple
 */
public class TileMapReader {

    
    /**
     *
     * @param url the url as used for laoding resources from a regular file
     * @return the initialized TileMap
     * @throws JAXBException
     */
    public static TileMap readMapFromFile(String url) throws JAXBException, FileNotFoundException {
        String baseUrl = url.substring(0, url.lastIndexOf('/'));
        JAXBContext context = JAXBContext.newInstance(TileMap.class);
        Unmarshaller um = context.createUnmarshaller();
        TileMap map = (TileMap) um.unmarshal(new FileInputStream(url));
        ArrayList<TileSet> tileSetlist = map.getTileSetlist();
        JAXBContext tileSetContext = JAXBContext.newInstance(TileSet.class);
        Unmarshaller tsum = tileSetContext.createUnmarshaller();
        for (TileSet tileSet : tileSetlist) {
            if (tileSet.getSource() != null) {
                String resourcePath = resourcePath(tileSet.getSource(), baseUrl);
                TileSet set = (TileSet) tsum.unmarshal(new FileInputStream(resourcePath));
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
            Image image = Image.create(source);
            tileSet.init(image);
        }
        return map;
    }

    
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
            Image image = Image.create(TileMapReader.class.getResource
                    (source).toExternalForm());
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
        } catch (IOException iox) {
            LOG.log(Level.ALL, "Unable to load tileset " + url, iox);
        }
        tileSet.setBaseUrl(baseUrl);
        String source = tileSet.getImage().getSource();
        String resourcePath = resourcePath(source, baseUrl);
        Image image = Image.create(TileMapReader.class.getResource(resourcePath).toExternalForm());
        tileSet.init(image);
        return tileSet;
    }

    public static void writeMap(TileMap tileMap, String fileURL) throws JAXBException, FileNotFoundException{
        JAXBContext tileMapContext = JAXBContext.newInstance(TileMap.class);
        Marshaller marshaller =             tileMapContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(tileMap, new FileOutputStream(fileURL));    
    }

    private TileMapReader() {
    }
    private static final Logger LOG = Logger.getLogger(TileMapReader.class.getName());
}
