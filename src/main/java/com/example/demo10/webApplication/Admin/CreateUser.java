package com.example.demo10.webApplication.Admin;

import com.example.demo10.NoSql.NoSQLQueryBuilder;

import java.io.IOException;

public class CreateUser {

    public void createNewUser(User user, String containerName) throws IOException, InterruptedException {
        NoSQLQueryBuilder noSQLQueryBuilder = new NoSQLQueryBuilder("usersInfo")
                .insert("userId",user.getUserId())
                .insert("name",user.getName())
                .insert("phone",user.getPhone())
                .insert("address",user.getAddress());
        noSQLQueryBuilder.writeQuery(containerName);

        NoSQLQueryBuilder noSQLQueryBuilder1 = new NoSQLQueryBuilder("users")
                .insert("userId",user.getUserId())
                .insert("password",user.getPassword())
                .insert("containerName",containerName)
                .insert("userType","user");
        noSQLQueryBuilder1.writeQuery(containerName);

        NoSQLQueryBuilder noSQLQueryBuilder2 = new NoSQLQueryBuilder("balances")
                .insert("userId",user.getUserId())
                .insert("balance",1000.0);
        noSQLQueryBuilder2.writeQuery(containerName);



    }


}
