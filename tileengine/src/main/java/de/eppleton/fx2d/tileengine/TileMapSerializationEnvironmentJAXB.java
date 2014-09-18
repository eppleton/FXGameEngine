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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import net.java.html.canvas.GraphicsContext2D;
import net.java.html.canvas.Image;
import org.eclipse.persistence.jaxb.JAXBContextFactory;

/**
 * Factory for TileMaps and TileSets to Unmarshal them from Files and initialize
 * the TileSet Images.
 *
 * @author antonepple
 */
//@ServiceProvider(service = TileMapSerializationEnvironment.class)
@Deprecated
public class TileMapSerializationEnvironmentJAXB implements TileMapSerializationEnvironment {

    public TileMapSerializationEnvironmentJAXB() {
    }

    /**
     *
     * @param url the url as used for laoding resources from a regular file
     * @return the initialized TileMap
     * @throws JAXBException
     */
    public TileMap readMapFromFile(GraphicsContext2D g2d,String url) throws JAXBException, FileNotFoundException, TileMapException {
        try {
            String baseUrl = url.substring(0, url.lastIndexOf('/'));
            Map<String, Object> properties = new HashMap<String, Object>(1);
            List<String> metadata = new ArrayList<String>(2);
            metadata.add("de/eppleton/fx2d/tileengine/binding.xml");
            metadata.add("de/eppleton/fx2d/tileengine/binding2.xml");
            properties.put(JAXBContextFactory.ECLIPSELINK_OXM_XML_KEY, metadata);
            JAXBContext context = JAXBContext.newInstance(new Class[]{TileMap.class}, properties);

            Unmarshaller um = context.createUnmarshaller();
            TileMap map = (TileMap) um.unmarshal(new FileInputStream(url));

            List<TileSet> tileSetlist = map.getTileSetlist();
            JAXBContext tileSetContext = JAXBContext.newInstance(new Class[]{TileSet.class}, properties);
            Unmarshaller tsum = tileSetContext.createUnmarshaller();
            for (TileSet tileSet : tileSetlist) {
                if (tileSet.getSource() != null) {
                    String resourcePath = TileMapReader.resourcePath(tileSet.getSource(), baseUrl);
//                    TileSet set = (TileSet) tsum.unmarshal(TileMapSerializationEnvironmentJAXB.class.getResourceAsStream(resourcePath));
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
                tileSet.init(g2d,image);
            }
            afterUnMarshal(map);
            return map;
        } catch (JAXBException ex) {
            Logger.getLogger(TileMapSerializationEnvironmentJAXB.class.getName()).log(Level.SEVERE, null, ex);
            throw new TileMapException("Could not deserialize " + url, ex);
        }
    }
    

    /**
     *
     * @param url the url as used for laoding resources from a jar
     * @return the initialized TileMap
     * @throws JAXBException
     */
    @Override
    public TileMap readMap(GraphicsContext2D g2d,String url) throws TileMapException {
        try {
            String baseUrl = url.substring(0, url.lastIndexOf('/'));
            Map<String, Object> properties = new HashMap<String, Object>(1);
            List<String> metadata = new ArrayList<String>(2);
            metadata.add("de/eppleton/fx2d/tileengine/binding.xml");
            metadata.add("de/eppleton/fx2d/tileengine/binding2.xml");
            properties.put(JAXBContextFactory.ECLIPSELINK_OXM_XML_KEY, metadata);
            JAXBContext context = JAXBContext.newInstance(new Class[]{TileMap.class}, properties);

            //        JAXBContext context = JAXBContext.newInstance(TileMap.class);
            Unmarshaller um = context.createUnmarshaller();
            TileMap map = (TileMap) um.unmarshal(TileMap.class.getResourceAsStream(url));

            List<TileSet> tileSetlist = map.getTileSetlist();
            JAXBContext tileSetContext = JAXBContext.newInstance(new Class[]{TileSet.class}, properties);
            Unmarshaller tsum = tileSetContext.createUnmarshaller();
            for (TileSet tileSet : tileSetlist) {
                if (tileSet.getSource() != null) {
                    String resourcePath = TileMapReader.resourcePath(tileSet.getSource(), baseUrl);
                    TileSet set = (TileSet) tsum.unmarshal(TileMapSerializationEnvironmentJAXB.class.getResourceAsStream(resourcePath));
                    tileSet.setBaseUrl(baseUrl);
                    tileSet.setImage(set.getImage());
                    tileSet.setMargin(set.getMargin());
                    tileSet.setName(set.getName());
                    tileSet.setSpacing(set.getSpacing());
                    tileSet.setTileheight(set.getTileheight());
                    tileSet.setTilewidth(set.getTilewidth());
                }
                String source = TileMapReader.resourcePath(tileSet.getImage().getSource(), baseUrl);
                LOG.log(Level.INFO, "loading image " + source);
                Image image = Image.create(TileMapSerializationEnvironmentJAXB.class.getResource(source).toExternalForm());
                tileSet.init(g2d, image);
            }
            afterUnMarshal(map);
            return map;
        } catch (JAXBException ex) {
            Logger.getLogger(TileMapSerializationEnvironmentJAXB.class.getName()).log(Level.SEVERE, null, ex);
            throw new TileMapException("Could not deserialize " + url, ex);
        }
    }

    /**
     *
     * @param url the url formatted like it's used for loading resources from a
     * jar
     * @return the Initialized TileSet.
     * @throws JAXBException
     */
    public TileSet readSet(GraphicsContext2D g2d, String url) throws TileMapException {
        try {
            String baseUrl = url.substring(0, url.lastIndexOf('/'));
            Map<String, Object> properties = new HashMap<String, Object>(1);
            List<String> metadata = new ArrayList<String>(2);
            metadata.add("de/eppleton/fx2d/tileengine/binding.xml");
            metadata.add("de/eppleton/fx2d/tileengine/binding2.xml");
            properties.put(JAXBContextFactory.ECLIPSELINK_OXM_XML_KEY, metadata);
            JAXBContext tileSetContext = JAXBContext.newInstance(new Class[]{TileSet.class}, properties);
            Unmarshaller tsum = tileSetContext.createUnmarshaller();
            TileSet tileSet = null;
            InputStream resourceAsStream = TileSet.class.getResourceAsStream(url);
            tileSet = (TileSet) tsum.unmarshal(resourceAsStream);

            tileSet.setBaseUrl(baseUrl);
            String source = tileSet.getImage().getSource();
            String resourcePath = TileMapReader.resourcePath(source, baseUrl);
            Image image = Image.create(TileMapSerializationEnvironmentJAXB.class.getResource(resourcePath).toExternalForm());
            tileSet.init(g2d, image);
            return tileSet;
        } catch (JAXBException ex) {
            Logger.getLogger(TileMapSerializationEnvironmentJAXB.class.getName()).log(Level.SEVERE, null, ex);
            throw new TileMapException("Could not deserialize " + url, ex);

        }
    }

    public static void writeMap(TileMap tileMap, String fileURL) throws JAXBException, FileNotFoundException {
        JAXBContext tileMapContext = JAXBContext.newInstance(TileMap.class);
        Marshaller marshaller = tileMapContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(tileMap, new FileOutputStream(fileURL));
    }


    private static void afterUnMarshal(TileMap map) {
        List<ObjectGroup> objectGroups = map.getObjectGroups();
        for (ObjectGroup objectGroup : objectGroups) {
            objectGroup.afterUnmarshal(map);
        }
        List<TileMapLayer> layers = map.getLayers();
        for (TileMapLayer tileMapLayer : layers) {
            tileMapLayer.afterUnmarshal(map);
        }
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

    private static final Logger LOG = Logger.getLogger(TileMapSerializationEnvironmentJAXB.class.getName());
}
