package com.example.demo10.NoSql;


import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NoSQLQueryBuilder {

    private String collectionName;
    private List<FilterCondition> conditions;
    private List<String> properties;
    private Map<String,Object> newDocument;
    FilterCondition filterCondition;

    public NoSQLQueryBuilder(){
    }
    public NoSQLQueryBuilder(String collectionName) {
        this.collectionName = collectionName;
        this.conditions = new ArrayList<>();
        this.properties = new ArrayList<>();
        this.newDocument = new HashMap<>();
    }

    public NoSQLQueryBuilder operator(String operator) {

        this.filterCondition.setOperator(operator);
        this.conditions.add(this.filterCondition);
        return this;
    }

    public NoSQLQueryBuilder filter(String field, Object value) {
        FilterCondition filterCondition = new FilterCondition();
        filterCondition.setField(field);
        filterCondition.setValue(value);
        this.filterCondition = filterCondition;
        return this;
    }

    public NoSQLQueryBuilder property(String property) {
        this.properties.add(property);
        return this;
    }
    public NoSQLQueryBuilder insert(String key ,Object value) {
        newDocument.put(key,value);
        return this;
    }

    public NoSQLQueryBuilder update(String key ,Object value) {
        newDocument.put(key,value);
        return this;
    }



    public synchronized void writeQuery(String containerName) throws IOException, InterruptedException {

        Query query = new Query();

        Document document = new Document();
        document.setData(newDocument);
        if(!newDocument.isEmpty()) {
         query.setCollectionName(collectionName);
         query.setQueryType("writeQuery");
         query.setNewDoc(newDocument);
         RequestDocuments requestDocuments = new RequestDocuments();
         requestDocuments.sendDocuments(query, containerName);


        }else{
            throw new QueryException("Not Valid Query.");
        }

    }


    public synchronized void updateQuery(String containerName) throws IOException, InterruptedException {

        Document document = new Document();
     Query query = new Query();
        document.setData(newDocument);
        if(!newDocument.isEmpty() && !conditions.isEmpty()) {
            query.setCollectionName(collectionName);
            query.setQueryType("updateQuery");
            query.setNewDoc(newDocument);
            query.setConditions(conditions);
            RequestDocuments requestDocuments = new RequestDocuments();
            requestDocuments.sendDocuments(query, containerName);

        }else{
            throw new QueryException("Not Valid Query.");
        }


    }

    public synchronized void updateManyQuery(String containerName) throws IOException, InterruptedException {

        Document document = new Document();
        Query query = new Query();
        document.setData(newDocument);
        if(!newDocument.isEmpty() && !conditions.isEmpty()) {
            query.setCollectionName(collectionName);
            query.setQueryType("updateManyQuery");
            query.setNewDoc(newDocument);
            query.setConditions(conditions);
            RequestDocuments requestDocuments = new RequestDocuments();
            requestDocuments.sendDocuments(query, containerName);

        }else{
            throw new QueryException("Not Valid Query.");
        }


    }


    public synchronized void deleteQuery(String containerName) throws IOException, InterruptedException {

        Query query = new Query();

        if(!conditions.isEmpty()) {
            query.setCollectionName(collectionName);
            query.setQueryType("deleteQuery");
            query.setConditions(conditions);
            RequestDocuments requestDocuments = new RequestDocuments();
            requestDocuments.sendDocuments(query, containerName);
        }else{
            throw new QueryException("Not Valid Query.");
        }


    }

    public synchronized void deleteMany(String containerName) throws IOException, InterruptedException {

        Query query = new Query();

        if(!conditions.isEmpty()) {
            query.setCollectionName(collectionName);
            query.setQueryType("deleteManyQuery");
            query.setConditions(conditions);
            RequestDocuments requestDocuments = new RequestDocuments();
            requestDocuments.sendDocuments(query, containerName);

        }else{
            throw new QueryException("Not Valid Query.");
        }


    }

    public synchronized void deleteCollection(String containerName) throws IOException, InterruptedException {

        Query query = new Query();

        if(!collectionName.isEmpty()) {
            query.setCollectionName(collectionName);
            query.setQueryType("deleteCollection");
            RequestDocuments requestDocuments = new RequestDocuments();
            requestDocuments.sendDocuments(query, containerName);

        }else{
            throw new QueryException("Not Valid Query.");
        }


    }

    public synchronized Document readQuery(String containerName) throws IOException, InterruptedException {

        Query query = new Query();
        Document document = new Document();

        if(!conditions.isEmpty()) {
            query.setQueryType("readQuery");
            query.setConditions(conditions);
            query.setCollectionName(collectionName);
            RequestDocuments requestDocuments = new RequestDocuments();
            document = requestDocuments.getDocument(query, containerName);

        }else{
            throw new QueryException("Not Valid Query.");
        }

        return document;
    }

    public synchronized List<Document> readMany(String containerName) throws IOException, InterruptedException {

        List<Document> document = new ArrayList<>();

        if(!collectionName.isEmpty()) {
           Query query = new Query();
            query.setQueryType("readQueries");
            query.setConditions(conditions);
            query.setCollectionName(collectionName);
            RequestDocuments requestDocuments = new RequestDocuments();
            document = requestDocuments.getDocuments(query, containerName);

        }else{
            throw new QueryException("Not Valid Query.");
        }
        return document;
    }

    class QueryException extends RuntimeException {
        public QueryException(String message) {
            super(message);
        }
    }


}
