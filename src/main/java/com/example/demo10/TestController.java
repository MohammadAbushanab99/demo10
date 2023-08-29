package com.example.demo10;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receive")
public class TestController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> receivePostRequest(@RequestBody String requestBody) {
        System.out.println("Received POST request with body: " + requestBody);

        String responseMessage = "Request received successfully!";
        return ResponseEntity.ok(responseMessage);
    }

}
