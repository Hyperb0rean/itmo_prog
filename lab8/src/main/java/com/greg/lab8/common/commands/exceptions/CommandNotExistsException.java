package com.greg.lab8.common.commands.exceptions;

public class CommandNotExistsException extends Exception{
    public CommandNotExistsException(String message) {
        super(message);
    }
}
