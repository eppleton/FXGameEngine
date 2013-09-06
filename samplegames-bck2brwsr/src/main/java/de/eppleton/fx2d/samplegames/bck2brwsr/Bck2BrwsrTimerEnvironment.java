/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.samplegames.bck2brwsr;

import de.eppleton.fx2d.samplegames.bck2brwsr.Bck2BrwsrTimerEnvironment.BBTimer;
import de.eppleton.fx2d.timer.Pulse;
import de.eppleton.fx2d.timer.spi.TimerEnvironment;
import org.apidesign.bck2brwsr.htmlpage.api.Timer;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = TimerEnvironment.class)
public class Bck2BrwsrTimerEnvironment implements TimerEnvironment<BBTimer> {

    public BBTimer create(final Pulse.Handler handler) {

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