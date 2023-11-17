package org.example;

import org.example.modal.actions.PostService;
import org.example.modal.result.CalculateUserTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

       // new PostService().postServiceResponse();

         new CalculateUserTime().calculateUserUsedTime2();



    }



}