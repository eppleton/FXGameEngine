/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.tileengine.sample.sprites;

import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.tileengine.TObject;
import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileSet;
import de.eppleton.fx2d.tileengine.TileSetAnimation;
import java.util.Properties;

/**
 *
 * @author antonepple
 */
public class GearFactory {

    public static Gear createGear(TObject tObject, TileMap map, GameCanvas field) {
        double x = tObject.getX();
        double y = tObject.getY();

        final TileSet tileSet = map.getTileSet(tObject.getProperties().getProperty("tileset"));
        int tileindex = 0;
        if (tObject.getProperties().containsKey("tileindex")) {
            tileindex = Integer.parseInt(tObject.getProperties().getProperty("tileindex"));
        }
        String property = tObject.getProperties().getProperty("layer");
        return createGear(x, y, tileSet, tileindex, tObject.getProperties(), field, Integer.parseInt(property));
    }

    public static Gear createGear(double x, double y, TileSet tileSet, int tileindex, Properties properties, GameCanvas field, int layer) {
        TileSetAnimation tileSetAnimation = new TileSetAnimation(tileSet, new int[]{tileindex}, .1f);

        final Gear gear = new Gear(tileSet, properties, field, properties.getProperty("name"), x, y, 32, 32, layer);
        gear.setAnimation(tileSetAnimation);

        return gear;
    }

    private GearFactory() {
    }
}
