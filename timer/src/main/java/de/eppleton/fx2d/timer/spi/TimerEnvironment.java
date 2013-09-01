/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.timer.spi;

import de.eppleton.fx2d.timer.Pulse.Handler;

/**
 *
 * @author antonepple
 */
public interface TimerEnvironment<Timer> {
    
    public Timer create(Handler handler);
    
    public void start(Timer timer);
    
    public void stop(Timer timer);
    
}
