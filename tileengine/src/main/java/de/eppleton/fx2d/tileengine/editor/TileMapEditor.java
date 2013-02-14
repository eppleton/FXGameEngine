/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine.editor;

import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.Layer;
import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileMapLayer;
import de.eppleton.fx2d.tileengine.TileMapReader;
import de.eppleton.fx2d.tileengine.TileSet;
import de.eppleton.fx2d.tileengine.algorithms.AStar;
import de.eppleton.fx2d.tileengine.algorithms.AStar.PathNode;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.xml.bind.JAXBException;

/**
 *
 * @author antonepple
 */
public class TileMapEditor extends Application implements ListChangeListener<TileMapLayer> {

    private TileMap tileMap;
    public LayerList layerList;
    public TileSetChooser tileSets;
    private DrawTileHandler drawTileHandler;
    private EraseHandler eraseHandler;
    public GameCanvas canvas;
    AStar.PathNode path;
    public String fileURL = "/Users/antonepple/NetBeansProjects/FXGames/tileengine/src/main/resources/assets/maps/test.tmx";

    private ToolBar initToolBar() {
        Button newMap = new Button("New");
        Button open = new Button("Open");
        Button save = new Button("Save");
        save.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                try {
                    TileMapReader.writeMap(tileMap, fileURL);
                } catch (JAXBException ex) {
                    Logger.getLogger(TileMapEditor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TileMapEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Separator separator = new Separator();
        ToggleButton draw = new ToggleButton("draw");
        drawTileHandler = new DrawTileHandler();
        eraseHandler = new EraseHandler();
        draw.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {

                canvas.setOnMousePressed(drawTileHandler);
                canvas.setOnMouseDragged(drawTileHandler);
            }
        });
        ToggleButton erase = new ToggleButton("erase");
        erase.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                canvas.setOnMousePressed(eraseHandler);
                canvas.setOnMouseDragged(eraseHandler);
            }
        });
        ToggleGroup group = new ToggleGroup();
        draw.setToggleGroup(group);
        erase.setToggleGroup(group);
        draw.setSelected(true);
        ToolBar toolBar = new ToolBar(newMap, open, save, separator, draw, erase);
        return toolBar;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Node initSplitPane() {
        SplitPane pane = new SplitPane();
        pane.setOrientation(Orientation.VERTICAL);
        layerList = new LayerList(tileMap);
        ArrayList<TileSet> tileSetlist = tileMap.getTileSetlist();
        tileSets = new TileSetChooser(tileSetlist);
        pane.getItems().addAll(layerList, tileSets);
        return pane;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            
            tileMap = TileMapReader.readMapFromFile(fileURL);
            canvas = new GameCanvas(tileMap.getTilewidth() * tileMap.getWidth(), tileMap.getHeight() * tileMap.getTileheight(), tileMap.getTilewidth() * tileMap.getWidth(), tileMap.getHeight() * tileMap.getTileheight());
            Node rightSide = initSplitPane();
            final ObservableList<TileMapLayer> layers = layerList.getLayerList();

            for (TileMapLayer tileMapLayer : layers) {
                canvas.addLayer(tileMapLayer);
            }
            canvas.addLayer(new AStarLayer());

            layers.addListener(this);
            BorderPane bp = new BorderPane();
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(canvas);
            SplitPane split = new SplitPane();
            bp.setCenter(split);
            bp.setTop(initToolBar());
            split.getItems().addAll(scrollPane, rightSide);
            canvas.start();
            Scene scene = new Scene(bp, 1024, 762);
            primaryStage.setTitle("Tile Editor");
            primaryStage.setScene(scene);
            canvas.requestFocus();

            primaryStage.show();
        } catch (JAXBException ex) {
            Logger.getLogger(TileMapEditor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void onChanged(Change<? extends TileMapLayer> change) {
        change.next();
        if (change.wasPermutated()) {
            int from = change.getFrom();
            int to = change.getTo();
        }
        if (change.wasRemoved()) {
            List<? extends TileMapLayer> removed = change.getRemoved();
            for (TileMapLayer tileMapLayer : removed) {
                canvas.removeLayer(tileMapLayer);
            }
        }
        if (change.wasAdded()) {
            List<? extends TileMapLayer> addedSubList = change.getAddedSubList();
            for (TileMapLayer tileMapLayer : addedSubList) {
                ObservableList<? extends TileMapLayer> list = change.getList();
                int idx = 0;
                for (TileMapLayer tileMapLayer1 : list) {
                    if (tileMapLayer == tileMapLayer1) {
                        canvas.addLayer(idx, tileMapLayer);
                        break;
                    }
                    idx++;
                }

            }
        }
    }

    private class EraseHandler implements EventHandler<MouseEvent> {

        AStar.AStarTile start = new AStar.AStarTile(0, tileMap.getHeight() / 2);
        AStar.AStarTile end = new AStar.AStarTile(tileMap.getWidth() - 1, tileMap.getHeight() / 2);

        @Override
        public void handle(MouseEvent t) {
            TileMapLayer selectedLayer = layerList.getSelected();
            if (selectedLayer == null) {
                return;
            }
            double x = t.getX();
            double y = t.getY();

            int idx = (int) ((int) x / tileMap.getTilewidth() + (((int) y / tileMap.getTileheight()) * tileMap.getWidth()));
            selectedLayer.getData().setGid(idx, 0);
            path = AStar.getPath(tileMap, selectedLayer, start, end);
        }
    }

    private class DrawTileHandler implements EventHandler<MouseEvent> {

        AStar.AStarTile start = new AStar.AStarTile(0, tileMap.getHeight() / 2);
        AStar.AStarTile end = new AStar.AStarTile(tileMap.getWidth() - 1, tileMap.getHeight() / 2);

        @Override
        public void handle(MouseEvent t) {
            TileMapLayer selectedLayer = layerList.getSelected();
            if (selectedLayer == null) {
                return;
            }
            double x = t.getX();
            double y = t.getY();

            int idx = (int) ((int) x / tileMap.getTilewidth() + (((int) y / tileMap.getTileheight()) * tileMap.getWidth()));
            selectedLayer.getData().setGid(idx, tileSets.getSelectedGid());
            path = AStar.getPath(tileMap, selectedLayer, start, end);

        }
    }

    private class AStarLayer extends Layer {

        public AStarLayer() {
        }
        Color pathColor = Color.rgb(255, 100, 100, .2);

        @Override
        public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
            PathNode start = path;
            if (start != null) {
                graphicsContext.setFill(pathColor);
                graphicsContext.fillRect(start.getX() * tileMap.getTilewidth(), start.getY() * tileMap.getTileheight(), tileMap.getTilewidth(), tileMap.getTileheight());
                while (start.getParent() != null) {
                    start = start.getParent();
                    graphicsContext.fillRect(start.getX() * tileMap.getTilewidth(), start.getY() * tileMap.getTileheight(), tileMap.getTilewidth(), tileMap.getTileheight());
                }
            } else {
                System.out.print(".");
            }
        }
    }
}
