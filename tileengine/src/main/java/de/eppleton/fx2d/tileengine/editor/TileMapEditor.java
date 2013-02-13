/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine.editor;

import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileMapLayer;
import de.eppleton.fx2d.tileengine.TileMapReader;
import de.eppleton.fx2d.tileengine.TileSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.xml.bind.JAXBException;

/**
 *
 * @author antonepple
 */
public class TileMapEditor extends Application {

    private TileMap tileMap;
    public LayerList layerList;
    public TileSetChooser tileSets;
    public MouseHandler mouseHandler;

    private ToolBar initToolBar() {
        Button newMap = new Button("New");
        Button open = new Button("Open");
        Button save = new Button("Save");
        Separator separator = new Separator();

        ToolBar toolBar = new ToolBar(newMap, open, save, separator);
        return toolBar;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Node initSplitPane() {
        SplitPane pane = new SplitPane();
        pane.setOrientation(Orientation.VERTICAL);
        layerList = new LayerList(tileMap, tileMap.getLayers());
        ArrayList<TileSet> tileSetlist = tileMap.getTileSetlist();
        tileSets = new TileSetChooser(tileSetlist);
        pane.getItems().addAll(layerList, tileSets);
        return pane;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        try {
            tileMap = TileMapReader.readMap("/assets/maps/test.tmx");
            GameCanvas canvas = new GameCanvas(tileMap.getTilewidth() * tileMap.getWidth(), tileMap.getHeight() * tileMap.getTileheight(), tileMap.getTilewidth() * tileMap.getWidth(), tileMap.getHeight() * tileMap.getTileheight());

            final ArrayList<TileMapLayer> layers = tileMap.getLayers();
            for (TileMapLayer tileMapLayer : layers) {
                canvas.addLayer(tileMapLayer);
            }
            BorderPane bp = new BorderPane();
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(canvas);
            bp.setCenter(scrollPane);
            bp.setTop(initToolBar());
            bp.setRight(initSplitPane());
            canvas.start();
            Scene scene = new Scene(bp, 1024, 762);
            primaryStage.setTitle("Tile Editor");
            primaryStage.setScene(scene);
            canvas.requestFocus();
            mouseHandler = new MouseHandler();
            canvas.setOnMousePressed(mouseHandler);
            canvas.setOnMouseDragged(mouseHandler);
            primaryStage.show();
        } catch (JAXBException ex) {
            Logger.getLogger(TileMapEditor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private class MouseHandler implements EventHandler<MouseEvent> {

        public MouseHandler() {
        }

        @Override
        public void handle(MouseEvent t) {
            TileMapLayer selectedLayer = layerList.getSelected();
            if (selectedLayer == null) {
                return;
            }
            double x = t.getX();
            double y = t.getY();
            System.out.println("t " + t);
            int idx = (int) ((int) x / tileMap.getTilewidth() + (((int) y / tileMap.getTileheight()) * tileMap.getWidth()));
            selectedLayer.getData().setGid(idx, tileSets.getSelectedGid());
        }
    }
}
