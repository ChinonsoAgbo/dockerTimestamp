package org.example.modal.result;

import org.example.modal.actions.Get;
import org.example.modal.event.Event;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

      // get workstamps
      for (Event event : new Get().getServiceResponse()) {
         String userId = event.getCustomerId();
         String workloadId = event.getWorkloadId();

         if ("start".equals(event.getEventType())) {
            mapEventStart.put(workloadId, event.getTimestamp());
         } else if ("stop".equals(event.getEventType())) {
            mapEventStop.put(workloadId, event.getTimestamp());
         }
      }
      Map<String, Long> totalWorkload = new HashMap<>();
      Map<String, String> mapWorkId_mit_UserId = new HashMap<>();

      Map<String, Long> saveResultMap = new HashMap<>();

      // calculate workstamp <workloadID, totalWorkstamp >
      for (Event event : new Get().getServiceResponse()) {
         String workloadId = event.getWorkloadId();
         Long startTime = mapEventStart.get(workloadId);
         Long stopTime = mapEventStop.get(workloadId);

         if (startTime != null && stopTime != null) {
            long timeSpent = stopTime - startTime;
            totalWorkload.put(workloadId, totalWorkload.getOrDefault(workloadId, 0L) + timeSpent);
            mapWorkId_mit_UserId.put(workloadId,event.getCustomerId());
         }
      }
      // save ressults per user
      for (Map.Entry<String, Long> entry : totalWorkload.entrySet()) {
         String workloadId = entry.getKey();
         Long timeSpent = entry.getValue();
         String userId = mapWorkId_mit_UserId.get(workloadId);

         if (userId != null) {
            saveResultMap.put(userId, saveResultMap.getOrDefault(userId, 0L) + timeSpent);
         }
      }

      // result list
         List<Result> results = new ArrayList<>();

      for (Map.Entry<String, Long> entry : saveResultMap.entrySet()) {
         results.add(new Result(entry.getKey(), entry.getValue()));
      }
      return new ResultData(results);
   }


   @GetMapping("v1/getResult2")
   public ResultData calculateUserUsedTime22() {
      Map<String, Long> mapEventStart = new HashMap<>();
      Map<String, Long> mapEventStop = new HashMap<>();

      // get workstamps
      for (Event event : new Get().getServiceResponse()) {
         String workloadId = event.getWorkloadId();

         if ("start".equals(event.getEventType())) {
            mapEventStart.put(workloadId, event.getTimestamp());
         } else if ("stop".equals(event.getEventType())) {
            mapEventStop.put(workloadId, event.getTimestamp());
         }
      }

      Map<String, Long> totalWorkload = new HashMap<>();
      Map<String, String> mapWorkId_mit_UserId = new HashMap<>();

      // calculate workstamp <workloadID, totalWorkstamp >
      for (Event event : new Get().getServiceResponse()) {
         String workloadId = event.getWorkloadId();
         Long startTime = mapEventStart.get(workloadId);
         Long stopTime = mapEventStop.get(workloadId);

         if (startTime != null && stopTime != null) {
            long timeSpent = stopTime - startTime;
            totalWorkload.put(workloadId, totalWorkload.getOrDefault(workloadId, 0L) + timeSpent);
            mapWorkId_mit_UserId.put(workloadId, event.getCustomerId());
         }
      }

      // result list
      List<Result> results = new ArrayList<>();

      // calculate consumption per user
      Map<String, Long> saveResultMap = new HashMap<>();
      for (Map.Entry<String, Long> entry : totalWorkload.entrySet()) {
         String workloadId = entry.getKey();
         Long timeSpent = entry.getValue();
         String userId = mapWorkId_mit_UserId.get(workloadId);

         if (userId != null) {
            saveResultMap.put(userId, saveResultMap.getOrDefault(userId, 0L) + timeSpent);
         }
      }

      // convert the result to the desired format
      for (Map.Entry<String, Long> entry : saveResultMap.entrySet()) {
         results.add(new Result(entry.getKey(), entry.getValue()));
      }

      return new ResultData(results);
   }

}