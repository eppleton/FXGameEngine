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
package de.eppleton.fx2d.samples.towerdefense;

import com.dukescript.api.canvas.GraphicsContext2D;
import com.dukescript.api.canvas.Style.Color;
import de.eppleton.fx2d.Renderer;
import de.eppleton.fx2d.Sprite;


/**
 *
 * @author antonepple
 */
public class HealthBarRenderer implements Renderer {

    @Override
    public boolean prepare(Sprite sprite, long time) {
        return true;
    }

    @Override
    public void render(Sprite sprite, GraphicsContext2D context, float alpha, long time) {
        context.translate(sprite.getWidth() / 2, sprite.getHeight() / 2);
        context.rotate(-sprite.getRotation());
        context.translate(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
        EnemySprite enemySprite = (EnemySprite) sprite;
        double health = enemySprite.getEnergy();
        double maxHealth = enemySprite.getMaxHealth();
        if (health == maxHealth) {
            return;
        }

        int width = sprite.getWidth();
        int height = sprite.getHeight();
        double percent = health / maxHealth;
        context.setFillStyle(new Color("rgba(200,200,200,.5)"));
        context.fillRect(4+(width / 2), 10+(height / 2), (width / 2), 4);
        
        context.setFillStyle(new Color("rgba(0,255,0,.5)"));
        if (percent < .5) {
            context.setFillStyle(new Color("rgba(255,255,0,.5)"));
        }
        if (percent < .2) {
            context.setFillStyle(new Color("rgba(255,0,0,0.5)"));
        }
        context.fillRect(4+(width / 2), 10+(height / 2), (width / 2 * percent), 4);
    }
}
