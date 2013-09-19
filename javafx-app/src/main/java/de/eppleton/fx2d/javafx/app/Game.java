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
package de.eppleton.fx2d.javafx.app;

import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.graphicsenvironment.JavaFXGraphicsEnvironment;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.java.html.canvas.GraphicsContext;

/**
 *
 * @author antonepple
 */
public abstract class Game extends Application {

 
    private String title = "My Game";
    private Level level;
    private Canvas canvas;
    public BorderPane pane;

    @Override
    public void start(Stage primaryStage) {
        canvas = new Canvas();
        level = getLevel(new GraphicsContext(new JavaFXGraphicsEnvironment(canvas)));
        canvas.setHeight(level.getScreenHeight());
        canvas.setWidth(level.getScreenWidth());
        pane = new BorderPane();
        pane.setCenter(canvas);
        Scene scene = new Scene(pane, level.getScreenWidth(), level.getScreenHeight());
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        canvas.requestFocus();
        canvas.addEventHandler(EventType.ROOT, new javafx.event.EventHandler<javafx.event.Event>() {
            @Override
            public void handle(javafx.event.Event t) {

                level.dispatchEvent(JavaFXEventConverter.copyForGame(t));
            }
        });
        level.start();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    protected abstract Level getLevel(GraphicsContext context);

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
}
