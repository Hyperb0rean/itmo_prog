package com.greg.lab8.server.util.io;

import java.nio.charset.StandardCharsets;

public class Response {
    private String message;
    private MessageType messageType;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Response(String message, MessageType messageType) {
        this.message = message;
        this.messageType = messageType;
    }
    public byte[] getBytes(){
        switch (messageType){
            case COMMON:return ("0" + message).getBytes(StandardCharsets.UTF_8);
            case ERROR: return ("1" + message).getBytes(StandardCharsets.UTF_8);
            default: return message.getBytes(StandardCharsets.UTF_8);
        }
    }
}
