/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samplegames.javafx;

import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.javafx.app.Game;
import de.eppleton.fx2d.samples.towerdefense.TowerDefense;
import static javafx.application.Application.launch;
import net.java.html.canvas.GraphicsContext;

/**
 *
 * @author antonepple
 */
public class TowerDefenseGame extends Game{

    @Override
    protected Level getLevel(GraphicsContext context) {
        return new TowerDefense(context, 460, 414, 460, 414);
    }
    public static void main(String[] args) {
        launch(args);
    }
}