package com.study.springprj.chap07.exception;

public class ClientException extends Exception {

    public ClientException() {
    }

    public ClientException(String message) {
        super(message);
    }
}
