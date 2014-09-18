package de.eppleton.fx2d.samplegames.bck2brwsr;

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


import de.eppleton.fx2d.samplegames.bck2brwsr.Bck2BrwsrTimerEnvironment.BBTimer;
import de.eppleton.fx2d.timer.Handler;
import org.openide.util.lookup.ServiceProvider;
import de.eppleton.fx2d.timer.spi.GameTimerEnvironment;

@ServiceProvider(service = GameTimerEnvironment.class)
public class Bck2BrwsrTimerEnvironment implements GameTimerEnvironment<BBTimer> {

    @Override
    public BBTimer create(final Handler handler) {

        return new BBTimer(new Runnable() {
            @Override
            public void run() {
                handler.pulse(System.nanoTime());
            }
        });

    }

    @Override
    public void start(BBTimer timer) {
        timer.start();
    }

    @Override
    public void stop(BBTimer timer) {
        timer.stop();
    }



    public static class BBTimer {

        Timer timer;
        Runnable runnable;

        public BBTimer(Runnable runnable) {
            this.runnable = runnable;
        }

        public void start() {
            if (timer !=null)timer.close();
            timer = Timer.create(runnable, 30);
        }

        public void stop() {
            timer.close();
        }
    }
}