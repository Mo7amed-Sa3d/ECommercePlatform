package com.ecommerce.ecommerceplatform.exception;

public class ErrorResponse {
    private int statusCode;
    private String message;
    private Long timestamp;

    public ErrorResponse() {}
    public ErrorResponse(int statusCode, String message, Long timestamp) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
