/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samples.towerdefense;

import com.sun.javafx.perf.PerformanceTracker;
import de.eppleton.fx2d.DebugLayer;
import de.eppleton.fx2d.tileengine.action.RotatingTileSetAnimation;
import de.eppleton.fx2d.tileengine.action.LookAheadTileSetAnimation;
import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.Layer;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.Behavior;
import de.eppleton.fx2d.action.SpriteBehavior;
import de.eppleton.fx2d.tileengine.ObjectGroup;
import de.eppleton.fx2d.tileengine.TObject;
import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileMapLayer;
import de.eppleton.fx2d.tileengine.TileMapReader;
import de.eppleton.fx2d.tileengine.TileSet;
import de.eppleton.fx2d.tileengine.action.TileSetAnimation;
import de.eppleton.fx2d.tileengine.algorithms.AStar;
import java.util.ArrayList;
import java.util.Properties;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.openide.util.Lookup;

/**
 *
 * @author antonepple
 */
public class TowerDefenseGame extends Application {

    private TileMap tileMap;
    public GameCanvas canvas;
    ListView listView;
    public String fileURL = "/de/eppleton/fx2d/towerdefense/towerdefense.tmx";
    AStar.PathNode attackPath;

    public static void main(String[] args) {
        launch(args);
    }
    private TileSetView turretView;
    private TileMapLayer turretBaseLayer;
    private TileMapLayer platformLayer;
    private double spawnpointTileX;
    private double spawnpointTileY;
    private double targetX;
    private double targetY;
    private Image backgroundImage;
    private long evaluationInterval;

    @Override
    public void start(Stage stage) throws Exception {

        tileMap = TileMapReader.readMap(fileURL);
        backgroundImage = tileMap.getTileSet("background").getTileImage();
        canvas = new GameCanvas(tileMap.getTilewidth() * tileMap.getWidth(), tileMap.getHeight() * tileMap.getTileheight(), tileMap.getTilewidth() * tileMap.getWidth(), tileMap.getHeight() * tileMap.getTileheight());

        // add all the layers
        canvas.addLayer(new Layer() {
            @Override
            public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
                graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillRect(0, 0, width, height);
                graphicsContext.drawImage(backgroundImage, 0, 0);
            }
        });

        ArrayList<TileMapLayer> layers = tileMap.getLayers();
        for (TileMapLayer tileMapLayer : layers) {
            canvas.addLayer(tileMapLayer);
            if (tileMapLayer.getName().equals("turret-bases")) {
                turretBaseLayer = tileMapLayer;
            } else if (tileMapLayer.getName().equals("terrain")) {
                platformLayer = tileMapLayer;
            }
        }

        final TileSet cannons = tileMap.getTileSet("turret-cannons");
        // get the tileset
        TileSet bases = tileMap.getTileSet("turret-bases");
        TileSet bullets = tileMap.getTileSet("pellet");
        final TileSetAnimation shoot = new TileSetAnimation(bullets, new int[]{0}, .00000001f);
        turretView = new TileSetView();
        turretView.setTileSet(bases, cannons);

        Sprite bullet = new Sprite(canvas, shoot, "bulletx", 1, 1,
                8, 8, Lookup.EMPTY);
        bullet.setVelocityX(1);
        bullet.addBehaviour(new SpriteBehavior() {

            @Override
            public boolean perform(Sprite sprite) {
                if (sprite.getX()>= sprite.getParent().getWidth())sprite.setVelocityX(-1);
                if (sprite.getX()<=0)sprite.setVelocityX(1);
                return true; //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public long getEvaluationInterval() {
                return 1000; //To change body of generated methods, choose Tools | Templates.
            }
            
            
        
        });

        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            int cannoncount = 0;

            @Override
            public void handle(MouseEvent t) {

                double x = t.getX();
                double y = t.getY();
                int tileGridX = (int) x / tileMap.getTilewidth();
                int tileGridY = (int) y / tileMap.getTileheight();
                int idx = (tileGridX + (tileGridY * tileMap.getWidth()));
                if (platformLayer.getGid(idx) != 0 && turretBaseLayer.getGid(idx) == 0) {
                    turretBaseLayer.getData().setGid(idx, turretView.getSelectedGid());
//                    System.out.println("selected cannon: " + turretView.getSelectedIndex());
                    CannonSprite cannonSprite = new CannonSprite(canvas, new RotatingTileSetAnimation(cannons, new int[]{turretView.getSelectedIndex()}, .1f), shoot, "cannon " + (cannoncount++), tileGridX * tileMap.getTilewidth(), tileGridY * tileMap.getTileheight(), 46, 46);


                }
            }
        });

        VBox palette = new VBox();
        palette.getChildren().addAll(turretView);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(palette);

        // handle the Objects
        ArrayList<ObjectGroup> objectGroups = tileMap.getObjectGroups();
        for (ObjectGroup objectGroup : objectGroups) {
            for (final TObject tObject : objectGroup.getObjectLIst()) {
                if (tObject.getName().equals("spawnpoint")) {

                    Properties properties = tObject.getProperties();
                    evaluationInterval = Long.parseLong(properties.getProperty("delay"));

                    spawnpointTileX = tObject.getX() / tileMap.getTilewidth();
                    spawnpointTileY = tObject.getY() / tileMap.getTilewidth();

                }

                if (tObject.getName().equals("target")) {

                    targetX = tObject.getX() / tileMap.getTilewidth();
                    targetY = tObject.getY() / tileMap.getTileheight();

                }
            }
        }
        AStar.AStarTile start = new AStar.AStarTile((int) spawnpointTileX, (int) spawnpointTileY);
        AStar.AStarTile end = new AStar.AStarTile((int) targetX, (int) targetY);
        attackPath = AStar.getPath(tileMap, platformLayer, end, start);

        final TileSet enemy1 = tileMap.getTileSet("enemy1");
        final TileSetAnimation tileSetAnimation = new LookAheadTileSetAnimation(enemy1, new int[]{0, 1, 2, 3, 4, 5}, 10f);

        Behavior monsterSpawnBehavior = new Behavior() {
            int enemyCount = 0;

            @Override
            public boolean perform(GameCanvas canvas, long nanos) {
                new EnemySprite(canvas, tileSetAnimation, "enemy" + (enemyCount++), ((int) spawnpointTileX) * tileMap.getTilewidth(), ((int) spawnpointTileY) * tileMap.getTileheight(), 46, 46, attackPath);
                return true;
            }
        };
        monsterSpawnBehavior.setEvaluationInterval(evaluationInterval);

        canvas.addBehaviour(monsterSpawnBehavior);
        Scene scene = new Scene(borderPane);

       canvas.addLayer(new DebugLayer(Color.WHITE, canvas));


        stage.setScene(scene);

        canvas.requestFocus();
        canvas.start();
        stage.show();
    }
}
