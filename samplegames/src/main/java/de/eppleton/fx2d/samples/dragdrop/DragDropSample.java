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
package de.eppleton.fx2d.samples.dragdrop;

import de.eppleton.fx2d.Camera;
import de.eppleton.fx2d.Game;
import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.SpriteLayer;
import de.eppleton.fx2d.action.ActionFactory;
import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import org.openide.util.Lookup;

/**
 *
 * @author antonepple
 */
public class DragDropSample extends Game {

    @Override
    protected void initGame() {
        final GameCanvas canvas = getCanvas();

        canvas.addLayer(new SpriteLayer());
        canvas.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                Dragboard db = canvas.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putString("test");
                db.setContent(content);

                event.consume();
            }
        });

        canvas.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                /* accept it only if it is not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() == canvas
                        && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });
        Sprite player = new Sprite(canvas, Sprite.NO_ANIMATION, "Player", 100, 240, 40, 30, Lookup.EMPTY);
        player.addAction(KeyCode.LEFT, ActionFactory.createMoveAction(Sprite.NO_ANIMATION, "left", -4, 0, 0, 0));
        player.addAction(KeyCode.RIGHT, ActionFactory.createMoveAction(Sprite.NO_ANIMATION, "right", 4, 0, 0, 0));
        player.addAction(KeyCode.UP, ActionFactory.createMoveAction(Sprite.NO_ANIMATION, "up", 0, -4, 0, 0));
        player.addAction(KeyCode.DOWN, ActionFactory.createMoveAction(Sprite.NO_ANIMATION, "down", 0, 4, 0, 0));
        canvas.setCamera(new Camera(player.getXProperty(), player.getYProperty()));
        canvas.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
