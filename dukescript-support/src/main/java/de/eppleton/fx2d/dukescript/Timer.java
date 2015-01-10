package de.eppleton.fx2d.dukescript;

import net.java.html.js.JavaScriptBody;



public abstract class Timer implements Runnable {
    
    Object jsTimer = null;
    
    public void cancel() {
        close(jsTimer);
        jsTimer = null;
    }
    
    @Override
    public abstract void run();

    @JavaScriptBody(args = { "r", "millis" }, javacall = true, body = 
        "return window.setTimeout(function() { r.@java.lang.Runnable::run()(); }, millis);"
    )
    private static native Object schedule(Runnable r, int millis); 
    
    public void schedule(int millis) {
        if (jsTimer != null) {
            cancel();
        }
        jsTimer = schedule(this, millis);
    }
    
    @JavaScriptBody(args = { "timer" }, body = 
        "window.clearTimeout(timer);"
    )
    private static native void close(Object self);
}
