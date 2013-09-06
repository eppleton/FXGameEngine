/**
 * This file is part of FXGameEngine 
 * A Game Engine written in JavaFX
 * Copyright (C) 2012 Anton Epple <info@eppleton.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://opensource.org/licenses/GPL-2.0.
 * 
 * For alternative licensing or use in closed source projects contact Anton Epple 
 * <info@eppleton.de>
 */
package de.eppleton.fx2d.action;

import de.eppleton.fx2d.Level;
import de.eppleton.fx2d.Level.MoveValidator;
import de.eppleton.fx2d.Sprite;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author antonepple
 */
public class DefaultMoveBehavior extends Behavior {
    private long lastUpdate;
    private long delay = -1;
    private double factor;

    public DefaultMoveBehavior() {
        this.moveValidators = new ArrayList<>();
    }

    @Override
    public boolean perform(Level canvas, long l) {
        boolean dirty = false;
        if (delay == -1) {
            delay = 16666667;
        } else {
            delay = l - lastUpdate;
        }
        Collection<Sprite> sprites1 = canvas.getSprites();
        for (Sprite sprite : sprites1) {
            if (updateSpritePosition(sprite)) {
                dirty = true;
            }
        }
        lastUpdate = l;
        return dirty;
    }

    private boolean updateSpritePosition(Sprite sprite) {
        boolean dirty = true;
        factor = (double) delay / 16666667;
        double velocityX = sprite.getVelocityX() * factor;
        double velocityY = sprite.getVelocityY() * factor;
        if (velocityX == 0 && velocityY == 0) {
            return false;
        }
        de.eppleton.fx2d.Rectangle2D moveBox = sprite.getMoveBox();
        double x = sprite.getX();
        double y = sprite.getY();
        double newX = velocityX + x;
        double newY = velocityY + y;
        if (isValidMove(newX + moveBox.getMinX(), newY + moveBox.getMinY(), moveBox.getWidth(), moveBox.getHeight())) {
            sprite.setX(newX);
            sprite.setY(newY);
        } else {
            sprite.invalidMove();
            dirty = false;
        }
        return dirty;
    }

    /**
     * Check if this is a valid move by calling all registered
     * {@link MoveValidator MoveValidators}
     *
     * @param xProperty
     * @param yProperty
     * @param width
     * @param height
     * @return true, if this is a valid move, false otherwise
     */
    private boolean isValidMove(double x, double y, double width, double height) {
        for (Level.MoveValidator moveValidator : moveValidators) {
            if (!moveValidator.isValidMove(x, y, width, height)) {
                return false;
            }
        }
        return true;
    }
    private ArrayList<Level.MoveValidator> moveValidators;

    public void addMoveValidator(Level.MoveValidator moveValidator) {
        moveValidators.add(moveValidator);
    }

    public void removeMoveValidator(Level.MoveValidator moveValidator) {
        moveValidators.remove(moveValidator);
    }
    
}
