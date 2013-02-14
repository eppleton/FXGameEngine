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
package de.eppleton.fx2d.tileengine.editor;

import de.eppleton.fx2d.tileengine.TileSet;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

class TileSetView extends StackPane {

    ImageView imageView;
    Canvas canvas;
    TileSet set;
    int selectedIndex = 0;
    Color selected = Color.rgb(100, 100, 255, .005);

    public TileSetView() {
    }

    public void setTileSet(final TileSet set) {
        this.set = set;
        getChildren().clear();
        imageView = new ImageView();
        getChildren().add(imageView);
        imageView.setImage(set.getTileImage());
        canvas = new Canvas(set.getTileImage().getWidth(), set.getTileImage().getWidth());
        getChildren().add(canvas);
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                double x = t.getX();
                double y = t.getY();
                selectedIndex = (int) ((int) x / set.getTilewidth() + (((int) y / set.getTileheight()) * set.getNumColumns()));
         
                updateCanvas();
            }
        });
        updateCanvas();
    }

    public int getSelectedGid() {
        if (set == null) {
            return -1;
        }
        return set.getFirstgid() + selectedIndex;
    }

    public void updateCanvas() {
        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
        graphicsContext2D.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < set.getNumRows(); i++) {
            for (int j = 0; j < set.getNumColumns(); j++) {
                graphicsContext2D.setFill(Color.WHITE);
                graphicsContext2D.fillRect(j * set.getTilewidth(), 0, 1, set.getTileheight() * set.getNumRows());
                graphicsContext2D.fillRect(0, i * set.getTileheight(), set.getTilewidth() * set.getNumColumns(), 1);

                if (selectedIndex >= 0) {
                    graphicsContext2D.setFill(selected);
                    int x = selectedIndex % set.getNumColumns();
                    int y = selectedIndex / set.getNumColumns();
                    graphicsContext2D.fillRect(x * set.getTilewidth(), y * set.getTileheight(), set.getTilewidth(), set.getTileheight());
                }
            }
        }
    }
}
