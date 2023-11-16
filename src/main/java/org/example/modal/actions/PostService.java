package org.example.modal.actions;

import org.example.modal.result.CalculateUserTime;
import org.example.modal.result.ResultData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@RestController
public class PostService {

    @PostMapping("customerPost")
    public void postServiceResponse(){
/*
        String url = "http://localhost:9090/v1/result";

        // my rest Template

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response=  restTemplate.patchForObject(url,new CalculateUserTime().calculateUserUsedTime(), ResponseEntity.class);

        System.out.println(response.getBody());
        // List<ResultData> postResponse = new ArrayList<>();


 */
    }


}
