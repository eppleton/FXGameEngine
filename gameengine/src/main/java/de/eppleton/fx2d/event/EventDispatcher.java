/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.event;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 *
 * @author antonepple
 */
public  class EventDispatcher {

    private static EventDispatcher INSTANCE;
    private final Map<Event.Type<? extends Event>,ProxyEventHandler<? extends Event>> eventHandlerMap;

    public EventDispatcher() {
        eventHandlerMap =
                new HashMap<>();
    }

    public final <T extends Event> void addEventHandler(
            final Event.Type<T> eventType,
            final EventHandler<? super T> eventHandler) {
        validateEventType(eventType);
        validateEventHandler(eventHandler);

        final ProxyEventHandler<T> proxy = createGetEventHandlerProxy(eventType);

        proxy.add((EventHandler<T>) eventHandler);
    }

    /**
     * Unregisters a previously registered event handler.
     *
     * @param <T> the specific event class of the handler
     * @param eventType the event type from which to unregister
     * @param eventHandler the handler to unregister
     * @throws NullPointerException if the event type or handler is null
     */
    public final <T extends Event> void removeEventHandler(
            final Event.Type<T> eventType,
            final EventHandler<? super T> eventHandler) {
        validateEventType(eventType);
        validateEventHandler(eventHandler);

        final ProxyEventHandler<T> proxy = (ProxyEventHandler<T>) eventHandlerMap.get(eventType);

        if (proxy != null) {
            proxy.remove(eventHandler);
        }
    }

    private static void validateEventType(final Event.Type<?> eventType) {
        if (eventType == null) {
            throw new NullPointerException("Event type must not be null");
        }
    }

    private static void validateEventHandler(
            final EventHandler<?> eventHandler) {
        if (eventHandler == null) {
            throw new NullPointerException("Event handler must not be null");
        }
    }

    public static EventDispatcher create() {
        if (INSTANCE == null) {
            ServiceLoader<EventDispatcher> dispatchers = ServiceLoader.load(EventDispatcher.class);
            for (EventDispatcher eventDispatcher : dispatchers) {
                INSTANCE = eventDispatcher;
                return eventDispatcher;
            }
        }
        return INSTANCE;
    }

    public void dispatchEvent(Event event){
        Event.Type type = event.getType();
        do {
            dispatchEvent(type, event);
            type = type.getSuperType();
        } while (type != null);
        
    }
    
    private void dispatchEvent(
            final Event.Type<? extends Event> handlerType, Event event) {
        final ProxyEventHandler<? extends Event> proxy =
                eventHandlerMap.get(handlerType);

        if (proxy != null) {
            proxy.dispatchEvent(event);
        }
    }

    
    private <T extends Event> ProxyEventHandler<T>
            createGetEventHandlerProxy(final Event.Type<T> eventType) {

        ProxyEventHandler<? extends Event> proxy = eventHandlerMap.get(eventType);
        if (proxy == null) {
            proxy = new ProxyEventHandler<>();
            eventHandlerMap.put(eventType, proxy);
        }
        return (ProxyEventHandler<T>) proxy;
    }

    
}
