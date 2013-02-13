/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
