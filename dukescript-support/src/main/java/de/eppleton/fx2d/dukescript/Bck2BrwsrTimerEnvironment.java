package de.eppleton.fx2d.dukescript;

import de.eppleton.fx2d.timer.Handler;
import de.eppleton.fx2d.timer.spi.GameTimerEnvironment;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

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
public class Bck2BrwsrTimerEnvironment implements GameTimerEnvironment<Bck2BrwsrTimerEnvironment.BBTimer> {

    @Override
    public BBTimer create(final Handler handler) {
        BBTimer timer = new BBTimer(handler);
        return timer;
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

        private static Logger LOGGER = Logger.getLogger(BBTimer.class.getName());
        private final Handler handler;
        private Timer timer;
        private boolean running;

        BBTimer(final Handler handler) {
            this.handler = handler;
        }

        private void start() {
//            running = true;
//            while (running) {
//                
//
//            }
            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    Main.brwsrctx.execute(new Runnable() {

                        @Override
                        public void run() {
                            handler.pulse(System.nanoTime());
                            start();
                        }
                    });
                }
            },16);
        }

        private void stop() {
//            running = false;
            timer.cancel();
            timer = null;
        }
    }

}
