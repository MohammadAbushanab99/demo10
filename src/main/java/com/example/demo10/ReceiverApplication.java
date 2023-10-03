package com.example.demo10;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import java.net.UnknownHostException;

@SpringBootApplication
public class ReceiverApplication {

	public static void main(String[] args) throws JsonProcessingException, UnknownHostException {
		SpringApplication.run(ReceiverApplication.class, args);
		RunningContainer.startAllContainers();
	}


}
