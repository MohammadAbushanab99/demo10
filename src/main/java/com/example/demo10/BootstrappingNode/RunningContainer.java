package com.example.demo10.BootstrappingNode;
import org.springframework.stereotype.Component;
import java.io.IOException;


@Component
public class RunningContainer {
    private String imageName = "abushanab";
    private String networkName = "my-network";
    private String containerName = "container";
    private int startingPort = 8080;
    private int numberOfContainers = 4;


    public void startAllContainers() {


        for (int i = 0; i < numberOfContainers; i++) {
            String containerName = (i == 0) ? "bossNode" : (this.containerName + i);
            int hostPort = startingPort + i;

            if (startDockerContainer( containerName, hostPort)) {
                System.out.println("Docker container " + containerName + " started successfully.");
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error: Failed to start Docker container " + containerName + ".");
            }

        }

    }

    private  boolean startDockerContainer( String containerName, int hostPort) {
        String[] dockerCommand = {
                "docker", "run",
                "--name", containerName,
                "--network", networkName,
                "-d", "-p", hostPort + ":8080",
                imageName
        };

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(dockerCommand);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String addNewDockerContainer( int number, int hostPort) {
        String containerName = this.containerName + number;
        String[] dockerCommand = {
                "docker", "run",
                "--name", containerName,
                "--network", networkName,
                "-d", "-p", hostPort + ":8080",
                imageName
        };

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(dockerCommand);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            return containerName;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return " ";
        }
    }

}
