package com.example.demo10;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Document implements Comparable<Document> {

    private int documentId;
    private Map<String, Object> data;

    public int getDocumentId() {
        return documentId;
    }


    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

//    public int compareTo(Document o,String propertyName) {
//        // Ensure both objects have the "age" property
//        if (data.containsKey(propertyName) && o.data.containsKey(propertyName)) {
//            // Retrieve the "age" property values
//            Integer thisAge = (Integer) data.get(propertyName);
//            Integer otherAge = (Integer) o.data.get(propertyName);
//
//            // Compare the "age" property values
//            return thisAge.compareTo(otherAge);
//        }
//
//        // If either object doesn't have the "age" property, consider them equal
//        return 0;
//    }

    @Override
    public int compareTo(Document o) {
        return 0;
    }


//    @Override
//    public int compareTo(Document o,String propertyName) {
//        // Ensure both objects have the "age" property
//        if (data.containsKey(propertyName) && o.data.containsKey(propertyName)) {
//            // Retrieve the "age" property values
//            Integer thisAge = (Integer) data.get(propertyName);
//            Integer otherAge = (Integer) o.data.get(propertyName);
//
//            // Compare the "age" property values
//            return thisAge.compareTo(otherAge);
//        }
//
//        // If either object doesn't have the "age" property, consider them equal
//        return 0;
//    }
}






