package com.greg.lab8.server.util.io;

import com.greg.lab8.server.util.ServerCommandManager;


import java.io.IOException;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;


public class ResponseOutput implements Writable{


    private DatagramSocket socket;
    private final byte[] buf = new byte[4096];
    private final ServerCommandManager manager;

    public ResponseOutput(ServerCommandManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean write(String output) {
        Response response = new Response(output,MessageType.COMMON);
        return send(response);
    }

    private boolean send(Response response) {
        String message =  response.getMessageType().ordinal()+ response.getMessage();
        Integer len = message.length();
        ByteBuffer buffer = ByteBuffer.wrap(len.toString().getBytes(StandardCharsets.UTF_8));

        try {
            DatagramChannel server = DatagramChannel.open().bind(null);
            server.send(buffer,manager.getInput().getCurrentClient());
            for(int offset=0; offset<message.length(); offset+=128) {
                String chunk;
                chunk=message.substring(offset,Math.min(offset+128,message.length()));
                buffer = ByteBuffer.wrap(chunk.getBytes(StandardCharsets.UTF_8));
                server.send(buffer,manager.getInput().getCurrentClient());
                ((Buffer)buffer).clear();
            }
        } catch (IOException e) {
            System.out.println("Не удалось отослать сообщение на клиент. Подробнее: \n" + e.getMessage());
        }
        return true;
    }

    @Override
    public boolean error(String errMessage) {
        Response response = new Response(errMessage,MessageType.ERROR);
        return send(response);
    }

}
