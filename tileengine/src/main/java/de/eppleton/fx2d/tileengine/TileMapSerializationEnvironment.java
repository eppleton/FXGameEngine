/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine;

import com.dukescript.api.canvas.GraphicsContext2D;

/**
 *
 * @author antonepple
 */
public interface TileMapSerializationEnvironment {

    public TileMap readMap(GraphicsContext2D g2d, String url) throws TileMapException;

//    public TileSet readSet(String url) throws TileMapException;

    
}
