/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine.action;

import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.tileengine.TileSet;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author antonepple
 */
public class RotatingTileSetAnimation extends TileSetAnimation {

    private double angle = 0;

    public RotatingTileSetAnimation(TileSet set, int[] indices, float speed) {
        super(set, indices, speed);
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override
    public void render(Sprite sprite, GraphicsContext context, float alpha, long delta) {
        context.save();
        context.translate(sprite.getWidth() / 2, sprite.getHeight() / 2);
        context.rotate(angle);
        context.translate(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
        super.render(sprite, context, alpha, delta); //To change body of generated methods, choose Tools | Templates.
        context.restore();
    }
}