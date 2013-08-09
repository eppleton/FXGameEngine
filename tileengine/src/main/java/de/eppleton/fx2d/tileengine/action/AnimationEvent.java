/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.tileengine.action;

import de.eppleton.fx2d.Sprite;

/**
 *
 * @author antonepple
 */
public class AnimationEvent {

    Sprite target;
    TileSetAnimation source;
    EventType type;

    public AnimationEvent(Sprite target, TileSetAnimation source, EventType type) {
        this.target = target;
        this.source = source;
        this.type = type;
    }

    public Sprite getTarget() {
        return target;
    }

    public void setTarget(Sprite target) {
        this.target = target;
    }

    public TileSetAnimation getSource() {
        return source;
    }

    public void setSource(TileSetAnimation source) {
        this.source = source;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
    
    
    
    public enum EventType{
        FINISHED,
        STARTED,
        CYCLE_FINISHED;      
    }
    
    
       
}
