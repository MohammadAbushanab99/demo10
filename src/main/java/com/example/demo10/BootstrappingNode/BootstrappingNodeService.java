package com.example.demo10.BootstrappingNode;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BootstrappingNodeService {


    public String addNewUser(String userId) throws IOException, InterruptedException {

        LoadBalancer loadBalancer= new LoadBalancer();
        String containerName = loadBalancer.assignUserToCluster(userId);
        UserMap.getInstance().insert(userId,containerName);
        return containerName;

    }
}
