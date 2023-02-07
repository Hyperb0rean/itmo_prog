package com.greg.lab8.client.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RequestManager {

    private boolean responseCode = false;
    private String responseMessage;

    public Request makeRequest(String user,String command,String argument){
        Request request = new Request();
        request.setUser(user);
        request.setCommand(command);
        request.setArgument(argument);
        request.setData(null);
        return request;
    }

    public Request makeRequest(String user,String command, Object data){
        Request request = new Request();
        request.setUser(user);
        request.setCommand(command);
        request.setArgument("");
        request.setData(data);
        return request;
    }
    public Request makeRequest(String user, String command,String argument, Object data){

        Request request = new Request();
        request.setUser(user);
        request.setArgument(argument);
        request.setCommand(command);
        request.setData(data);
        return request;
    }



    synchronized public boolean sendRequest(Request request)  {

        ByteBuffer buffer = ByteBuffer.wrap(request.getBytes());
        SocketAddress serverAddress = new InetSocketAddress("localhost",1337);
        try {
            DatagramChannel client = DatagramChannel.open().bind(null);
            client.send(buffer,serverAddress);

            buffer = ByteBuffer.allocate(4096);
            ((Buffer)buffer).clear();
            serverAddress = client.receive(buffer);
            ((Buffer)buffer).flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            int len = Integer.parseInt(new String(bytes));
            ((Buffer)buffer).clear();
            StringBuilder msg = new StringBuilder("");
            for(int offset=0; offset<len; offset+=128) {
                serverAddress = client.receive(buffer);
                ((Buffer)buffer).flip();
                byte[] temp = new byte[buffer.remaining()];
                buffer.get(temp);
                msg.append(new String(temp));
                ((Buffer)buffer).clear();
            }

            if(msg.toString().toCharArray()[0] == '0'){
                responseMessage = msg.substring(1);
                System.out.println(msg.substring(1));
                responseCode = true;
            }else if (msg.toString().toCharArray()[0] == '1'){
                responseMessage = msg.substring(1);
                System.err.println(msg.substring(1));
                responseCode=false;
            }

        } catch (IOException e) {
            System.out.println("Не удалось отослать сообщение на сервер. Подробнее: \n" + e.getMessage());
        }


        return true;
    }

    public boolean isResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
