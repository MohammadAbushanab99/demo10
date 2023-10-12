package com.example.demo10.NoSql;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class RequestDocuments {

    public RequestDocuments(){
    }
    @Async
    public synchronized List<Document> getDocuments(Query query, String containerName) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(query);

        String receiverUrl = "http://"+containerName+":8080/receive-query/readDocuments";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<List<Document>> responseEntity = restTemplate.exchange(
                    receiverUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {}
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                List<Document> responseList = responseEntity.getBody();
                System.out.println("Received a list of documents: " + responseList);
                return responseList;
            } else {
                System.err.println("Received non-OK status code: " + responseEntity.getStatusCode());
                return Collections.emptyList(); // Return an empty list or handle the error as needed
            }
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP Client Error: " + e.getMessage());
            return Collections.emptyList(); // Return an empty list or handle the error as needed
        } catch (ResourceAccessException e) {
            // Handle resource access errors (e.g., network issues)
            System.err.println("Resource Access Error: " + e.getMessage());
            return Collections.emptyList(); // Return an empty list or handle the error as needed
        } catch (Exception e) {
            // Handle other unexpected errors
            System.err.println("An unexpected error occurred: " + e.getMessage());
            return Collections.emptyList(); // Return an empty list or handle the error as needed
        }
    }

    @Async
    public synchronized Document getDocument(Query query,String containerName) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(query);

        String receiverUrl = "http://"+containerName+":8080/receive-query/readDocument";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Document> responseEntity = restTemplate.exchange(
                    receiverUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {}
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Document response = responseEntity.getBody();
                System.out.println("Received a list of documents: " + response);
                return response;
            } else {
                // Handle non-OK status codes here
                System.err.println("Received non-OK status code: " + responseEntity.getStatusCode());
                return null; // Return an empty list or handle the error as needed
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors (e.g., 4xx status codes)
            System.err.println("HTTP Client Error: " + e.getMessage());
            return null; // Return an empty list or handle the error as needed
        } catch (ResourceAccessException e) {
            // Handle resource access errors (e.g., network issues)
            System.err.println("Resource Access Error: " + e.getMessage());
            return null; // Return an empty list or handle the error as needed
        } catch (Exception e) {
            // Handle other unexpected errors
            System.err.println("An unexpected error occurred: " + e.getMessage());
            return null; // Return an empty list or handle the error as needed
        }
    }
    @Async
    public synchronized void sendDocuments(Query query ,String containerName) throws JsonProcessingException {
        System.out.println("container name = "+ containerName);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(query);

        String receiverUrl = "http://"+containerName+":8080/receive-query/write";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    receiverUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                System.out.println("documents SEND ");

            } else {
                // Handle non-OK status codes here
                System.err.println("Received non-OK status code: " + responseEntity.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors (e.g., 4xx status codes)
            System.err.println("HTTP Client Error: " + e.getMessage());
        } catch (ResourceAccessException e) {
            // Handle resource access errors (e.g., network issues)
            System.err.println("Resource Access Error: " + e.getMessage());
        } catch (Exception e) {
            // Handle other unexpected errors
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }

    }

}
