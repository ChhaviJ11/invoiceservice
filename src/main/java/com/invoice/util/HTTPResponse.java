package com.invoice.util;

public class HTTPResponse
{

    private Object  data;
    private String message;
    private short status;

    public HTTPResponse(Object data, String message, short status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }
}