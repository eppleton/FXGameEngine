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
package de.eppleton.fx2d.event;


/**
 *
 * @author antonepple
 */
public class MouseEvent extends Event{
      /**
     * Common supertype for all mouse event types.
     */
    public static final Event.Type<MouseEvent> ANY =
            new Event.Type<MouseEvent>("MOUSE", Event.ANY);

    public static final Event.Type<MouseEvent> MOUSE_PRESSED =
            new Event.Type<MouseEvent>("MOUSE_PRESSED",ANY);

    public static final Event.Type<MouseEvent> MOUSE_RELEASED =
            new Event.Type<MouseEvent>( "MOUSE_RELEASED",ANY);

    public static final Event.Type<MouseEvent> MOUSE_CLICKED =
            new Event.Type<MouseEvent>("MOUSE_CLICKED",ANY);

    public static final Event.Type<MouseEvent> MOUSE_ENTERED_TARGET =
            new Event.Type<MouseEvent>("MOUSE_ENTERED_TARGET",ANY);

    public static final Event.Type<MouseEvent> MOUSE_ENTERED =
            new Event.Type<MouseEvent>( "MOUSE_ENTERED",ANY);

    public static final Event.Type<MouseEvent> MOUSE_EXITED_TARGET =
            new Event.Type<MouseEvent>( "MOUSE_EXITED_TARGET",ANY);

    public static final Event.Type<MouseEvent> MOUSE_EXITED =
            new Event.Type<MouseEvent>( "MOUSE_EXITED",ANY);

    public static final Event.Type<MouseEvent> MOUSE_MOVED =
            new Event.Type<MouseEvent>("MOUSE_MOVED",ANY);

    public static final Event.Type<MouseEvent> MOUSE_DRAGGED =
            new Event.Type<MouseEvent>("MOUSE_DRAGGED",ANY);

    private double x;
    private double y;
    
    public MouseEvent(Object source, Event.Type<? extends MouseEvent> type , double x, double y) {
        super(source, type );
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    
}
