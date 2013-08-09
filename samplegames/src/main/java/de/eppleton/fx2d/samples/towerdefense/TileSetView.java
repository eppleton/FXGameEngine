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
package de.eppleton.fx2d.samples.towerdefense;

import de.eppleton.fx2d.tileengine.TileSet;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

class TileSetView extends StackPane {

    Canvas canvas;
    TileSet cannons;
    TileSet bases;
    int selectedIndex = 0;
    Color selected = Color.rgb(100, 100, 255, .2);

    public TileSetView() {
    }

    public void setTileSet(final TileSet bases, final TileSet cannons) { 
        this.cannons = cannons;
        this.bases = bases;
        getChildren().clear();
        ImageView turretBases = new ImageView();
        turretBases.setImage(bases.getTileImage());
        
        ImageView turretCannons = new ImageView();
        turretCannons.setImage(cannons.getTileImage());
        
        getChildren().addAll(turretBases, turretCannons);
        
    canvas = new Canvas(cannons.getTileImage().getWidth(), cannons.getTileImage().getHeight());
        getChildren().add(canvas);
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                double x = t.getX();
                double y = t.getY();
                selectedIndex = (int) ((int) x / cannons.getTilewidth() + (((int) y / cannons.getTileheight()) * cannons.getNumColumns()));
                updateCanvas();
            }
        });
        updateCanvas();
    }

    public int getSelectedGid() {
        if (bases == null) {
            return -1;
        }
        return bases.getFirstgid() + selectedIndex;
    }
    
    public int getSelectedIndex(){
        return selectedIndex;
    }

    public void updateCanvas() {
        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
        graphicsContext2D.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                if (selectedIndex >= 0) {
                    graphicsContext2D.setFill(selected);
                    int x = selectedIndex % cannons.getNumColumns();
                    int y = selectedIndex / cannons.getNumColumns();
                    graphicsContext2D.fillRect(x * cannons.getTilewidth(), y * cannons.getTileheight(), cannons.getTilewidth(), cannons.getTileheight());
                }
    }
}
