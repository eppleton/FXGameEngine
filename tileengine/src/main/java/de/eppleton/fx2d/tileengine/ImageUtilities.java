/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 *
 * @author antonepple
 */
public class ImageUtilities {

    /**
     * Used to decorate an Image. E.g. Take a picture of the player and add a
     * hat.
     *
     * @param bottom The image that will be treated as bottom layer
     * @param top The image that will be treated as top layer
     * @return
     */
    public static Image merge(Image bottom, Image top) {
        
        ImageView bottomView = new ImageView(bottom);
        ImageView topView = new ImageView(top);
        StackPane stackPane = new StackPane();
        
        stackPane.getChildren().add(bottomView);
        stackPane.getChildren().add(topView);
        // Don't remove, adding to Scene is required for snapshot
        Scene scene = new Scene(stackPane);
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);
        return stackPane.snapshot(snapshotParameters, null);

    }

    private ImageUtilities() {
    }
}
