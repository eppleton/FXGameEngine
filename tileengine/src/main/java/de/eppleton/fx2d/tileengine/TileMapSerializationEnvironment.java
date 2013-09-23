/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine;

/**
 *
 * @author antonepple
 */
public interface TileMapSerializationEnvironment {

    public TileMap readMap(String url) throws TileMapException;

//    public TileSet readSet(String url) throws TileMapException;

    
}
