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
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.SpriteBehavior;
import de.eppleton.fx2d.tileengine.TileSet;
import java.util.Properties;
import java.util.logging.Logger;
import org.openide.util.Lookup;

/**
 *
 * @author antonepple
 */
public class Gear extends Sprite {

    private TileSet tileset;
    private Properties properties;
    private int layer;

    public Gear(TileSet tileset, Properties properties, Level parent, String name, double x, double y, int width, int height, int layer) {
        super(parent, name, x, y, width, height, Lookup.EMPTY);
        this.tileset = tileset;
        this.properties = properties;
        this.layer = layer;
        addBehaviour(new SpriteBehavior() {
            @Override
            public boolean perform(Sprite sprite) {
                if (sprite.getCollisionBox().intersects(sprite.getParent().getSprite("hero").getCollisionBox())) {
                    boolean taken = ((Hero) sprite.getParent().getSprite("hero")).offer(Gear.this);
                    if (taken) {
                        sprite.getParent().removeSprite(sprite);
                    }
                }
                return true;
            }
        });
    }

    public TileSet getTileset() {
        return tileset;
    }

    public String getProperty(String property) {
        return properties.getProperty(property);
    }

    public int getLayer() {
        return layer;
    }
    private static final Logger LOG = Logger.getLogger(Gear.class.getName());
}
