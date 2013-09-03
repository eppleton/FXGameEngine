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

import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.tileengine.TObject;
import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileSet;
import de.eppleton.fx2d.tileengine.action.TileSetAnimation;
import java.util.Properties;

/**
 *
 * @author antonepple
 */
public class GearFactory {

    public static Gear createGear(TObject tObject, TileMap map, Level field) {
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

    public static Gear createGear(double x, double y, TileSet tileSet, int tileindex, Properties properties, Level field, int layer) {
        TileSetAnimation tileSetAnimation = new TileSetAnimation(tileSet, new int[]{tileindex}, .1f);

        final Gear gear = new Gear(tileSet, properties, field, properties.getProperty("name"), x, y, 32, 32, layer);
        gear.setAnimation(tileSetAnimation);

        return gear;
    }

    private GearFactory() {
    }
}
