/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samples.towerdefense;

import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.ImageLayer;
import de.eppleton.fx2d.IntegerProperty;
import de.eppleton.fx2d.Layer;
import de.eppleton.fx2d.MouseClick;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.StackedRenderer;
import de.eppleton.fx2d.action.Behavior;
import de.eppleton.fx2d.action.MouseEventHandler;
import de.eppleton.fx2d.tileengine.ObjectGroup;
import de.eppleton.fx2d.tileengine.TObject;
import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileMapException;
import de.eppleton.fx2d.tileengine.TileMapLayer;
import de.eppleton.fx2d.tileengine.TileMapReader;
import de.eppleton.fx2d.tileengine.TileSet;
import de.eppleton.fx2d.tileengine.action.TileSetAnimation;
import de.eppleton.fx2d.tileengine.algorithms.AStar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.openide.util.Lookup;

/**
 *
 * @author antonepple
 */
public class TowerDefenseGame extends Application {

    private IntegerProperty score = new IntegerProperty(0);
    private TileMap tileMap;
    private GameCanvas canvas;
    private String fileURL = "/de/eppleton/fx2d/towerdefense/towerdefense.tmx";
    private AStar.PathNode attackPath;
    private Palette palette;
    private TileMapLayer turretBaseLayer;
    private TileMapLayer platformLayer;
    private double spawnpointTileX;
    private double spawnpointTileY;
    private double targetX;
    private double targetY;
    private Image backgroundImage;
    private List<Wave> waves = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }
    private int currentWave;

    @Override
    public void start(Stage stage) throws Exception {
        // read the Map
        tileMap = TileMapReader.readMap(fileURL);
        backgroundImage = tileMap.getTileSet("background").getTileImage();

        // create the canvas
        canvas = new GameCanvas(tileMap.getTilewidth() * tileMap.getWidth(), tileMap.getHeight() * tileMap.getTileheight(), tileMap.getTilewidth() * tileMap.getWidth(), tileMap.getHeight() * tileMap.getTileheight());

        // add all the layers
        canvas.addLayer(new ImageLayer(backgroundImage));
        ArrayList<TileMapLayer> layers = tileMap.getLayers();
        for (TileMapLayer tileMapLayer : layers) {
            canvas.addLayer(tileMapLayer);
        }
        canvas.addLayer(new HUD());
        final Sprite button = new Sprite(canvas, "button", tileMap.getWidthInPixels() - 30, 20, 20, 20, Lookup.EMPTY);
        button.setOnMouseClicked(new MouseEventHandler() {
            @Override
            public void handle(MouseClick click) {
                startWave();
                button.die();
            }
        });
        canvas.addSprite(button);

        turretBaseLayer = (TileMapLayer) canvas.getLayer("turret-bases");
        platformLayer = (TileMapLayer) canvas.getLayer("terrain");

        // initialize the Palette
        TileSet bullets = tileMap.getTileSet("pellet");
        final TileSetAnimation shoot = new TileSetAnimation(bullets, new int[]{0}, .00000001f);
        final TileSet cannons = tileMap.getTileSet("turret-cannons");
        TileSet bases = tileMap.getTileSet("turret-bases");
        palette = new Palette(bases, cannons);

        // add Handler for placing Turrets
        canvas.setOnMousePressed(new TurretHandler(cannons, shoot));

        // read spawnpoints, target, turret-properties,...
        readObjectProperties();
        calculateAttackPath();

        // setup the basic Layout
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(palette);

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("/de/eppleton/fx2d/towerdefense/towerdefense.css");
        stage.setScene(scene);

        // start the game
        canvas.requestFocus();
        canvas.start();
        stage.show();
    }

    private void readObjectProperties() throws NumberFormatException, TileMapException {
        final TileSet enemy1 = tileMap.getTileSet("enemy1");
        final TileSetAnimation tileSetAnimation = new TileSetAnimation(enemy1, new int[]{0, 1, 2, 3, 4, 5}, 10f);
        final StackedRenderer stacked = new StackedRenderer(tileSetAnimation, new HealthBarRenderer());
        // handle the Objects
        ArrayList<ObjectGroup> objectGroups = tileMap.getObjectGroups();
        for (ObjectGroup objectGroup : objectGroups) {
            for (final TObject tObject : objectGroup.getObjectLIst()) {
                if (tObject.getName().equals("spawnpoint")) {
                    Properties properties = tObject.getProperties();
                    spawnpointTileX = tObject.getX() / tileMap.getTilewidth();
                    spawnpointTileY = tObject.getY() / tileMap.getTilewidth();
                    for (int i = 0; i < properties.size() / 2; i++) {
                        long evaluationInterval = Long.parseLong(properties.getProperty("wave" + i + "."
                                + "delay"));

                        waves.add(new Wave(stacked, evaluationInterval, properties.getProperty("wave" + i + "."
                                + "monsters").split(",")));
                    }
                    
                }
                if (tObject.getName().equals("target")) {
                    targetX = tObject.getX() / tileMap.getTilewidth();
                    targetY = tObject.getY() / tileMap.getTileheight();
                }
            }
        }
    }

    private void startWave() {
        // setup Enemy Spawning
        Wave current = waves.get(currentWave++);
        canvas.addBehaviour(current);
    }
    
    private void stopWave(){
        System.out.println("Wave finished!");
    }

    private void calculateAttackPath() {
        // calculate the attack path
        AStar.AStarTile start = new AStar.AStarTile((int) spawnpointTileX, (int) spawnpointTileY);
        AStar.AStarTile end = new AStar.AStarTile((int) targetX, (int) targetY);
        attackPath = AStar.getPath(tileMap, platformLayer, end, start);
    }

    private class ScanForLastEnemyBevavior extends Behavior {

        public ScanForLastEnemyBevavior() {
            setEvaluationInterval(100000000l );
        }

        @Override
        public boolean perform(GameCanvas canvas, long nanos) {
            Collection<Sprite> sprites = canvas.getSprites();
            for (Sprite sprite : sprites) {
                if (sprite instanceof EnemySprite) return true;
            }
            
            
            stopWave();
            return false;
        }
    
        
    
    }

    private class HUD extends Layer {

        @Override
        public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
            graphicsContext.setFill(Color.RED);
            graphicsContext.setFont(Font.font("OricNeo", FontWeight.BOLD, 24));
            graphicsContext.fillText("Score: " + score, 10, 28);
        }
    }

    private class TurretHandler implements EventHandler<MouseEvent> {

        private final TileSet cannons;
        private final TileSetAnimation shoot;

        public TurretHandler(TileSet cannons, TileSetAnimation shoot) {
            this.cannons = cannons;
            this.shoot = shoot;
        }
        int cannoncount = 0;

        @Override
        public void handle(MouseEvent t) {

            double x = t.getX();
            double y = t.getY();
            int tileGridX = (int) x / tileMap.getTilewidth();
            int tileGridY = (int) y / tileMap.getTileheight();
            int idx = (tileGridX + (tileGridY * tileMap.getWidth()));
            if (platformLayer.getGid(idx) != 0 && turretBaseLayer.getGid(idx) == 0) {
                turretBaseLayer.getData().setGid(idx, palette.getSelectedGid());
                new CannonSprite(canvas, palette.getSelectedProperties(), new TileSetAnimation(cannons, new int[]{palette.getSelectedIndex()}, .1f), shoot, "cannon " + (cannoncount++), tileGridX * tileMap.getTilewidth(), tileGridY * tileMap.getTileheight(), 46, 46);
            }
        }
    }

    private class Wave extends Behavior {

        private final StackedRenderer stacked;
        private final ArrayList<EnemySprite> enemySprites;
        private int enemyIndex;
        int enemyCount = 0;

        public Wave(StackedRenderer stacked, long evaluationInterval, String[] waveProperties) {
            this.stacked = stacked;
            setEvaluationInterval(evaluationInterval);
            enemySprites = new ArrayList<>();
            for (int i = 0; i < waveProperties.length; i++) {
                int numEnemies = Integer.parseInt(waveProperties[i]);
                for (int j = 0; j < numEnemies; j++) {
                    enemySprites.add(new EnemySprite(canvas, score, new Properties(), stacked, "enemy" + (enemyCount++), ((int) spawnpointTileX) * tileMap.getTilewidth(), ((int) spawnpointTileY) * tileMap.getTileheight(), 46, 46));
                }
            }
        }

        @Override
        public boolean perform(GameCanvas canvas, long nanos) {
            if (enemyIndex < enemySprites.size()) {
                EnemySprite nextEnemy = enemySprites.get(enemyIndex);
                nextEnemy.setAttackPath(attackPath);
                canvas.addSprite(nextEnemy);
            } else {
                canvas.addBehaviour(new ScanForLastEnemyBevavior());
            }
            return !(enemySprites.size() == enemyIndex++);
        }
        
        
    }
}
