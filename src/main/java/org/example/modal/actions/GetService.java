package org.example.modal.actions;

import org.example.modal.event.Event;
import org.example.modal.event.EventData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class GetService {
    private Set<Event> eventSetList;
    public GetService(){
        eventSetList = new HashSet<>();
    }

    @GetMapping("/customerevents")
    public Set<Event> getServiceResponse(){
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<EventData> response = restTemplate.getForEntity(
                "http://localhost:9090/v1/dataset",
                EventData.class);

        EventData eventData = response.getBody();

        // accessing the events from eventData
        List<Event> eventList = eventData.getEvents();
       eventSetList = new HashSet<>(eventList);

        return eventSetList;
    }

}
