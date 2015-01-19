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
package de.eppleton.fx2d;

import java.util.ArrayList;
import java.util.List;

/**
 * A base implementation of Game. Subclass this and add
 *
 * @ServiceProvider(service = Game.class) Annotation to register as game
 * @author antonepple
 */
public class AbstractGame implements Game {

    private List<Level> levels = new ArrayList<Level>();
    private int index = 0;

    @Override
    public Level getCurrentLevel() {
        return getLevel(index);
    }

    @Override
    public void addLevel(Level level) {
        levels.add(level);
    }

    @Override
    public void nextLevel() {
        if (index <= levels.size()-1) {
            index++;
        }
    }

    @Override
    public Level getLevel(int index) {
        return levels.get(index);
    }

}
