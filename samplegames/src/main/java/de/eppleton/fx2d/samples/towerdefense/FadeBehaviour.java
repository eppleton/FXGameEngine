/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samples.towerdefense;

import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.Layer;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.Behavior;
import java.util.Collection;
import net.java.html.canvas.GraphicsContext;
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
            setOpacity(0);
        }

        private void fade() {
            setOpacity(getOpacity() + .1);
            System.out.println("fade " + getOpacity());
        }

        @Override
        public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
            graphicsContext.setFillStyle(new Color("rgba(0,0,0,"+getOpacity()+")"));
            graphicsContext.fillRect(x, y, width, height);
        }
    }
}
