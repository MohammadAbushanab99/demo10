package com.example.demo10.BootstrappingNode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserMap {

    private final Map<String, String> usersMap;

    private static UserMap instance;

    public UserMap() {
        this.usersMap = new ConcurrentHashMap<>();
    }

    public static synchronized UserMap getInstance() {
        if (instance == null) {
            instance = new UserMap();
            instance.usersMap.put("admin","bossNode");
        }
        return instance;
    }

    public void insert(String userId,String containerName){
        usersMap.put(userId,containerName);
    }

    public String getContainerName(String userId){
        if(usersMap.containsKey(userId)) {
            return usersMap.get(userId);
        }else
            return null;

    }
}
