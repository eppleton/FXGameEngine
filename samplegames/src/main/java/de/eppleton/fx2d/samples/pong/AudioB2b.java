package de.eppleton.fx2d.samples.pong;

import org.apidesign.bck2brwsr.core.JavaScriptBody;
import org.apidesign.html.sound.spi.AudioEnvironment;
import org.openide.util.lookup.ServiceProvider;


@ServiceProvider(service = AudioEnvironment.class, position = 1)
public final class AudioB2b implements AudioEnvironment<Object> {
    @Override
    @JavaScriptBody(args = { "src" }, body = ""
        + "if (!Audio) return null;"
        + "return new Audio(src);")
    public Object create(String src) {
        // null if not running in browser
        return null;
    }

    @Override @JavaScriptBody(args = { "a" }, body = "a.play();")
    public void play(Object a) {
    }

    @Override @JavaScriptBody(args = { "a" }, body = "a.pause();")
    public void pause(Object a) {
    }

    @Override @JavaScriptBody(args = { "a", "volume" }, body = "a.setVolume(volume);")
    public void setVolume(Object a, double volume) {
    }

    @Override
    @JavaScriptBody(args = "a", body = "return true;")
    public boolean isSupported(Object a) {
        return false;
    }
}