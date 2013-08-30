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
package de.eppleton.fx2d.graphicsenvironment;

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
