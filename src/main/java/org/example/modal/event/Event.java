package org.example.modal.event;

import java.util.Objects;

public class Event {

    private String customerId;
    private String workloadId;
    private long timestamp;

    private String eventType;

    Event(){}
    public Event(String customerId, String workloadId, long timestamp, String eventType) {
        this.customerId = customerId;
        this.workloadId = workloadId;
        this.timestamp = timestamp;
        this.eventType = eventType;
    }

    // Additional methods for calculating time spent







    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getWorkloadId() {
        return workloadId;
    }

    public void setWorkloadId(String workloadId) {
        this.workloadId = workloadId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return timestamp == event.timestamp && Objects.equals(customerId, event.customerId) && Objects.equals(workloadId, event.workloadId) && Objects.equals(eventType, event.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, workloadId, timestamp, eventType);
    }
}
