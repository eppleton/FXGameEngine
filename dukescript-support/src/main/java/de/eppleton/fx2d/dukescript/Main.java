package de.eppleton.fx2d.dukescript;

import de.eppleton.fx2d.Level;
import java.util.ServiceLoader;
import net.java.html.BrwsrCtx;
import net.java.html.boot.BrowserBuilder;
import net.java.html.json.Model;
import net.java.html.json.Models;
@Model(className = "Data", properties={
})
public final class Main {

    public static BrwsrCtx brwsrctx;
    private static Level running;
    
    private Main() {
    }

    public static void main(String... args) throws Exception {
        BrowserBuilder.newBrowser().
                loadPage("pages/index.html").
                loadClass(Main.class).
                invoke("onPageLoad", args).
                showAndWait();
        System.exit(0);
    }

    /**
     * Called when the page is ready.
     */
    public static void onPageLoad() throws Exception {
        brwsrctx = BrwsrCtx.findDefault(Main.class);
        JQuery.init();
        Data data = new Data();
        Models.applyBindings(data);
//        registerKeyEvents();
        ServiceLoader<Level> load = ServiceLoader.load(Level.class);
        for (Level level : load) {
            running = level;
            level.start();
        }
    }
    

    


}
