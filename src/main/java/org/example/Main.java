package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.modal.result.CalculateUserTime;
import org.example.modal.result.Result;
import org.example.modal.result.ResultData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * The Main class serves as the entry point for the application.
 */
@SpringBootApplication
public class Main {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "7070"));
        app.run(args);

        try {
            // Fetch data and send it to another service
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Calculate user time and retrieve the result
            List<Result> resultList = new CalculateUserTime().calculateUserUsedTime().getResult();
            ResultData resultData = new ResultData(resultList);

            // Convert ResultData to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResult = objectMapper.writeValueAsString(resultData);

            // Print the JSON result
            System.out.println("JSON Result: " + jsonResult);

            // Prepare HTTP request entity
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonResult, headers);

            // Define the URL for the external service
            String url = "http://localhost:8080/v1/result";

            // Create a RestTemplate for making HTTP requests
            RestTemplate restTemplate = new RestTemplate();
            System.out.println(jsonResult);

            // Make a POST request to the external service
            ResponseEntity<ResultData> responseEntity = restTemplate.postForEntity(url, requestEntity, ResultData.class);
            System.out.println(responseEntity);
            ResultData responseBody = responseEntity.getBody();

            System.out.println("Request successful");
            System.out.println("Response: " + responseBody.toString());

        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors
            System.out.println("Request failed with status code: " + e.getRawStatusCode());
            System.out.println("Response body: " + e.getResponseBodyAsString());
            throw e; // rethrow the exception if needed
        } catch (JsonProcessingException e) {
            // Handle JSON processing errors
            throw new RuntimeException(e);
        }
    }
}