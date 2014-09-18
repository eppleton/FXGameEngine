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
package de.eppleton.fx2d.samples.towerdefense;

import de.eppleton.fx2d.tileengine.Tile;
import de.eppleton.fx2d.tileengine.TileSet;
import java.util.ArrayList;
import java.util.Properties;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

final class Palette extends StackPane {

    private final Canvas canvas;
    private final TileSet cannons;
    private final TileSet bases;
    private final Color selected = Color.rgb(100, 100, 255, .2);
    private int selectedIndex = 0;

    Palette(final TileSet bases, final TileSet cannons) {
        this.cannons = cannons;
        this.bases = bases;
        ArrayList<Tile> tileList = bases.getTileList();
        for (Tile tile : tileList) {
            System.out.println("tile "+tile.getId());
        }
        getChildren().clear();
        ImageView turretBases = new ImageView();
        turretBases.setImage(new Image(bases.getTileImage().getSrc()));
        ImageView turretCannons = new ImageView();
        turretCannons.setImage(new Image(cannons.getTileImage().getSrc()));
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

    public Properties getSelectedProperties(){
        return bases.getTileList().get(selectedIndex).getProperties();
    }
    
    public int getSelectedGid() {
        if (bases == null) {
            return -1;
        }
        return bases.getFirstgid() + selectedIndex;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void updateCanvas() {
        GraphicsContext2D graphicsContext2D = canvas.getGraphicsContext2D2D();
        graphicsContext2D.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (selectedIndex >= 0) {
            graphicsContext2D.setFill(selected);
            int x = selectedIndex % cannons.getNumColumns();
            int y = selectedIndex / cannons.getNumColumns();
            graphicsContext2D.fillRect(x * cannons.getTilewidth(), y * cannons.getTileheight(), cannons.getTilewidth(), cannons.getTileheight());
        }
    }
}
