package com.example.demo10;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReceiveMessage {

        @GetMapping("/receive-message")
        @ResponseStatus(HttpStatus.OK)
        public String receiveMessageFromContainerA() {
            return "Message received from Container A!";
        }


}
