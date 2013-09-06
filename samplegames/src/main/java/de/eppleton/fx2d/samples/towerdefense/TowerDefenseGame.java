/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samples.towerdefense;

import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.ImageLayer;
import de.eppleton.fx2d.beans.IntegerProperty;
import de.eppleton.fx2d.Layer;

import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.StackedRenderer;
import de.eppleton.fx2d.action.Behavior;
import de.eppleton.fx2d.event.EventHandler;
import de.eppleton.fx2d.event.MouseEvent;
import de.eppleton.fx2d.tileengine.ObjectGroup;
import de.eppleton.fx2d.tileengine.TObject;
import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileMapException;
import de.eppleton.fx2d.tileengine.TileMapLayer;
import de.eppleton.fx2d.tileengine.TileMapReader;
import de.eppleton.fx2d.tileengine.TileSet;
import de.eppleton.fx2d.tileengine.action.TileSetAnimation;
import de.eppleton.fx2d.tileengine.algorithms.AStar;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;


import net.java.html.canvas.GraphicsContext;
import net.java.html.canvas.Image;
import net.java.html.canvas.Style.Color;
import org.openide.util.Lookup;

/**
 *
 * @author antonepple
 */
public class TowerDefenseGame extends Level {
   private IntegerProperty score = new IntegerProperty(0);
    private IntegerProperty hits = new IntegerProperty(0);
    private TileMap tileMap;
    private Level level;
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
    private String message;
    private int currentWave;
    private int maxHits = 2;
    private boolean levelLost;

    public TowerDefenseGame(GraphicsContext graphicsContext, double playfieldWidth, double playfieldHeight, double viewPortWidth, double viewPortHeight) {
        super(graphicsContext, playfieldWidth, playfieldHeight, viewPortWidth, viewPortHeight);
    }

    
    
    @Override
    public void initGame() {
        try {
            // read the Map
            tileMap = TileMapReader.readMap(fileURL);
            backgroundImage = tileMap.getTileSet("background").getTileImage();

            // create the level
//            canvas = new Canvas(tileMap.getTilewidth() * tileMap.getWidth(), tileMap.getHeight() * tileMap.getTileheight());
//            level = new Level(new GraphicsContext(new JavaFXGraphicsEnvironment(canvas)), tileMap.getTilewidth() * tileMap.getWidth(), tileMap.getHeight() * tileMap.getTileheight(), tileMap.getTilewidth() * tileMap.getWidth(), tileMap.getHeight() * tileMap.getTileheight());
            level = this;
            // add all the layers
            level.addLayer(new ImageLayer(backgroundImage));
            ArrayList<TileMapLayer> layers = tileMap.getLayers();
            for (TileMapLayer tileMapLayer : layers) {
                level.addLayer(tileMapLayer);
            }
            level.addLayer(new HUD());

           

            final Sprite button = new Sprite(level, "button", tileMap.getWidthInPixels() - 30, 20, 20, 20);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                    startWave();
                    button.die();
                }
            });
            level.addSprite(button);

            turretBaseLayer = (TileMapLayer) level.getLayer("turret-bases");
            platformLayer = (TileMapLayer) level.getLayer("terrain");

            // initialize the Palette
            TileSet bullets = tileMap.getTileSet("pellet");
            final TileSetAnimation shoot = new TileSetAnimation(bullets, new int[]{0}, .00000001f);
            final TileSet cannons = tileMap.getTileSet("turret-cannons");
            TileSet bases = tileMap.getTileSet("turret-bases");
            palette = new Palette(bases, cannons);

            // add Handler for placing Turrets
            level.addEventHandler(MouseEvent.MOUSE_PRESSED, new TurretHandler(cannons, shoot));


            // read spawnpoints, target, turret-properties,...
            readObjectProperties();
            calculateAttackPath();


            level.start();

        } catch (Exception ex) {
            Logger.getLogger(TowerDefenseGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                        System.out.println("Spawn monsters every " + evaluationInterval + " nanos");

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
        try {
            TileMapReader.writeMap(tileMap, "LevelState.tmx");
        } catch (JAXBException ex) {
            Logger.getLogger(TowerDefenseGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TowerDefenseGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // setup Enemy Spawning
        Wave current = waves.get(currentWave++);
        levelLost = false;
        level.addBehaviour(current);
        level.addBehaviour(new CheckHitsBehavior(hits));
    }

    private void stopWave() {
        if (hits.integerValue() >= maxHits) {
            message = "Section destroyed!";
        } else {
            message = "Wave finished, hits: " + hits;
        }
        if (!levelLost) {
            level.addBehaviour(new FadeBehaviour());
            levelLost = true;
        }

    }

    private void calculateAttackPath() {
        // calculate the attack path
        AStar.AStarTile start = new AStar.AStarTile((int) spawnpointTileX, (int) spawnpointTileY);
        AStar.AStarTile end = new AStar.AStarTile((int) targetX, (int) targetY);
        attackPath = AStar.getPath(tileMap, platformLayer, end, start);
    }

    private class CheckHitsBehavior extends Behavior {

        private final IntegerProperty hits;

        public CheckHitsBehavior(IntegerProperty hits) {
            this.hits = hits;
            setEvaluationInterval(1000000l);
        }

        @Override
        public boolean perform(Level canvas, long nanos) {
            if (hits.integerValue() < maxHits) {
                return true;
            } else {
                stopWave();
                return false;
            }
        }
    }

    private class ScanForLastEnemyBevavior extends Behavior {

        public ScanForLastEnemyBevavior() {
            setEvaluationInterval(10000000l);
        }

        @Override
        public boolean perform(Level canvas, long nanos) {
            Collection<Sprite> sprites = canvas.getSprites();
            for (Sprite sprite : sprites) {
                if (sprite instanceof EnemySprite) {
                    return true;
                }
            }

            stopWave();
            return false;
        }
    }

    private class HUD extends Layer {

        @Override
        public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
            graphicsContext.setFillStyle(new Color("#ff0000"));
            graphicsContext.setFont("Bold 24 OricNeo");
            graphicsContext.fillText("Score: " + score, 10, 28);
            if (message != null) {
                graphicsContext.fillText(message, 150, 200);
            }
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
                new CannonSprite(level, palette.getSelectedProperties(), new TileSetAnimation(cannons, new int[]{palette.getSelectedIndex()}, .1f), shoot, "cannon " + (cannoncount++), tileGridX * tileMap.getTilewidth(), tileGridY * tileMap.getTileheight(), 46, 46);
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
                    enemySprites.add(new EnemySprite(level, score, hits, new Properties(), stacked, "enemy" + (enemyCount++), ((int) spawnpointTileX) * tileMap.getTilewidth(), ((int) spawnpointTileY) * tileMap.getTileheight(), 46, 46));
                }
            }
        }

        @Override
        public boolean perform(Level canvas, long nanos) {
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
