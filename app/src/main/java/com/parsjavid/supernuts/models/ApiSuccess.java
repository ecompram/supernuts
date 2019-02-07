package com.parsjavid.supernuts.models;

import java.util.List;

public class ApiSuccess<T> {
    private int resultKey;
    private String errorMessage;
    private String message;
    private List<T> data;
    public int getResultKey() {
        return resultKey;
    }

    public void setResultKey(int resultKey) {
        this.resultKey = resultKey;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
