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
package de.eppleton.fx2d;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author antonepple
 */
public class Game extends Application {

    private double playfieldWidth = 800;
    private double playfieldHeight = 600;
    private double viewPortWidth = 800;
    private double viewPortHeight = 600;
    private String title = "My Game";
    private GameCanvas canvas;
    public BorderPane pane;

    @Override
    public void start(Stage primaryStage) {
        canvas = new GameCanvas(getPlayfieldWidth(), getPlayfieldHeight(), getViewPortWidth(), getViewPortHeight());
        pane = new BorderPane();
        initGame();
        pane.setCenter(canvas);
        Scene scene = new Scene(pane, getViewPortWidth(), getViewPortHeight());
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        canvas.requestFocus();
        primaryStage.show();
    }

    public void setBottom(Node bottom) {
        pane.setBottom(bottom);
    }

    public void setRight(Node right) {
        pane.setRight(right);
    }

    public void setLeft(Node left) {
        pane.setLeft(left);

    }

    public void setTop(Node top) {
        pane.setTop(top);

    }

    public GameCanvas getCanvas() {
        return canvas;
    }

    /**
     * override this to provide your own dimensions
     *
     * @return
     */
    protected double getPlayfieldWidth() {
        return playfieldWidth;
    }

    /**
     * override this to provide your own dimensions
     *
     * @return
     */
    protected double getPlayfieldHeight() {
        return playfieldHeight;
    }

    /**
     * override this to provide your own dimensions
     *
     * @return
     */
    protected double getViewPortWidth() {
        return viewPortWidth;
    }

    /**
     * override this to provide your own dimensions
     *
     * @return
     */
    protected double getViewPortHeight() {
        return viewPortHeight;
    }

    protected String getTitle() {
        return title;
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    protected void initGame() {
        throw new UnsupportedOperationException("implement this!");
    }
}
