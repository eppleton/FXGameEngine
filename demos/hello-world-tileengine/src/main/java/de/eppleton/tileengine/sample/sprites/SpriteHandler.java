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
package de.eppleton.tileengine.sample.sprites;

import de.eppleton.fx2d.Camera;
import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.Rectangle2D;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.tileengine.ObjectGroup;
import de.eppleton.fx2d.tileengine.ObjectGroupHandler;
import de.eppleton.fx2d.tileengine.TObject;
import de.eppleton.fx2d.tileengine.TileSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

/**
 *
 * @author antonepple
 */
public class SpriteHandler implements ObjectGroupHandler {

    Hero hero;

    @Override
    public void handle(ObjectGroup group, final Level field) {
        if (group.getName().equals("sprites")) {
            for (final TObject tObject : group.getObjectLIst()) {
                if (tObject.getType().equals("gear")) {
                    field.addSprite(GearFactory.createGear(tObject, group.getTileMap(), field));
                }
                if (tObject.getName().equals("MonsterSpawner")) {
                    double x = tObject.getX();
                    double y = tObject.getY();
                    final TileSet monster = group.getTileMap().getTileSet(tObject.getProperties().getProperty("tileset"));
//                        TileSet monster = TileMapReader.readSet("/de/eppleton/tileengine/resources/maps/BODY_skeleton.tsx");
                    Sprite monsterSprite = new Monster(field, "monster", x, y, 64, 64, monster);
                    monsterSprite.setMoveBox(new Rectangle2D(18, 42, 28, 20));
                    field.addSprite(monsterSprite);

                } else {
                    if (tObject.getName().equals("spawnpoint")) {
                        double x = tObject.getX();
                        double y = tObject.getY();
                        TileSet tileSet = group.getTileMap().getTileSet(tObject.getProperties().getProperty("tileset"));
                        hero = new Hero(field, tileSet, "hero", x, y, 64, 64);
                        field.setCamera(new Camera(hero.getXProperty(), hero.getYProperty()));
                        Properties properties = tObject.getProperties();
                        Set<String> keySet = properties.stringPropertyNames();
                        for (String key : keySet) {
                            if (key.startsWith("gear.")) {
                                String[] gear = key.split("\\.");
                                int layer = Integer.parseInt(gear[1]);
                                TileSet gearSet = group.getTileMap().getTileSet(tObject.getProperties().getProperty(key));
                                Gear createdGear = GearFactory.createGear(x, y, gearSet, 0, new Properties(), field, layer);
                                hero.offer(createdGear);
                            }
                            
                        }
                        hero.mergeTileSets();
                    }
                }
            }
        }
    }
    private static final Logger LOG = Logger.getLogger(SpriteHandler.class.getName());
}
