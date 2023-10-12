package com.example.demo10.webApplication.DAO;

import com.example.demo10.NoSql.Document;
import com.example.demo10.NoSql.NoSQLQueryBuilder;
import com.example.demo10.BootstrappingNode.UserMap;
import com.example.demo10.webApplication.Transactions.Transaction;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionDao {
    UserMap userMap = UserMap.getInstance();

    public void createTransaction(String userId,Double transaction,double balance) throws IOException, InterruptedException {
      String containerName = userMap.getContainerName(userId);

        Date currentTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String timeAsString = dateFormat.format(currentTime);

        NoSQLQueryBuilder noSQLQueryBuilder = new NoSQLQueryBuilder("transaction")
                .insert("userId",userId)
                .insert("transaction",transaction)
                .insert("time",timeAsString);
        noSQLQueryBuilder.writeQuery(containerName);

        NoSQLQueryBuilder noSQLQueryBuilder1 = new NoSQLQueryBuilder("balances")
                .update("balance",balance)
                .filter("userId",userId)
                .operator("=");
        noSQLQueryBuilder1.updateQuery(containerName);

    }

    public Double getBalance(String userId) throws IOException, InterruptedException {

        String containerName = userMap.getContainerName(userId);

        NoSQLQueryBuilder noSQLQueryBuilder = new NoSQLQueryBuilder("balances")
                .filter("userId",userId)
                .operator("=");

        Document document = noSQLQueryBuilder.readQuery(containerName);
             if(document.getData().size() > 0){
                 return (Double) document.getData().get("balance");
             }
        return 0.0;
    }


    public List<Transaction> getTransaction(String userId) throws IOException, InterruptedException {

        String containerName = userMap.getContainerName(userId);

        NoSQLQueryBuilder noSQLQueryBuilder = new NoSQLQueryBuilder("transaction")
                .filter("userId",userId)
                .operator("=");
        List<Document> documents = noSQLQueryBuilder.readMany(containerName);

        List<Transaction> transactions = new ArrayList<>();

        for (int i = 0; i < documents.size();i++){
            Map <String,Object> data = documents.get(i).getData();

            Transaction transaction = new Transaction();
            transaction.setUserId(userId);
            transaction.setAmount((Double) data.get("transaction"));
            transaction.setTime((String) data.get("time"));

            transactions.add(transaction);



        }

        return transactions;
    }


}
