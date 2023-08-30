package test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;


public class SenderApplication {

    public static void main(String[] args) {
        SenderApplication senderApp = new SenderApplication();
        senderApp.runDockerContainer();
    }

    public void runDockerContainer() {
        String imageName = "abushanab"; // Set the Docker image name
        String containerName = "container1"; // Set the Docker container name
        int hostPort = 8080; // Host port to map to the container's port

        // Build and run the Docker command
        String[] dockerCommand = {
                "docker", "run", "-d",
                "--name", containerName,
                "-p", hostPort + ":8080", // Map host port to container port
                imageName
        };
//        String[] dockerStartCommand = {
//                "docker", "start",
//                containerName
//        };

        ProcessBuilder processBuilder = new ProcessBuilder(dockerCommand);

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Docker container started successfully.");

                try {
                    Thread.sleep(5000); // Wait for 5 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendPostRequest();
            } else {
                System.out.println("Error: Failed to start Docker container.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }



        String[] dockerInspectIpAddressCommand = {
                "docker", "inspect", "-f",
                "'{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}'",
                containerName
        };

        String[] dockerInspectPortCommand = {
                "docker", "inspect", "-f",
                "{{(index (index .NetworkSettings.Ports \"8080/tcp\") 0).HostPort}}",
                containerName

        };

        try {
            Process ipAddressProcess = new ProcessBuilder(dockerInspectIpAddressCommand).start();
            Process portProcess = new ProcessBuilder(dockerInspectPortCommand).start();

            String ipAddress =getProcessOutput(ipAddressProcess).trim().replace("'", "");
            String port = getProcessOutput(portProcess).trim().replace("'", "");

            System.out.println("Container IP Address: " + ipAddress);
            System.out.println("Container Port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] dockerStopCommand = {
                "docker", "stop",
                containerName
        };

        ProcessBuilder stopProcessBuilder = new ProcessBuilder(dockerStopCommand);
        try {
            Process stopProcess = stopProcessBuilder.start();
            int stopExitCode = stopProcess.waitFor();

            if (stopExitCode == 0) {
                System.out.println("Docker container stopped successfully.");
            } else {
                System.out.println("Error: Failed to stop Docker container.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }




    }

    public void sendPostRequest() throws UnknownHostException {
        String hostIpAddress = InetAddress.getLocalHost().getHostAddress();
        String receiverUrl = "http://localhost:8080/receive";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"message\": \"Hello, Receiver!\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(receiverUrl, requestEntity, String.class);
            System.out.println("Response from receiver: " + response.getBody());
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP Client Error: " + e.getMessage());
        } catch (ResourceAccessException e) {
            System.err.println("Resource Access Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());

        }
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