/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.tileengine.sample;

import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.tileengine.ObjectGroup;
import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileMapLayer;
import de.eppleton.fx2d.tileengine.TileMapReader;
import de.eppleton.tileengine.sample.sprites.SpriteHandler;
import java.util.ArrayList;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.xml.bind.JAXBException;

/**
 *
 * @author antonepple
 */
public class SampleGame extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // create the world 
            TileMap tileMap = TileMapReader.readMap("/de/eppleton/tileengine/sample/resources/maps/sample.tmx");
            // initialize the TileMapCanvas
            GameCanvas playingField = new GameCanvas(tileMap.getTilewidth() * tileMap.getWidth(), tileMap.getHeight() * tileMap.getTileheight(), 500, 500);
            ArrayList<TileMapLayer> layers = tileMap.getLayers();
            for (TileMapLayer tileMapLayer : layers) {
                playingField.addLayer(tileMapLayer);
            }

            // add Handlers
            SpriteHandler monsterHandler = new SpriteHandler();
            ArrayList<ObjectGroup> objectGroups = tileMap.getObjectGroups();
            for (ObjectGroup objectGroup : objectGroups) {
                monsterHandler.handle(objectGroup, playingField);
            }
            
            playingField.addMoveValidator(new SampleMoveValidator(tileMap));
            // display the TileMapCanvas

            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(playingField);
            Scene scene = new Scene(borderPane, 500, 500);
            playingField.requestFocus();
            primaryStage.setTitle("Tile Engine Sample");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (JAXBException ex) {
            Logger.getLogger(SampleGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    private static final Logger LOG = Logger.getLogger(SampleGame.class.getName());
}
