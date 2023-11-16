package org.example.modal.result;

import org.example.modal.actions.GetService;
import org.example.modal.event.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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
    */
   @GetMapping("getTimespent")
   //public ResultData calculateUserUsedTime() {
   public List<Result> calculateUserUsedTime() {

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
         mapUserUsedTime.put(event.getCustomerId(),currentTimeSpent);

         // save user time
         saveResultMap.put(event.getCustomerId(), currentTimeSpent);

      }


      List<Result> results = new ArrayList<>();


      for (Map.Entry<String, Long> entry : saveResultMap.entrySet()) {

         results.add(new Result(entry.getKey(), entry.getValue()));

      }

/*
      String url = "http://localhost:9090/v1/result";

      RestTemplate restTemplate = new RestTemplate();

      restTemplate.patchForObject(url,new ResultData(results), ResponseEntity.class);



 */

     // return new ResultData(results);

      return results;

   }
}

