package com.example.demo10;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
public class ReceiveMessage {


    @RestController
    public class ContainerBController {
        @GetMapping("/receive-message")
        public String receiveMessageFromContainerA() {
            return "Message received from Container A!";
        }
    }

}
