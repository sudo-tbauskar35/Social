package com.social.backend.model.api;

import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Model for response sent by API call
 */
public class ApiResponse {

    private HttpStatus status;
    private List<String> errors;
    private String message;

    private String token;

    public ApiResponse() {
    }

    public ApiResponse(Builder builder) {
        this.status = builder.status;
        this.errors = builder.errors;
        this.message = builder.message;
        this.token = builder.token;
    }

    public static class Builder {
        private HttpStatus status;
        private List<String> errors;
        private String message;

        private String token;

        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder errors(List<String> errors) {
            this.errors = errors;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public ApiResponse build() {
            return new ApiResponse(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
