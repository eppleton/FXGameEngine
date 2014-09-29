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
import java.util.ServiceLoader;

/**
 *
 * @author antonepple
 */
public abstract class GamePulse {

    public static GamePulse create(Handler handler) {
        for (GameTimerEnvironment<?> te : ServiceLoader.load(GameTimerEnvironment.class)) {
            Impl handle = create(te, handler);
            if (handle != null) {
                return handle;
            }
        }
        return null;
    }

    public abstract void start();

    public abstract void stop();

    private static <Timer> Impl<Timer> create(GameTimerEnvironment<Timer> env, Handler handler) {
        Timer a = env.create(handler);
        if (a != null) {
            return new Impl<Timer>(env, a);
        } else {
            return null;
        }
    }

    private static final class Impl<Timer> extends GamePulse {

        private final Timer timer;
        private final GameTimerEnvironment<Timer> env;

        public Impl(GameTimerEnvironment<Timer> env, Timer timer) {
            this.timer = timer;
            this.env = env;
        }

        @Override
        public void start() {
            env.start(timer);
        }

        @Override
        public void stop() {
            env.stop(timer);
        }
    }
}
