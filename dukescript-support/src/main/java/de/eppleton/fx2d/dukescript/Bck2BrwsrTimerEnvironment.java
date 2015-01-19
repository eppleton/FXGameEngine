package de.eppleton.fx2d.dukescript;

import de.eppleton.fx2d.timer.Handler;
import de.eppleton.fx2d.timer.spi.GameTimerEnvironment;
import java.util.logging.Logger;
import net.java.html.js.JavaScriptBody;
import net.java.html.js.JavaScriptResource;

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

    @JavaScriptResource(value = "GameLoop.js")
    public static class BBTimer {

        private static Logger LOGGER = Logger.getLogger(BBTimer.class.getName());
        private final Handler handler;
        private Timer timer;
        private boolean running;

        BBTimer(final Handler handler) {
            this.handler = handler;
        }

        private void start() {
            startAnimation(handler);
        }

        private void stop() {

        }

        
        
        
        
        @JavaScriptBody(args = {"handl"}, javacall = true,
                body = "Game_Singleton.prototype.mainLoop = function () {\n"
                + "    var date = new Date();\n"
                        + "var time = date.getTime();"
                + "    handl.@de.eppleton.fx2d.timer.Handler::pulse(J)(time);\n"
                + "    requestAnimationFrame(Game.mainLoop);\n"
                + "};"
                        + "Game.start();")
        public static native void startAnimation(Handler handl);

    }

}
