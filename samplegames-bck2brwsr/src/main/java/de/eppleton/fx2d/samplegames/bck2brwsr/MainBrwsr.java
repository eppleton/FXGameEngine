package de.eppleton.fx2d.samplegames.bck2brwsr;

public class MainBrwsr {
    static {
        try {
            Main.onPageLoad();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
