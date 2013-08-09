/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine.action;

import de.eppleton.fx2d.tileengine.action.RotatingTileSetAnimation;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.tileengine.TileSet;
import de.eppleton.fx2d.tileengine.TileSet;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author antonepple
 */
public class LookAheadTileSetAnimation extends RotatingTileSetAnimation {

    public LookAheadTileSetAnimation(TileSet set, int[] indices, float speed) {
        super(set, indices, speed);
    }

    @Override
    public void render(Sprite sprite, GraphicsContext context, float alpha, long delta) {
        setAngle(Math.toDegrees(Math.atan2(sprite.getVelocityY(), sprite.getVelocityX())));
        super.render(sprite, context, alpha, delta); //To change body of generated methods, choose Tools | Templates.
    }
}
