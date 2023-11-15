package org.example.controller;

import org.example.modal.Event;
import org.example.modal.EventData;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TimeService {

    public Map<String, Long> calculateTimeSpent(EventData eventData) {
        Map<String, Long> results = new HashMap<>();

        for (Event event : eventData.getEvents()) {
            String userId = event.getCustomerId();
            long timestamp = event.getTimestamp();

            if ("start".equals(event.getEventType())) {
                // Store the start timestamp
                // You might want to use a more sophisticated data structure for handling multiple start events
                // ...
            } else if ("stop".equals(event.getEventType())) {
                // Calculate time spent and store in results map
                // ...

                // For demonstration, let's assume a simple calculation here
                long startTime = results.getOrDefault(userId, 0L);
                long timeSpent = timestamp - startTime;
                results.put(userId, timeSpent);
            }
        }

        return results;
    }
}
