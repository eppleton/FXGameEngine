package de.eppleton.fx2d.timer;

import de.eppleton.fx2d.timer.spi.TimerEnvironment;
import javafx.animation.AnimationTimer;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service=TimerEnvironment.class)
public class JavaFXTimerEnvironment implements TimerEnvironment<AnimationTimer> {

    public AnimationTimer create(final Pulse.Handler handler) {
        return new AnimationTimer() {
            @Override
            public void handle(long l) {
                handler.pulse(l);
            }
        };
    }

    public void start(AnimationTimer timer) {
        timer.start();
    }

    public void stop(AnimationTimer timer) {
        timer.stop();
    }
}
