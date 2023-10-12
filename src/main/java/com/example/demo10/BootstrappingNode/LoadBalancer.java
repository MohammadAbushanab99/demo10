package com.example.demo10.BootstrappingNode;

import com.example.demo10.NoSql.Document;
import com.example.demo10.NoSql.NoSQLQueryBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class LoadBalancer {

    private final int usersNumbers = 10;


    public String assignUserToCluster(String userId) throws IOException, InterruptedException {
         String containerName = "";
         boolean userAdded = false;

        NoSQLQueryBuilder noSQLQueryBuilder = new NoSQLQueryBuilder("usersVm");
        List<Document> documents= noSQLQueryBuilder.readMany("bossNode");
        System.out.println();
        int numberOfUsers = 0;
        for (int i = 0; i < documents.size(); i++) {
            Map<String, Object> data = documents.get(i).getData();
            numberOfUsers = (Integer) data.get("users");

            if (numberOfUsers < usersNumbers) {
                containerName = (String) data.get("containerName");
                //data.put("users", ++numberOfUsers);
                numberOfUsers++;
                NoSQLQueryBuilder noSQLQueryBuilder1 = new NoSQLQueryBuilder("usersVm")
                        .update("users", numberOfUsers)
                        .filter("containerName", containerName)
                        .operator("=");

                noSQLQueryBuilder1.updateQuery("bossNode");
                userAdded =true;
                break;
            }
        }
        if(!userAdded) {

            Document lastDocument = documents.get(documents.size() - 1);
            RunningContainer runningContainer = new RunningContainer();
            int port = (Integer) lastDocument.getData().get("port") + 1;
            String newContainerName = runningContainer.addNewDockerContainer(documents.size(),port);

            if(!containerName.isEmpty()){
                containerName= newContainerName;
            }

        }

     return containerName;

    }
}
