/**
 * This file is part of FXGameEngine 
 * A Game Engine written in JavaFX
 * Copyright (C) 2012 Anton Epple <info@eppleton.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://opensource.org/licenses/GPL-2.0.
 * 
 * For alternative licensing or use in closed source projects contact Anton Epple 
 * <info@eppleton.de>
 */
package de.eppleton.fx2d;

import net.java.html.canvas.GraphicsContext;
import net.java.html.canvas.Style.Color;

/**
 *
 * @author antonepple
 */
public class DebugLayer extends Layer{

    private final Color fill;
    private final Level canvas;
//    private final PerformanceTracker perfTracker;

    public DebugLayer(Color textFill, Level canvas) {
        super("debug");
        this.fill = textFill;
        this.canvas = canvas;
//        Scene scene = canvas.getScene();
//        perfTracker =  PerformanceTracker.getSceneTracker(scene);
    }

    @Override
    public void draw(GraphicsContext graphicsContext, double x, double y, double width, double height) {
        
        
//        graphicsContext.setFont(Font.font("Monospaced", FontWeight.BOLD, 10));
        graphicsContext.setFillStyle(fill);
        graphicsContext.fillText("#pulses "+canvas.getPulses() , x, y+12);
        graphicsContext.fillText("skipped "+canvas.getCleanCounter() , x, y+24);
//        graphicsContext.fillText("AveragePulses "+perfTracker.getAveragePulses() , x, y+36);
//        graphicsContext.fillText("AverageFPS "+perfTracker.getAverageFPS() , x, y+48);
//        
//        graphicsContext.fillText("InstantFPS "+perfTracker.getInstantFPS() , x, y+60);
//        graphicsContext.fillText("InstantPulses "+perfTracker.getInstantPulses(), x, y+72);
        graphicsContext.fillText("StutterPulses "+canvas.getStutter() , x, y+84);
        graphicsContext.fillText("LastSlowness "+canvas.getLastSlowness(), x, y+96);
        graphicsContext.fillText("Maxdelta "+canvas.getMaxTimePassed(), x, y+108);
        
    }
    
}
