/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.tileengine.sample.sprites;

import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.tileengine.ObjectGroup;
import de.eppleton.fx2d.tileengine.ObjectGroupHandler;
import de.eppleton.fx2d.tileengine.TObject;
import de.eppleton.fx2d.tileengine.TileSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;

/**
 *
 * @author antonepple
 */
public class SpriteHandler implements ObjectGroupHandler {

    Hero hero;

    @Override
    public void handle(ObjectGroup group, final GameCanvas field) {
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
                        field.setHero(hero);
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
                    }
                }
            }
        }
    }
    private static final Logger LOG = Logger.getLogger(SpriteHandler.class.getName());
}
