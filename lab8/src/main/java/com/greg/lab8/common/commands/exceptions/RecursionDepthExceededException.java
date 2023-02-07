package com.greg.lab8.common.commands.exceptions;

public class RecursionDepthExceededException extends Exception{
    public RecursionDepthExceededException(String message) {
        super(message);
    }
}
