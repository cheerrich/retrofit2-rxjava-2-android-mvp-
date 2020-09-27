package com.example.mvp.base;

import java.io.Serializable;

public class BaseBean implements Serializable {
    private int statusCode;
    private int timestamp;
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
    public String errorMessage;

    @Override
    public String toString() {
        return "BaseBean{" +
                "statusCode=" + statusCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
