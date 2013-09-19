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
package de.eppleton.fx2d.timer;

import de.eppleton.fx2d.timer.spi.GameTimerEnvironment;
import javafx.animation.AnimationTimer;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service=GameTimerEnvironment.class)
public class JavaFXTimerEnvironment implements GameTimerEnvironment<AnimationTimer> {

    @Override
    public AnimationTimer create(final Handler handler) {
        return new AnimationTimer() {
            @Override
            public void handle(long l) {
                handler.pulse(l);
            }
        };
    }

    @Override
    public void start(AnimationTimer timer) {
        timer.start();
    }

    @Override
    public void stop(AnimationTimer timer) {
        timer.stop();
    }
}
