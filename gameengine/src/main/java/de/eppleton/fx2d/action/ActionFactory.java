/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.action;

/**
 *
 * @author antonepple
 */
public class ActionFactory {

    public static SpriteAction createMoveAction(Renderer anim, String name, double x1, double y1, double x2, double y2) {
        return new MoveAction(x1, y1, x2, y2, anim, name);
    }
}
