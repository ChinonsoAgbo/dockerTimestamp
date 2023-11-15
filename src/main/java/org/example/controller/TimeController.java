package org.example.controller;
import org.example.modal.EventData;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/v1")
public class TimeController {

    private  TimeService timeService;


    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }
        @PostMapping("/result")
        public ResponseEntity<String> postResults (@RequestBody Map < String, Long > results){
            // Process and store the results as needed

            // Process and store the results using the TimeService
            timeService.calculateTimeSpent((EventData) results);
            System.out.println("Received results: " + results);
            return ResponseEntity.ok("Results received successfully");
        }

    private String formatResults(Map<String, Long> customerConsumptionMap) {
        // Format the results in the specified JSON format
        // For simplicity, we are constructing the JSON string manually
        // You may want to use a library like Jackson or Gson for JSON formatting
        StringBuilder resultJson = new StringBuilder("{\"result\": [");
        for (Map.Entry<String, Long> entry : customerConsumptionMap.entrySet()) {
            resultJson.append("{ \"customerId\": \"").append(entry.getKey())
                    .append("\", \"consumption\": ").append(entry.getValue())
                    .append(" },");
        }
        if (!customerConsumptionMap.isEmpty()) {
            resultJson.deleteCharAt(resultJson.length() - 1); // Remove the last comma
        }
        resultJson.append("]}");
        return resultJson.toString();
    }


    private void sendResults(Map<String, Long> customerConsumptionMap) {
        String url = "http://localhost:8080/v1/result";

        // Format the results in JSON format
        String resultJson = formatResults(customerConsumptionMap);

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set up the request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(resultJson, headers);

        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Make the HTTP POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Check the response status
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Results sent successfully.");
        } else {
            System.err.println("Failed to send results. HTTP Status: " + responseEntity.getStatusCode());
        }
    }
    }
