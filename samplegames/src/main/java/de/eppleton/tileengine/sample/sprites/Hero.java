/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.tileengine.sample.sprites;

import de.eppleton.fx2d.GameCanvas;
import de.eppleton.fx2d.Sprite;
import de.eppleton.fx2d.action.Renderer;
import de.eppleton.fx2d.action.SpriteAction;
import de.eppleton.fx2d.action.State;
import de.eppleton.fx2d.tileengine.TileMapReader;
import de.eppleton.fx2d.tileengine.TileSet;
import de.eppleton.fx2d.tileengine.TileSetAnimation;
import de.eppleton.tileengine.sample.SampleGame;
import de.eppleton.tileengine.sample.sprites.actions.MoveAction;
import de.eppleton.tileengine.sample.sprites.actions.StateAnimation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import org.openide.util.Lookup;

/**
 *
 * @author antonepple
 */
public class Hero extends Sprite {

    private static State UP;
    private static State LEFT;
    private static State DOWN;
    private static State RIGHT;
    private SpriteAction moveUp;
    private SpriteAction moveLeft;
    private SpriteAction moveDown;
    private SpriteAction moveRight;
    private SpriteAction attack;
    TileSet heroSet;
    // character 
    private int level = 0;
    private int power = 0;
    private int accuracy = 0;
    private int toughness = 0;
    // defence
    private int shield = 0;
    private int armor = 0;
    private int helmet = 0;
    // weapons
    private int aura = 0;
    private int range = 0;
    private int range_damage = 0;
    private int weapon = 0;
    ArrayList<Gear> gearList;
    private static Comparator<Gear> LAYER_COMPARATOR = new Comparator<Gear>() {
        @Override
        public int compare(Gear o1, Gear o2) {
            if (o1.getLayer() > o2.getLayer()) {
                return 1;
            } else if (o1.getLayer() == o2.getLayer()) {
                return 0;
            } else {
                return - 1;
            }
        }
    };

    /**
     *
     * @param field
     * @param base
     * @param name
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Hero(GameCanvas field, TileSet base, String name, double x, double y, int width, int height) {
        super(field, name, x, y, width, height, Lookup.EMPTY);

        heroSet = base;
        setMoveBox(new Rectangle2D(18, 42, 28, 20));
        UP = new State(new TileSetAnimation(heroSet, new int[]{0}, 50f), "up");
        LEFT = new State(new TileSetAnimation(heroSet, new int[]{16}, 50f), "left");
        DOWN = new State(new TileSetAnimation(heroSet, new int[]{32}, 50f), "down");
        RIGHT = new State(new TileSetAnimation(heroSet, new int[]{48}, 50f), "right");

        moveUp = new MoveAction(new TileSetAnimation(heroSet, new int[]{1, 2, 3, 4, 5, 6, 7, 8}, .1f), "move up", 0, -2, UP);
        moveLeft = new MoveAction(new TileSetAnimation(heroSet, new int[]{17, 18, 19, 20, 21, 22, 23, 24}, .1f), "move left", -2, 0, LEFT);
        moveDown = new MoveAction(new TileSetAnimation(heroSet, new int[]{33, 34, 35, 36, 37, 38, 39, 40}, .1f), "move down", 0, 2, DOWN);
        moveRight = new MoveAction(new TileSetAnimation(heroSet, new int[]{49, 50, 51, 52, 53, 54, 55, 56}, .1f), "move right", 2, 0, RIGHT);

        addAction(KeyCode.A, moveLeft);
        addAction(KeyCode.S, moveDown);
        addAction(KeyCode.W, moveUp);
        addAction(KeyCode.D, moveRight);


        HashMap<State, int[]> state2Index = new HashMap<>();
        state2Index.put(UP, new int[]{65, 66, 67, 68, 69});
        state2Index.put(LEFT, new int[]{81, 82, 83, 84, 85});
        state2Index.put(DOWN, new int[]{97, 98, 99, 100, 101});
        state2Index.put(RIGHT, new int[]{113, 114, 115, 116, 117});


        attack = new SpriteActionImpl(
                new StateAnimation(heroSet, new int[]{65, 66, 67, 68, 69}, state2Index, 50f), "attack");
        addAction(KeyCode.SPACE, attack);
        gearList = new ArrayList<>();


    }

    private void mergeTileSets() {
        String source = heroSet.getImage().getSource();
        String resourcePath = TileMapReader.resourcePath(source, heroSet.getBaseUrl());
        Image image = new Image(SampleGame.class.getResourceAsStream(resourcePath));
        heroSet.init(image);


        Collections.sort(gearList, LAYER_COMPARATOR);
        for (Gear gear : gearList) {
            heroSet.merge(gear.getTileset());
        }
    }

    public boolean offer(Gear gear) {
        gearList.add(gear);
        mergeTileSets();
        return true;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getToughness() {
        return toughness;
    }

    public void setToughness(int toughness) {
        this.toughness = toughness;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getHelmet() {
        return helmet;
    }

    public void setHelmet(int helmet) {
        this.helmet = helmet;
    }

    public int getAura() {
        return aura;
    }

    public void setAura(int aura) {
        this.aura = aura;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getRange_damage() {
        return range_damage;
    }

    public void setRange_damage(int range_damage) {
        this.range_damage = range_damage;
    }

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }
    private static final Logger LOG = Logger.getLogger(Hero.class.getName());

    private static class SpriteActionImpl extends SpriteAction {

        public SpriteActionImpl(Renderer animation, String name) {
            super(animation, name);
        }

        @Override
        public void started(Sprite sprite) {
            sprite.setVelocityX(0);
            sprite.setVelocityY(0);
        }
    }
}
