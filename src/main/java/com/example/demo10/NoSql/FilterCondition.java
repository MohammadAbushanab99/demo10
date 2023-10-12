package com.example.demo10.NoSql;

public class FilterCondition {
    private String field;
    private String operator;
    private Object value;


    public String getField() {
        return field;
    }

    public String getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
