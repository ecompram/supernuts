package com.parsjavid.supernuts.models;

public class ApiSuccess {
    private int resultKey;
    private String errorMessage;
    private String message;

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
}
