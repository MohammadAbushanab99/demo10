package com.example.demo10.NoSql;
import com.example.demo10.NoSql.FilterCondition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Query {


    private String queryType;
    private String collectionName;
    private List<FilterCondition> conditions;
    private List<String> properties;
    private Map<String,Object> newDoc;

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public List<FilterCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<FilterCondition> conditions) {
        this.conditions = conditions;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }


    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public Map<String, Object> getNewDoc() {
        return newDoc;
    }

    public void setNewDoc(Map<String, Object> newDoc) {
        this.newDoc = newDoc;
    }


}
