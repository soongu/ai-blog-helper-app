package com.study.springprj.chap07.exception;

public class ServerException extends Exception {

    public ServerException() {
    }

    public ServerException(String message) {
        super(message);
    }
}
