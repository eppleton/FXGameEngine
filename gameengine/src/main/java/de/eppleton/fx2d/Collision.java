/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d;

/**
 *
 * @author antonepple
 */
public class Collision {

    Sprite spriteOne;
    Sprite spriteTwo;

    public Collision(Sprite spriteOne, Sprite spriteTwo) {
        this.spriteOne = spriteOne;
        this.spriteTwo = spriteTwo;
    }

    public Sprite getSpriteOne() {
        return spriteOne;
    }

    public Sprite getSpriteTwo() {
        return spriteTwo;
    }
}
