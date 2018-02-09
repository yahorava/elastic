package com.epam.elasticadddoc.exception;

public class ElasticException extends RuntimeException{
    public ElasticException() {
    }
    public ElasticException(String message, Throwable exception) {
        super(message, exception);
    }
    public ElasticException(String message) {
        super(message);
    }
    public ElasticException(Throwable exception) {
        super(exception);
    }
}
