package com.example.demo10;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
public class SendMessage {

        @RestController
        public class ContainerAController {


            @GetMapping("/send-message")
            public String sendMessageToContainerB(@RequestBody String requestBody1) throws JsonProcessingException {
                ObjectMapper objectMapper = new ObjectMapper();

                String requestBody = objectMapper.writeValueAsString("filterRequest");
                System.out.println("requestBody1" + requestBody1);
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                String containerBUrl = "http://containerB/receive-message";
                String response = restTemplate.getForObject(containerBUrl, String.class);


                System.out.println("response = " + response);
                return "Received response from Container B: " + response;
            }
        }

    }

