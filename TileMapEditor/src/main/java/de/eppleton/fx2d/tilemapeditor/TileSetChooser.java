/**
 * This file is part of FXGameEngine 
 * A Game Engine written in JavaFX
 * Copyright (C) 2012 Anton Epple <info@eppleton.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://opensource.org/licenses/GPL-2.0.
 * 
 * For alternative licensing or use in closed source projects contact Anton Epple 
 * <info@eppleton.de>
 */
package de.eppleton.fx2d.tilemapeditor;

import de.eppleton.fx2d.tileengine.TileSet;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

/**
 *
 * @author antonepple
 */
class TileSetChooser extends BorderPane {

    ObservableList<TileSet> tilesets;
    TileSetView view;
    public Button button;

    TileSetChooser(List<TileSet> tileSetlist) {
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
