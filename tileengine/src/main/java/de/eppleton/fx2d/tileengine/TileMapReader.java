/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine;

import java.util.ServiceLoader;

/**
 *
 * @author antonepple
 */
public class TileMapReader {

    private static TileMapSerializationEnvironment READER;

    /**
     *
     * @param url the url as used for laoding resources from a jar
     * @return the initialized TileMap
     * @throws JAXBException
     */
    public static TileMap readMap(String url) throws TileMapException {
        return getReader().readMap(url);
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
            ServiceLoader<TileMapSerializationEnvironment> load = ServiceLoader.load(TileMapSerializationEnvironment.class);
            for (TileMapSerializationEnvironment tileMapSerializationEnvironment : load) {
                READER = tileMapSerializationEnvironment;
                return READER;
            }
        }
        return READER;
    }
}
