package com.example.demo10.NoSql;

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

    @Override
    public int compareTo(Document o) {
        return 0;
    }

}






