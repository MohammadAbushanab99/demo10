package com.example.demo10;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
@RestController
@RequestMapping("/send-message")
public class SendMessage {


            @PostMapping
            @ResponseStatus(HttpStatus.CREATED)
            public String sendMessageToContainerB(@RequestBody String requestBody1) throws JsonProcessingException {
                ObjectMapper objectMapper = new ObjectMapper();

                String requestBody = objectMapper.writeValueAsString("filterRequest");
                System.out.println("requestBody1" + requestBody1);
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                String containerBUrl = "http://containerB:8081/api/receive-message";
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(containerBUrl, String.class);

                // Check the HTTP status code
                if (responseEntity.getStatusCodeValue() == 200) {
                    String responseBody = responseEntity.getBody();
                    System.out.println("Response: " + responseBody);
                } else {
                    System.out.println("HTTP Request failed with status code: " + responseEntity.getStatusCodeValue());
                }

                System.out.println("response = " + requestBody1);
                return "Received response from Container B: " + requestBody1;
            }


    }

