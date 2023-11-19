package org.example.modal.event;

import java.util.List;

/**
 * The EventData class represents a collection of events.
 */
public class EventData {

    private List<Event> events;

    /**
     * Gets the list of events.
     *
     * @return The list of events.
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Sets the list of events.
     *
     * @param events The list of events to set.
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
