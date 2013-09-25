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
        super("astar");
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
