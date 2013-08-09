/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d;

import com.sun.javafx.perf.PerformanceTracker;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author antonepple
 */
public class DebugLayer extends Layer{

    private final Color fill;
    private final GameCanvas canvas;
    private final PerformanceTracker perfTracker;

    public DebugLayer(Color textFill, GameCanvas canvas) {
        this.fill = textFill;
        this.canvas = canvas;
        Scene scene = canvas.getScene();
        perfTracker =  PerformanceTracker.getSceneTracker(scene);
    }

    @Override
    public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
        
        
        graphicsContext.setFont(Font.font("Monospaced", FontWeight.BOLD, 10));
        graphicsContext.setFill(fill);
        graphicsContext.fillText("#pulses "+canvas.getPulses() , x, y+12);
        graphicsContext.fillText("skipped "+canvas.getCleanCounter() , x, y+24);
        graphicsContext.fillText("AveragePulses "+perfTracker.getAveragePulses() , x, y+36);
        graphicsContext.fillText("AverageFPS "+perfTracker.getAverageFPS() , x, y+48);
        
        graphicsContext.fillText("InstantFPS "+perfTracker.getInstantFPS() , x, y+60);
        graphicsContext.fillText("InstantPulses "+perfTracker.getInstantPulses(), x, y+72);
        graphicsContext.fillText("StutterPulses "+canvas.getStutter() , x, y+84);
        graphicsContext.fillText("LastSlowness "+canvas.getLastSlowness(), x, y+96);
        graphicsContext.fillText("Maxdelta "+canvas.getMaxTimePassed(), x, y+108);
        
    }
    
}
