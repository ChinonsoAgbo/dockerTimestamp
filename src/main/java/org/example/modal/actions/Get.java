package org.example.modal.actions;

import org.example.modal.event.Event;
import org.example.modal.event.EventData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * The Get class retrieves a list of events from an external service.
 */
//@RestController
public class Get {

    private List<Event> eventSetList;

    /**
     * Default constructor initializes the list of events.
     */
    public Get() {
        eventSetList = new ArrayList<>();
    }

    /**
     * Retrieves a list of events from an external service.
     *
     * @return The list of events.
     * @throws Exception If an error occurs during the retrieval process.
     */
    // @GetMapping("/v1/dataset")
    public List<Event> getServiceResponse() throws IllegalAccessException {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Make a GET request to the external service
            ResponseEntity<EventData> response = restTemplate.getForEntity(
                    "http://localhost:8080/v1/dataset",
                    EventData.class);

            EventData eventData = response.getBody();

            // Accessing the events from eventData
            List<Event> eventList = eventData.getEvents();

            // Copy the events to the internal list
            eventSetList = new ArrayList<>(eventList);


            return eventSetList;
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            throw new IllegalAccessException("The container mrksdh/assessment might be turned off: Check connections  ");
        }
    }
}
