/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.event;

/**
 *
 * @author antonepple
 */
public class KeyEvent extends Event{

    public static final Event.Type<KeyEvent> ANY =
            new Event.Type<KeyEvent>("KEY");

    public static final Event.Type<KeyEvent> KEY_PRESSED =
            new Event.Type<KeyEvent>("KEY_PRESSED");

    public static final Event.Type<KeyEvent> KEY_RELEASED =
            new Event.Type<KeyEvent>( "KEY_RELEASED");

    public static final Event.Type<KeyEvent> KEY_TYPED =
            new Event.Type<KeyEvent>("KEY_TYPED");

    
    private final KeyCode keyCode;
    public KeyEvent(Object source, Type type, KeyCode keyCode) {
        super(source, type);
        this.keyCode = keyCode;
    }

    public KeyCode getKeyCode() {
        return keyCode;
    }

    
     
   
    
}
