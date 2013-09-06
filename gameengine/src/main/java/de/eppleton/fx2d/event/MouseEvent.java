/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
