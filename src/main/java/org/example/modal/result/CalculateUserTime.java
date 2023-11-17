package org.example.modal.result;

import org.example.modal.actions.GetService;
import org.example.modal.event.Event;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;


/**
 * CalculateUserTime is a Spring MVC RestController class responsible for calculating and
 * retrieving the total time spent by each user on various workloads. It uses Event data
 * retrieved through the GetService class to determine the start and stop times of workloads
 * and computes the total time spent by each user.
 * The results are encapsulated in a ResultData object, which is returned as a JSON response
 * to the client when the "/getTimespent" endpoint is accessed via HTTP GET request.
 */

@RestController

public class CalculateUserTime {

   /**
    * Handles HTTP GET requests for the "/getTimespent" endpoint. Retrieves Event data using
    * GetService, calculates the total time spent by each user on different workloads,
    * and returns the results in a ResultData object.
    *
    * @return ResultData object containing a list of Result instances with user IDs and
    * their corresponding total time spent.
    *
    * Delete this code later
    */
   @GetMapping("v1/getResult")
   public ResultData calculateUserUsedTime() {

      Map<String, Long> mapUserUsedTime = new HashMap<>();
      Map<String, Long> mapEventStart = new HashMap<>();
      Map<String, Long> mapEventStop = new HashMap<>();

      for (Event event : new GetService().getServiceResponse()) {
         String userId = event.getCustomerId();
         String workloadId = event.getWorkloadId();

         if ("start".equals(event.getEventType())) {
            mapEventStart.put(workloadId, event.getTimestamp());
         } else if ("stop".equals(event.getEventType())) {
            mapEventStop.put(workloadId, event.getTimestamp());
         }
      }

      Map<String, Long> saveResultMap = new HashMap<>();

      for (Event event : new GetService().getServiceResponse()) {
         String workloadId = event.getWorkloadId();
         Long startTime = mapEventStart.get(workloadId);
         Long stopTime = mapEventStop.get(workloadId);

         if (startTime != null && stopTime != null) {
            long timeSpent = stopTime - startTime;
            long currentTimeSpent = timeSpent + mapUserUsedTime.getOrDefault(event.getCustomerId(), 0L);

            mapUserUsedTime.put(event.getCustomerId(), currentTimeSpent);
            saveResultMap.put(event.getCustomerId(), currentTimeSpent);
         }
      }

      List<Result> results = new ArrayList<>();

      for (Map.Entry<String, Long> entry : saveResultMap.entrySet()) {
         results.add(new Result(entry.getKey(), entry.getValue()));
      }
      return new ResultData(results);
   }







   @GetMapping("/handle")
   public HttpEntity<ResultData> calculateUserUsedTime2() {
      //public List<Result> calculateUserUsedTime() {

      Map<String, Long> mapUserUsedTime = new HashMap<>();
      Map<String, Long> mapEventStart = new HashMap<>();
      Map<String, Long> mapEventStop = new HashMap<>();

      for (Event event : new GetService().getServiceResponse()) {

         String userId = event.getCustomerId();
         String workloadId = event.getWorkloadId();


         if ("start".equals(event.getEventType())) {
            mapUserUsedTime.put(userId, 0L);
            mapEventStart.put(workloadId, event.getTimestamp());

         } else if ("stop".equals(event.getEventType())) {
            mapUserUsedTime.put(userId, 0L);
            mapEventStop.put(workloadId, event.getTimestamp());
         }


      }

      Map<String, Long> saveResultMap = new HashMap<>();

      for (Event event : new GetService().getServiceResponse()) {

         long startTime = mapEventStart.get(event.getWorkloadId());
         long stopTime = mapEventStop.get(event.getWorkloadId());

         long timeSpent = stopTime - startTime;

         long currentTimeSpent = timeSpent + mapUserUsedTime.get(event.getCustomerId());

         // update user time
         mapUserUsedTime.put(event.getCustomerId(), currentTimeSpent);

         // save user time
         saveResultMap.put(event.getCustomerId(), currentTimeSpent);

      }


      List<Result> results = new ArrayList<>();


      for (Map.Entry<String, Long> entry : saveResultMap.entrySet()) {

         results.add(new Result(entry.getKey(), entry.getValue()));

      }


      try {
         // ... (rest of your existing code)

         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_JSON);

         ResultData resultData = new ResultData(results);
         HttpEntity<ResultData> requestEntity = new HttpEntity<>(resultData, headers);

         String url = "http://localhost:9090/v1/result";
         RestTemplate restTemplate = new RestTemplate();

         ResponseEntity<ResultData> responseEntity = restTemplate.postForEntity(url, requestEntity, ResultData.class);
         ResultData responseBody = responseEntity.getBody();

         System.out.println("Request successful");
         System.out.println("Response: " + responseBody.toString());

         // Return the ResponseEntity or extract the data as needed
         return responseEntity;
      } catch (HttpClientErrorException e) {
         System.out.println("Request failed with status code: " + e.getRawStatusCode());
         System.out.println("Response body: " + e.getResponseBodyAsString());
         throw e; // rethrow the exception if needed
      }
   }
}