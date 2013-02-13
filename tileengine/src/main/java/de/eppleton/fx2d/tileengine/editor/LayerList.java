/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine.editor;

import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileMapLayer;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author antonepple
 */
class LayerList extends BorderPane {

    Slider opacity;
    ToolBar toolbar;
    public ListView<TileMapLayer> listView;
    public ObservableList<TileMapLayer> layerList;
    TileMap tileMap;

    public LayerList(TileMap map, ArrayList<TileMapLayer> layers) {
        this.tileMap = map;
        opacity = new Slider(0, 1, 1);
        HBox box = new HBox();
        box.getChildren().addAll(new Label("Opacity: "), opacity);
        setTop(box);
        layerList = FXCollections.observableArrayList(layers);
        listView = new ListView<TileMapLayer>();
        listView.setItems(layerList);
        if (!layerList.isEmpty()) {
            listView.getSelectionModel().select(layerList.get(0));
        }
        Callback<TileMapLayer, ObservableValue<Boolean>> getProperty = new Callback<TileMapLayer, ObservableValue<Boolean>>() {
            @Override
            public BooleanProperty call(TileMapLayer layer) {

                return layer.getVisibleProperty();

            }
        };
        StringConverter stringConverter = new StringConverter<TileMapLayer>() {
            @Override
            public String toString(TileMapLayer t) {
                return t.getName();
            }

            @Override
            public TileMapLayer fromString(String string) {
                for (TileMapLayer tileMapLayer : layerList) {
                    if (string.equals(tileMapLayer.getName())) {
                        return tileMapLayer;
                    }
                }
                return null;
            }
        };
        Callback<ListView<TileMapLayer>, ListCell<TileMapLayer>> forListView = CheckBoxListCell.forListView(getProperty, stringConverter);

        listView.setCellFactory(forListView);
        setCenter(listView);
        setBottom(initToolBar());

    }

    private Node initToolBar() {
        Button newLayer = new Button("new");
        Button up = new Button("up");
        Button down = new Button("down");
        Button copy = new Button("copy");
        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                ObservableList<TileMapLayer> selectedItems = listView.getSelectionModel().getSelectedItems();
                for (TileMapLayer tileMapLayer : selectedItems) {
                    TileMapLayer copied = copy(tileMapLayer);
                    tileMap.getLayers().add(copied);
                    layerList.add(copied);
                }
            }
        });

        Button trash = new Button("trash");
        toolbar = new ToolBar(newLayer, up, down, copy, trash);
        return toolbar;
    }

    private TileMapLayer copy(TileMapLayer tileMapLayer) {
        TileMapLayer copy = new TileMapLayer();
        copy.setName("copy of " + tileMapLayer.getName());
        copy.setOpacity(tileMapLayer.getOpacity());
        copy.setVisible(tileMapLayer.isVisible());
        copy.setData(tileMapLayer.getData());
        return copy;
    }

    TileMapLayer getSelected() {
        return listView.getSelectionModel().getSelectedItems().get(0);
    }
}
