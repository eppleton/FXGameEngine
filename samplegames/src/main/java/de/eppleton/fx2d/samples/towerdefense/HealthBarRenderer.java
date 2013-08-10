/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samples.towerdefense;

import de.eppleton.fx2d.Renderer;
import de.eppleton.fx2d.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
    public void render(Sprite sprite, GraphicsContext context, float alpha, long time) {
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
        context.setFill( Color.rgb(200,200,200,.5));
        context.fillRect(4+(width / 2), 10+(height / 2), (width / 2), 4);
        
        context.setFill( Color.rgb(0,255,0,.5));
        if (percent < .5) {
            context.setFill(Color.rgb(255,255,0,.5));
        }
        if (percent < .2) {
            context.setFill(Color.rgb(255,0,0,0.5));
        }
        context.fillRect(4+(width / 2), 10+(height / 2), (width / 2 * percent), 4);
    }
}
