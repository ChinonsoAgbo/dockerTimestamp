package org.example.modal.event;

import java.util.Objects;

/**
 * The Event class represents an event with details such as customer ID, workload ID, timestamp, and event type.
 */
public class Event {

    private String customerId;
    private String workloadId;
    private long timestamp;
    private String eventType;

    // Default constructor for Jackson deserialization
    Event() {}

    /**
     * Constructs an Event object with the specified details.
     *
     * @param customerId The customer ID associated with the event.
     * @param workloadId The workload ID associated with the event.
     * @param timestamp  The timestamp when the event occurred.
     * @param eventType  The type of the event.
     */
    public Event(String customerId, String workloadId, long timestamp, String eventType) {
        this.customerId = customerId;
        this.workloadId = workloadId;
        this.timestamp = timestamp;
        this.eventType = eventType;
    }

    /**
     * Gets the customer ID associated with the event.
     *
     * @return The customer ID.
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer ID associated with the event.
     *
     * @param customerId The customer ID to set.
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the workload ID associated with the event.
     *
     * @return The workload ID.
     */
    public String getWorkloadId() {
        return workloadId;
    }

    /**
     * Sets the workload ID associated with the event.
     *
     * @param workloadId The workload ID to set.
     */
    public void setWorkloadId(String workloadId) {
        this.workloadId = workloadId;
    }

    /**
     * Gets the timestamp when the event occurred.
     *
     * @return The timestamp.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the event occurred.
     *
     * @param timestamp The timestamp to set.
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets the type of the event.
     *
     * @return The event type.
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Sets the type of the event.
     *
     * @param eventType The event type to set.
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * Checks if this Event object is equal to another object.
     *
     * @param o The object to compare to.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return timestamp == event.timestamp && Objects.equals(customerId, event.customerId) && Objects.equals(workloadId, event.workloadId) && Objects.equals(eventType, event.eventType);
    }

    /**
     * Generates a hash code for this Event object.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(customerId, workloadId, timestamp, eventType);
    }
}
