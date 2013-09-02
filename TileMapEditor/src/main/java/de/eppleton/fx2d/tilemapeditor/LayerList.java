/**
 * This file is part of FXGameEngine A Game Engine written in JavaFX Copyright
 * (C) 2012 Anton Epple <info@eppleton.de>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. Look for COPYING file in the top folder. If not, see
 * http://opensource.org/licenses/GPL-2.0.
 *
 * For alternative licensing or use in closed source projects contact Anton
 * Epple
 * <info@eppleton.de>
 */
package de.eppleton.fx2d.tilemapeditor;

import de.eppleton.fx2d.tileengine.Data;
import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileMapLayer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

    public LayerList(TileMap map) {
        this.tileMap = map;
        opacity = new Slider(0, 1, 1);
        HBox box = new HBox();
        box.getChildren().addAll(new Label("Opacity: "), opacity);
        setTop(box);
        layerList = FXCollections.observableArrayList(tileMap.getLayers());
        listView = new ListView<TileMapLayer>();
        listView.setItems(layerList);
        if (!layerList.isEmpty()) {
            listView.getSelectionModel().select(layerList.get(0));
        }
        Callback<TileMapLayer, ObservableValue<Boolean>> getProperty = new Callback<TileMapLayer, ObservableValue<Boolean>>() {
            @Override
            public BooleanProperty call(TileMapLayer layer) {
                // @TODO: this is broken...
                return new SimpleBooleanProperty(layer.getVisibleProperty().getValue());

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

    public ObservableList<TileMapLayer> getLayerList() {
        return layerList;
    }

    private Node initToolBar() {
        Button newLayer = new Button("new");
        Button up = new Button("up");
        up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                TileMapLayer selected = listView.getSelectionModel().getSelectedItems().get(0);
                final int selectedIndex = listView.getSelectionModel().getSelectedIndex();
                if (selectedIndex == 0 || layerList.size() <= 1) {
                    return;
                }
                layerList.remove(selected);
                layerList.add(selectedIndex - 1, selected);
            }
        });
        Button down = new Button("down");
        down.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                TileMapLayer selected = listView.getSelectionModel().getSelectedItems().get(0);
                final int selectedIndex = listView.getSelectionModel().getSelectedIndex();

                if (selectedIndex == layerList.size() - 1 || layerList.size() <= 1) {
                    return;
                }
                layerList.remove(selected);
                layerList.add(selectedIndex + 1, selected);
            }
        });
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
        newLayer.setOnAction(new EventHandler<ActionEvent>() {
            private int i;

            @Override
            public void handle(ActionEvent t) {
                TileMapLayer newLayer = new TileMapLayer();
                init(newLayer);
                while (!testName("new Layer " + i)) {
                    i++;
                };
                newLayer.setName("new Layer " + i);
                tileMap.getLayers().add(newLayer);
                layerList.add(newLayer);
            }

            private boolean testName(String name) {
                for (TileMapLayer tileMapLayer : layerList) {
                    if (tileMapLayer.getName().equals(name)) {
                        return false;
                    }
                }
                return true;
            }
        });
        Button trash = new Button("trash");
        trash.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                ObservableList<TileMapLayer> selectedItems = listView.getSelectionModel().getSelectedItems();
                layerList.remove(selectedItems.get(0));
            }
        });
        toolbar = new ToolBar(newLayer, up, down, copy, trash);
        return toolbar;
    }

    private TileMapLayer copy(TileMapLayer tileMapLayer) {
        TileMapLayer copy = new TileMapLayer();
        copy.setName("copy of " + tileMapLayer.getName());
        copy.setOpacity(tileMapLayer.getOpacity());
        copy.setVisible(tileMapLayer.isVisible());
        copy.setTileMap(tileMap);
        Data newData = new Data();
        newData.setContent(tileMapLayer.getData().getContent());
        copy.setData(newData);
        return copy;
    }

    private void init(TileMapLayer newLayer) {
        newLayer.setOpacity(1);
        newLayer.setVisible(true);
        newLayer.setTileMap(tileMap);

        Data data = new Data();
        int tiles = tileMap.getWidth() * tileMap.getHeight();
        String content = "0";
        for (int i = 0; i < tiles - 1; i++) {
            content += ",0";

        }
        data.setContent(content);
        newLayer.setData(data);
    }

    TileMapLayer getSelected() {
        return listView.getSelectionModel().getSelectedItems().get(0);
    }
}
