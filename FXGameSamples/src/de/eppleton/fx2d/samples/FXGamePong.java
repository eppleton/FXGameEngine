/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samples;

import de.eppleton.fx2d.GameCanvas;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author antonepple
 */
public class FXGamePong extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameCanvas canvas = new GameCanvas(800, 600, 800, 600);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        Scene scene = new Scene(borderPane, 800, 600);
        canvas.addLayer(new SpriteLayer());
        canvas.addMoveValidator(new GameCanvas.MoveValidator() {
            @Override
            public boolean isValidMove(double x, double y, double width, double height) {
                if (x < 0 || x + width > 800 || y < 0 || y + height > 600) {
                    return false;
                }
                return true;
            }
        });
        canvas.addSprite(new Bat(canvas, "Player", 10, 262, 30, 75));
        canvas.addSprite(new Bat(canvas, "Computer", 750, 262, 30, 75));
        Ball ball = new Ball(canvas, null, 400, 300, 20, 20);
        ball.setVelocityX(2);
        ball.setVelocityY(2);
        canvas.addSprite(ball);
        primaryStage.setTitle("Play Pong");
        primaryStage.setScene(scene);
        primaryStage.show();
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
