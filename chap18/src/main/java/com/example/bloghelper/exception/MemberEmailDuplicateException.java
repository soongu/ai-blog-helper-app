package com.example.bloghelper.exception;

public class MemberEmailDuplicateException extends RuntimeException {
    public MemberEmailDuplicateException(String message) {
        super(message);
    }
}
