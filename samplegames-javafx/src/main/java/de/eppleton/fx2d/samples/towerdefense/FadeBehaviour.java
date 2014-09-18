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

import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.Layer;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.Behavior;
import java.util.Collection;
import net.java.html.canvas.GraphicsContext2D;
import net.java.html.canvas.Style.Color;

/**
 *
 * @author antonepple
 */
class FadeBehaviour extends Behavior {

    FadeLayer fadeLayer = new FadeLayer();

    @Override
    public boolean perform(Level canvas, long nanos) {
        if (canvas.getLayer("fadeLayer") == null) {
            canvas.addLayer(fadeLayer);
        }
        fadeLayer.fade();
        if (fadeLayer.getOpacity() >= 1) {
            Collection<Sprite> sprites = canvas.getSprites();
            for (Sprite sprite : sprites) {
               if (sprite instanceof EnemySprite) canvas.removeSprite(sprite);
            }
            System.out.println("removing Fadelayer");
           fadeLayer.setVisible(false);
        }
        return fadeLayer.getOpacity() < 1; //To change body of generated methods, choose Tools | Templates.
    }

    private static class FadeLayer extends Layer {

        public FadeLayer() {
            super("fade");
            setOpacity(0);
        }

        private void fade() {
            setOpacity(getOpacity() + .1);
            System.out.println("fade " + getOpacity());
        }

        @Override
        public void draw(GraphicsContext2D graphicsContext, double x, double y, double width, double height) {
            graphicsContext.setFillStyle(new Color("rgba(0,0,0,"+getOpacity()+")"));
            graphicsContext.fillRect(x, y, width, height);
        }
    }
}
