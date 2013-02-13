/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine.editor;

import de.eppleton.fx2d.tileengine.TileSet;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author antonepple
 */
class TileSetChooser extends BorderPane {

    ObservableList<TileSet> tilesets;
    TileSetView view;
    public Button button;

    TileSetChooser(ArrayList<TileSet> tileSetlist) {
        tilesets = FXCollections.observableArrayList(tileSetlist);

        ChoiceBox box = new ChoiceBox(tilesets);
        box.setConverter(new StringConverter<TileSet>() {
            @Override
            public String toString(TileSet t) {
                return t.getName();
            }

            @Override
            public TileSet fromString(String string) {

                for (TileSet tileSet : tilesets) {
                    if (tileSet.getName().equals(string)) {
                        return tileSet;
                    }
                }
                return null;
            }
        });
        view = new TileSetView();
       
        box.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                view.setTileSet(tilesets.get(t1.intValue()));
            }
        });
        setTop(box);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(view);
        setCenter(scrollPane);
        if (!tilesets.isEmpty()){
            box.getSelectionModel().select(tilesets.get(0));
        }
    }

    public void addTileSet(TileSet set) {
        tilesets.add(set);
    }

    int getSelectedGid() {
        return view.getSelectedGid();
    }
}
