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
package de.eppleton.tileengine.sample;

import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.action.DefaultMoveBehavior;

import de.eppleton.fx2d.tileengine.ObjectGroup;
import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileMapLayer;
import de.eppleton.fx2d.tileengine.TileMapReader;
import de.eppleton.tileengine.sample.sprites.SpriteHandler;
import java.util.List;
import net.java.html.canvas.GraphicsContext;
import org.apidesign.bck2brwsr.htmlpage.Logger;

/**
 *
 * @author antonepple
 */
public class SampleGame extends Level {

    public SampleGame(GraphicsContext graphicsContext, double playfieldWidth, double playfieldHeight, double viewPortWidth, double viewPortHeight) {
        super(graphicsContext, playfieldWidth, playfieldHeight, viewPortWidth, viewPortHeight);
    }
    
    @Override
    public void initGame() {
        Logger.log("initializing...");
        try {
            // create the world 
            // initialize the TileMapCanvas
           TileMap tileMap = TileMapReader.readMap("/de/eppleton/tileengine/sample/resources/maps/sample.json");
        Logger.log("1");

//            final Level playingField = new Level(new GraphicsContext(new JavaFXGraphicsEnvironment(canvas)), tileMap.getTilewidth() * tileMap.getWidth(), tileMap.getHeight() * tileMap.getTileheight(), 500, 500);
            List<TileMapLayer> layers = tileMap.getLayers();
                    Logger.log("2");

            for (TileMapLayer tileMapLayer : layers) {
                this.addLayer(tileMapLayer);
            }        Logger.log("3");


            // add Handlers
            SpriteHandler monsterHandler = new SpriteHandler();
            List<ObjectGroup> objectGroups = tileMap.getObjectGroups();
            for (ObjectGroup objectGroup : objectGroups) {
                monsterHandler.handle(objectGroup, this);
            }
                    Logger.log("4");

            DefaultMoveBehavior defaultMoveBehavior = new DefaultMoveBehavior();          
            defaultMoveBehavior.addMoveValidator(new SampleMoveValidator(tileMap));
            addBehaviour(defaultMoveBehavior);
 
            
        } catch (Exception ex) {
           Logger.log(ex.getMessage());
        }
                Logger.log("game initialized");

    }

}
