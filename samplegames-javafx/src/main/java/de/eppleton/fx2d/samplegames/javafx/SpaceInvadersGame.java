/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samplegames.javafx;

import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.javafx.app.Game;
import de.eppleton.fx2d.samples.spaceinvaders.SpaceInvaders;
import static javafx.application.Application.launch;
import net.java.html.canvas.GraphicsContext;

/**
 *
 * @author antonepple
 */
public class SpaceInvadersGame extends Game{

    @Override
    protected Level getLevel(GraphicsContext context) {
        return new SpaceInvaders(context, 800, 600, 800, 600);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
