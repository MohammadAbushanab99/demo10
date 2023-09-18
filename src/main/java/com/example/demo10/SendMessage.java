package com.example.demo10;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
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
                String containerBUrl = "http://containerB:8080/api/receive-message";

                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

                try {
                    ResponseEntity<String> responseEntity = restTemplate.exchange(
                            containerBUrl,
                            HttpMethod.GET,
                            requestEntity,
                            String.class
                    );

                    if (responseEntity.getStatusCode() == HttpStatus.OK) {
                        System.out.println("documents SEND ");
                        System.out.println(responseEntity.getBody());
                    } else {

                        System.err.println("Received non-OK status code: " + responseEntity.getStatusCode());
                    }
                } catch (HttpClientErrorException e) {

                    System.err.println("HTTP Client Error: " + e.getMessage());
                } catch (ResourceAccessException e) {

                    System.err.println("Resource Access Error: " + e.getMessage());
                } catch (Exception e) {

                    System.err.println("An unexpected error occurred: " + e.getMessage());
                }

                System.out.println("response = " + requestBody1);
                return "Received response from Container B: " + requestBody1;
            }


    }

