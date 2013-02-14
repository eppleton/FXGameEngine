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

import de.eppleton.fx2d.Layer;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.GraphicsContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonepple
 */
@XmlRootElement(name = "layer")
public class TileMapLayer extends Layer {

    private String name;
    private double opacity;
    private BooleanProperty visibleProperty = new SimpleBooleanProperty(true);
    private Data data;
    private TileMap tileMap;

    /**
     *
     * @return
     */
    @XmlElement
    public Data getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     *
     * @return
     */
    @XmlAttribute
    @Override
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    @XmlAttribute
    @Override
    public double getOpacity() {
        return opacity;
    }

    /**
     *
     * @param opacity
     */
    @Override
    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    /**
     *
     * @return
     */
    @XmlAttribute
    @Override
    public boolean isVisible() {
        return visibleProperty.get();
    }

    /**
     *
     * @param visible
     */
    @Override
    public void setVisible(boolean visible) {
        this.visibleProperty.set(visible);
    }

    /**
     *
     * @param idx
     * @return
     */
    public int getGid(int idx) {
        return data.getGid(idx);
    }

    /**
     *
     * @param graphicsContext
     * @param cameraX
     * @param cameraY
     * @param screenWidth
     * @param screenHeight
     */
    @Override
    public void draw(GraphicsContext graphicsContext, double cameraX, double cameraY, double screenWidth, double screenHeight) {
        // x,y index of first tile to be shown
        int startX = (int) (cameraX / tileMap.getTilewidth());
        int startY = (int) (cameraY / tileMap.getTileheight());

        // the offset in pixels
        int offX = (int) (cameraX % tileMap.getTilewidth());
        int offY = (int) (cameraY % tileMap.getTileheight());

        int screenHeightInTiles = (int) screenHeight / tileMap.getTileheight();
        int screenWidthInTiles = (int) screenWidth / tileMap.getTilewidth();

        double cameraMaxX = (tileMap.getWidth() * tileMap.getTilewidth()) - screenWidth;
        double cameraMaxY = (tileMap.getHeight() * tileMap.getTileheight()) - screenHeight;

        for (int y = 0; y < screenHeightInTiles
                // render an extra row to make up for the offset
                + (startY >= (int) (cameraMaxY / tileMap.getTileheight()) ? 0 : 1); y++) {
            for (int x = 0; x < screenWidthInTiles
                    // render an extra row to make up for the offset
                    + (startX >= (int) (cameraMaxX / tileMap.getTilewidth()) ? 0 : 1); x++) {
                int gid = getGid((x + startX) + ((y + startY) * tileMap.getWidth()));

                graphicsContext.save();
                graphicsContext.translate((x * tileMap.getTilewidth()) - offX,
                        (y * tileMap.getTileheight()) - offY);
                tileMap.drawTile(graphicsContext, gid);
                graphicsContext.restore();
            }
        }

    }

    /**
     *
     * @param u
     * @param parent
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        this.tileMap = (TileMap) parent;
    }
    private static final Logger LOG = Logger.getLogger(TileMapLayer.class.getName());

    public BooleanProperty getVisibleProperty() {
        return visibleProperty;
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }
    
    
}
