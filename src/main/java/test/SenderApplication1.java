package test;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.PullImageResultCallback;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class SenderApplication1 {

    public static void main(String[] args) throws UnknownHostException {
        SenderApplication senderApp = new SenderApplication();
        senderApp.runDockerContainer();
    }

    public void runDockerContainer() throws UnknownHostException {
        String imageName = "abushanab"; // Set the Docker image name
        String containerName = "container1"; // Set the Docker container name
        int hostPort = 8080; // Host port to map to the container's port

        // Docker client setup
        DockerClient dockerClient = DockerClientBuilder.getInstance(DefaultDockerClientConfig.createDefaultConfigBuilder().build()).build();

        // Pull the Docker image
        dockerClient.pullImageCmd(imageName)
                .exec(new PullImageResultCallback())
                .awaitSuccess();

        // Create container

        CreateContainerResponse containerResponse = dockerClient.createContainerCmd(imageName)
                .withName(containerName)
                .withHostConfig(HostConfig.newHostConfig()
                        .withPortBindings((List<PortBinding>) Ports.Binding.bindPort(hostPort))
                )
                .exec();

        String containerId = containerResponse.getId();

        // Start container
        dockerClient.startContainerCmd(containerId).exec();

        try {
            Thread.sleep(5000); // Wait for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sendPostRequest(dockerClient, containerId);

        // Inspect container
        InspectContainerResponse containerInfo = dockerClient.inspectContainerCmd(containerId).exec();
        String ipAddress = containerInfo.getNetworkSettings().getNetworks().values().iterator().next().getIpAddress();
        String port = containerInfo.getNetworkSettings().getPorts().getBindings().get(ExposedPort.tcp(hostPort))[0].getHostPortSpec();

        System.out.println("Container IP Address: " + ipAddress);
        System.out.println("Container Port: " + port);

        // Stop container
        dockerClient.stopContainerCmd(containerId).exec();

        // Clean up
       // dockerClient.removeContainerCmd(containerId).exec();
    }

    public void sendPostRequest(DockerClient dockerClient, String containerId) throws UnknownHostException {
        String receiverUrl = "http://" + getContainerIpAddress(dockerClient, containerId) + ":8080/receive";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"message\": \"Hello, Receiver!\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(receiverUrl, HttpMethod.POST, requestEntity, String.class);
            System.out.println("Response from receiver: " + response.getBody());
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP Client Error: " + e.getMessage());
        } catch (ResourceAccessException e) {
            System.err.println("Resource Access Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public String getContainerIpAddress(DockerClient dockerClient, String containerId) {
        InspectContainerResponse containerInfo = dockerClient.inspectContainerCmd(containerId).exec();
        return containerInfo.getNetworkSettings().getNetworks().values().iterator().next().getIpAddress();
    }
}