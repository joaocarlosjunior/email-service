package com.joaocarlos.email_service.exceptions;

public class ValidationError {
    private String field;
    private String detail;

    public ValidationError(String field, String detail) {
        this.field = field;
        this.detail = detail;
    }

    public String getField() {
        return field;
    }

    public String getDetail() {
        return detail;
    }
}
