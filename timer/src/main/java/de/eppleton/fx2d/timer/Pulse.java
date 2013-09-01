/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.timer;

import de.eppleton.fx2d.timer.spi.TimerEnvironment;
import java.util.ServiceLoader;

/**
 *
 * @author antonepple
 */
public abstract class Pulse {

    public static Pulse create(Handler handler) {
        for (TimerEnvironment<?> te : ServiceLoader.load(TimerEnvironment.class)) {
            Impl handle = create(te, handler);
            if (handle != null) {
                return handle;
            }
        }
        return null;
    }

    public abstract void start();

    public abstract void stop();

    private static <Timer> Impl<Timer> create(TimerEnvironment<Timer> env, Handler handler) {
        Timer a = env.create(handler);
        if (a != null) {
            return new Impl<Timer>(env, a);
        } else {
            return null;
        }
    }

    public interface Handler {

        public void pulse(long pulse);
    }

    private static final class Impl<Timer> extends Pulse {

        private final Timer timer;
        private final TimerEnvironment<Timer> env;

        public Impl(TimerEnvironment<Timer> env, Timer timer) {
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
