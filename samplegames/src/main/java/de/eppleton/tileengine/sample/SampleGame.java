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
import java.util.ArrayList;
import java.util.logging.Logger;
import net.java.html.canvas.GraphicsContext;

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
        try {
            // create the world 
            // initialize the TileMapCanvas
           TileMap tileMap = TileMapReader.readMap("/de/eppleton/tileengine/sample/resources/maps/sample.tmx");

//            final Level playingField = new Level(new GraphicsContext(new JavaFXGraphicsEnvironment(canvas)), tileMap.getTilewidth() * tileMap.getWidth(), tileMap.getHeight() * tileMap.getTileheight(), 500, 500);
            ArrayList<TileMapLayer> layers = tileMap.getLayers();
            for (TileMapLayer tileMapLayer : layers) {
                this.addLayer(tileMapLayer);
            }

            // add Handlers
            SpriteHandler monsterHandler = new SpriteHandler();
            ArrayList<ObjectGroup> objectGroups = tileMap.getObjectGroups();
            for (ObjectGroup objectGroup : objectGroups) {
                monsterHandler.handle(objectGroup, this);
            }
            DefaultMoveBehavior defaultMoveBehavior = new DefaultMoveBehavior();          
            defaultMoveBehavior.addMoveValidator(new SampleMoveValidator(tileMap));
            addBehaviour(defaultMoveBehavior);
 
            
        } catch (Exception ex) {
            Logger.getLogger(SampleGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    private static final Logger LOG = Logger.getLogger(SampleGame.class.getName());
}
