/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine.algorithms;

import de.eppleton.fx2d.Layer;
import de.eppleton.fx2d.tileengine.TileMap;
import net.java.html.canvas.GraphicsContext;
import net.java.html.canvas.Style.Color;

/**
 *
 * @author antonepple
 */
class AStarLayer extends Layer {

    private AStar.PathNode attackPath;
    private final TileMap tileMap;

    public AStarLayer(TileMap tileMap, AStar.PathNode attackPath) {
        this.attackPath = attackPath;
        this.tileMap = tileMap;

    }
    Color pathColor = new Color("rgb(255, 100, 100, .3");

    @Override
    public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
        AStar.PathNode start = attackPath;
        if (start != null) {
            graphicsContext.setFillStyle(pathColor);
            graphicsContext.fillRect(start.getX() * tileMap.getTilewidth(), start.getY() * tileMap.getTileheight(), tileMap.getTilewidth(), tileMap.getTileheight());
            while (start.getParent() != null) {
                start = start.getParent();
                graphicsContext.fillRect(start.getX() * tileMap.getTilewidth(), start.getY() * tileMap.getTileheight(), tileMap.getTilewidth(), tileMap.getTileheight());
            }
        }
    }
}
