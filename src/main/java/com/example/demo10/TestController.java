package com.example.demo10;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/receive")
public class TestController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SendQuery> receivePostRequest(@RequestBody String requestBody) throws IOException, InterruptedException {

        String imageName = "abushanab"; // Set the Docker image name
        String containerName = "container1"; // Set the Docker container name
        int hostPort = 8081; // Host port to map to the container's port

        // Build and run the Docker command
//        String[] dockerCommand = {
//                "docker", "run", "-d",
//                "--name", containerName,
//                "-p", hostPort + ":8080", // Map host port to container port
//                imageName
//        };


        List<FilterCondition> filterConditions = new ArrayList<>();
        List<String> propertyNames = new ArrayList<>();
        SendQuery filterRequest = new SendQuery();
        FilterCondition filterCondition = new FilterCondition();
        filterCondition.setValue(25);
        filterCondition.setField("age");
        filterCondition.setOperator("=");
        filterConditions.add(filterCondition);
        filterRequest.setCollectionName("Users");
        filterRequest.setConditions(filterConditions); // List<FilterCondition>
        filterRequest.setProperties(propertyNames);
        filterRequest.setQueryType("write");// List<String>

            String containerName1 = System.getenv("HOSTNAME");

            System.out.println("HOSTNAME" + containerName1);


            System.out.println("hi");
            return ResponseEntity.ok(filterRequest);
        }

    public static String getProcessOutput(Process process) throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream inputStream = process.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }
        return output.toString();
    }

}
