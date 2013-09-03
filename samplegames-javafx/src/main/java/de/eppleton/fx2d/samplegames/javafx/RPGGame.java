/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samplegames.javafx;

import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.javafx.app.Game;
import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileMapReader;
import de.eppleton.tileengine.sample.SampleGame;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javax.xml.bind.JAXBException;
import net.java.html.canvas.GraphicsContext;

/**
 *
 * @author antonepple
 */
public class RPGGame extends Game
{

    @Override
    protected Level getLevel(GraphicsContext context) {
    

        return new SampleGame(context, 1000, 1000, 500, 500);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
