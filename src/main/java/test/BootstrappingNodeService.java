package test;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.PullImageResultCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BootstrappingNodeService {

    private String databaseImage ="shanabDb";
    private User userRepository;

    DockerClient dockerClient = DockerClientBuilder.getInstance(DefaultDockerClientConfig.createDefaultConfigBuilder().build()).build();

    private List<VirtualMachine>  vmRepository;

    public BootstrappingNodeService() {
        dockerClient.pullImageCmd(databaseImage)
                .exec(new PullImageResultCallback())
                .awaitSuccess();
    }

    public void initializeCluster() {
        // Logic to initialize cluster and assign users to VMs
    }


    public CreateContainerResponse runContainer(String containerName, int hostPort ){

        VirtualMachine virtualMachine = new VirtualMachine();

        CreateContainerResponse containerResponse = dockerClient.createContainerCmd(databaseImage)
                .withName(containerName)
                .withHostConfig(HostConfig.newHostConfig()
                        .withPortBindings((List<PortBinding>) Ports.Binding.bindPort(hostPort))
                )
                .exec();


        runContainerId(virtualMachine,containerResponse);

        return containerResponse;

    }

    public void runContainerId(VirtualMachine virtualMachine ,CreateContainerResponse  containerResponse){
        String containerId = containerResponse.getId();
        virtualMachine.setVmId(containerResponse.getId());
        dockerClient.startContainerCmd(containerId).exec();

    }

    public void putIpAddress(String containerId){
        InspectContainerResponse containerInfo = dockerClient.inspectContainerCmd(containerId).exec();
        String ipAddress = containerInfo.getNetworkSettings().getNetworks().values().iterator().next().getIpAddress();

    }

    public void putPort(String containerId ,int hostPort){
        InspectContainerResponse containerInfo = dockerClient.inspectContainerCmd(containerId).exec();
        String port = containerInfo.getNetworkSettings().getPorts().getBindings().get(ExposedPort.tcp(hostPort))[0].getHostPortSpec();
    }

    public void stopContainer(String containerId){
        dockerClient.stopContainerCmd(containerId).exec();
    }
    public User assignVMToUser(User user) {
        // Logic to assign a VM to a user
        return user;
    }
}
