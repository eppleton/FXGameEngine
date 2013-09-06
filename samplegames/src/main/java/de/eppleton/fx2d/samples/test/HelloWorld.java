/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samples.test;

import de.eppleton.fx2d.Layer;
import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.SpriteLayer;
import net.java.html.canvas.GraphicsContext;

/**
 *
 * @author antonepple
 */
public class HelloWorld extends Level {

    public HelloWorld(GraphicsContext graphicsContext, double playfieldWidth, double playfieldHeight, double viewPortWidth, double viewPortHeight) {
        super(graphicsContext, playfieldWidth, playfieldHeight, viewPortWidth, viewPortHeight);
    }

    @Override
    protected void initGame() {
        addLayer(new SpriteLayer());

        final Sprite sprite = new Sprite(this, "Test", 100, 100, 42, 42);
        sprite.setVelocityX(1);
        addLayer(new Layer() {
            int i = 1;

            @Override
            public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
                graphicsContext.setFillStyle(graphicsContext.getWebColor("#ffffff"));
                getGraphicsContext().setFont("italic 40 Calibri");
                getGraphicsContext().fillText("I=" + (i++), 100, 100);
//                sprite.setX(sprite.getX() + 1);
            }
        });
        addSprite(sprite);
        this.start();

    }
}
