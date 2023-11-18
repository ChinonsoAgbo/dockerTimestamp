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

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

       // new PostService().postServiceResponse();

        // new CalculateUserTime().calculateUserUsedTime2();

        try {
            // ... (rest of your existing code)

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            List<Result> resultList = new CalculateUserTime().calculateUserUsedTime22().getResult();

            ResultData resultData = new ResultData(resultList);
            // Convert ResultData to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResult = objectMapper.writeValueAsString(resultData);

            HttpEntity<String> requestEntity = new HttpEntity<>(jsonResult, headers);

            String url = "http://localhost:9090/v1/result";
            RestTemplate restTemplate = new RestTemplate();
            System.out.println(jsonResult);

            ResponseEntity<ResultData> responseEntity = restTemplate.postForEntity(url, requestEntity, ResultData.class);
            System.out.println(responseEntity);
            ResultData responseBody = responseEntity.getBody();

            System.out.println("Request successful");
            System.out.println("Response: " + responseBody.toString());

            //System.out.println(new CalculateUserTime().calculateUserUsedTime22().getResult());
            // Return the ResponseEntity or extract the data as needed
           // return responseEntity;
        } catch (HttpClientErrorException e) {
            System.out.println("Request failed with status code: " + e.getRawStatusCode());
            System.out.println("Response body: " + e.getResponseBodyAsString());
            throw e; // rethrow the exception if needed
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }



}