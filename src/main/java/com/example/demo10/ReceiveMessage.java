package com.example.demo10;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReceiveMessage {

        @GetMapping("/receive-message")
        @ResponseStatus(HttpStatus.OK)
        public String receiveMessageFromContainerA() {
            return "Message received from Container A!";
        }


}
