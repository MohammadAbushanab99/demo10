package com.example.demo10;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class RunningContainer {

    public  static  void startAllContainers() throws UnknownHostException, JsonProcessingException {

//        try {
//            String[] command = {"docker-compose", "up", "-d"};
//            Process process = new ProcessBuilder(command).start();
//            int exitCode = process.waitFor();
//            if (exitCode == 0) {
//                System.out.println("Containers started successfully.");
//            } else {
//                System.err.println("Failed to start containers.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String imageName = "abushanab"; // Set the Docker image name
        String containerName = "container"; // Set the Docker container name
        int hostPort = 8080; // Host port to map to the container's port
        //sendPostRequest();
        // Build and run the Docker command
//        String[] dockerCommand = {
//                "docker", "run", "-d",
//                "--name", containerName,
//                "-p", hostPort + ":8080", // Map host port to container port
//                imageName
//        };
        for(int i = 0; i < 4 ;i++) {
            String name;
            if(i==0){
                name="bossNode";
            }else
                name = containerName + i;

            String[] dockerCommand = {
                    "docker", "run",
                    "--name", name,
                    "--network", "my-network",
                    "-d", "-p", hostPort + ":8080",
                    imageName
            };
            ++hostPort;
//
//
            ProcessBuilder processBuilder = new ProcessBuilder(dockerCommand);

            try {
                Process process = processBuilder.start();
                int exitCode = process.waitFor();

                if (exitCode == 0) {
                    System.out.println("Docker container started successfully.");

                    try {
                        Thread.sleep(60000); // Wait for 5 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //sendPostRequest2();
                } else {
                    System.out.println("Error: Failed to start Docker container.");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        int i =0;
        while (i<100){

            sendPostRequest(String.valueOf(i));
            i++;
        }

    }
    @Async
    public static void sendPostRequest(String i) throws UnknownHostException, JsonProcessingException {
        String hostIpAddress = InetAddress.getLocalHost().getHostAddress();
        String receiverUrl = "http://container1:8080/receive-query/write";
        Query filterRequest = new Query();
//        filterRequest.setCollectionName("usersVm");
//        Map<String,Object> a = new HashMap<>();
//        a.put("containerName","containerA");
//        a.put("users",0);
//        a.put("id","b6aed6d898aa");
//        filterRequest.setNewDoc(a);
//        filterRequest.setQueryType("writeQuery");

        filterRequest.setCollectionName("users");
        Map<String,Object> a = new HashMap<>();
        a.put("name",("ahmad"+i));
        a.put("userId",i);
        a.put("containerName","container1");
        filterRequest.setNewDoc(a);
        filterRequest.setQueryType("writeQuery");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(filterRequest);
        System.out.println(requestBody);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Query> responseEntity = restTemplate.exchange(
                    receiverUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Query.class
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
    }
}
