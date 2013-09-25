/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samplegames.javafx;

import de.eppleton.fx2d.graphicsenvironment.JavaFXGraphicsEnvironment;
import de.eppleton.fx2d.javafx.app.JavaFXEventConverter;
import de.eppleton.fx2d.samples.towerdefense.TowerDefense;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
public class TowerDefenseGame extends Application {

    private String title = "My Game";
    private TowerDefense level;
    private Canvas canvas;
    public BorderPane pane;

    @Override
    public void start(Stage primaryStage) {
        canvas = new Canvas();
        GraphicsContext graphicsContext = new GraphicsContext(new JavaFXGraphicsEnvironment(canvas));
        level = new TowerDefense(graphicsContext, 460, 414, 460, 414);
        canvas.setHeight(level.getScreenHeight());
        canvas.setWidth(level.getScreenWidth());
        pane = new BorderPane();
        pane.setCenter(canvas);
        
        Scene scene = new Scene(pane, 500,414);
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
        pane.setRight(level.getPalette());
        primaryStage.setResizable(false);
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