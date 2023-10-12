package com.example.demo10;

import com.example.demo10.BootstrappingNode.RunningContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.UnknownHostException;

@EnableAsync
@SpringBootApplication
public class ReceiverApplication {

	public static void main(String[] args) throws JsonProcessingException, UnknownHostException, InterruptedException {
		SpringApplication.run(ReceiverApplication.class, args);
		RunningContainer runningContainer = new RunningContainer();
		runningContainer.startAllContainers();
	}


}
