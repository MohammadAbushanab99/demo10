package com.example.demo10.webApplication.DAO;

import com.example.demo10.NoSql.Document;
import com.example.demo10.NoSql.NoSQLQueryBuilder;
import com.example.demo10.BootstrappingNode.UserMap;
import com.example.demo10.webApplication.Admin.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDao {



    public static List<User> getUsers(String userId) throws IOException, InterruptedException {
        UserMap userMap = UserMap.getInstance();

        String containerName = userMap.getContainerName(userId);

        NoSQLQueryBuilder noSQLQueryBuilder = new NoSQLQueryBuilder("usersInfo");
        List<Document> documents = noSQLQueryBuilder.readMany(containerName);
        List<User> users = new ArrayList<>();
        for (int i = 0; i < documents.size();i++ ){
            Map map = documents.get(i).getData();
            User user = new User();
            user.setUserId((String) map.get("userId"));
            user.setName((String) map.get("name"));
            user.setPhone((String) map.get("phone"));
            user.setAddress((String) map.get("address"));
            users.add(user);
        }

        return users;
    }


    public static  void deleteUser(String userId) throws IOException, InterruptedException {
        UserMap userMap = UserMap.getInstance();
        String containerName = userMap.getContainerName(userId);

        NoSQLQueryBuilder noSQLQueryBuilder = new NoSQLQueryBuilder("usersInfo")
                .filter("userId",userId)
                .operator("=");
         noSQLQueryBuilder.deleteQuery(containerName);


         NoSQLQueryBuilder noSQLQueryBuilder1 = new NoSQLQueryBuilder("users")
                 .filter("userId",userId)
                 .operator("=");
         noSQLQueryBuilder1.deleteQuery(containerName);


        NoSQLQueryBuilder noSQLQueryBuilder2 = new NoSQLQueryBuilder("usersVm")
                .filter("containerName", containerName)
                .operator("=");

        Document document = noSQLQueryBuilder2.readQuery(containerName);

        int numberOfUsers = (Integer) document.getData().get("users");

        --numberOfUsers;

         NoSQLQueryBuilder noSQLQueryBuilder3 = new NoSQLQueryBuilder("usersVm")
                 .update("users",numberOfUsers)
                 .filter("containerName",containerName)
                 .operator("=");

         noSQLQueryBuilder3.updateQuery(containerName);


         NoSQLQueryBuilder noSQLQueryBuilder4 = new NoSQLQueryBuilder("balances")
                 .filter("userId",userId)
                 .operator("=");

         noSQLQueryBuilder4.deleteQuery(containerName);

    }

    public static boolean checkUser(String userId ,String password) throws IOException, InterruptedException {

        UserMap userMap = UserMap.getInstance();
        String containerName = userMap.getContainerName(userId);

        NoSQLQueryBuilder noSQLQueryBuilder = new NoSQLQueryBuilder("users")
                .filter("userId" ,userId)
                .operator("=")
                .filter("password",password)
                .operator("=");
        Document document= noSQLQueryBuilder.readQuery(containerName);

             if(document.getData().size() > 0)
                return true;


        return false;
    }
}
