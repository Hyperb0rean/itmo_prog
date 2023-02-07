package com.greg.lab8.server.util.io;

import com.greg.lab8.common.util.data.*;
import com.greg.lab8.server.util.ServerCommandManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class RequestInput implements Readable {

    private final int port;
    private DatagramChannel server;
    private SocketAddress currentClient;
    private ServerCommandManager manager;

    public RequestInput(int port, ServerCommandManager manager) {
        this.port = port;
        this.manager = manager;
        InetSocketAddress address = new InetSocketAddress("localhost", port);
        System.out.println("Сервер запущен по адресу #" + address);

    }


    @Override
    public Organization readOrganisation() {
        return manager.getTempOrganisation();
    }

    @Override
    public String read() {

        InetSocketAddress address = new InetSocketAddress("localhost", port);
        try {
            DatagramChannel server = DatagramChannel.open().bind(address);
            this.server = server;
            System.out.println("Чтение данных...");
        } catch (IOException e) {
            //System.err.println("Произошла ошибка при попытке чтения данных. Подробнее\n" + e.getMessage());
        }

        ByteBuffer buffer = ByteBuffer.allocate(4096);
        try {
            this.currentClient = server.receive(buffer);
        } catch (IOException e) {
            System.err.println("Произошла ошибка при попытке чтения данных. Подробнее\n" + e.getMessage());
        }

        byte[] buf = new byte[buffer.position()];
        for(int i =0; i< buf.length;i++){
            buf[i] = buffer.get(i);
        }
        String msg = new String(buf);
        return msg;
    }

    @Override
    public Scanner getScanner() {
        return null;
    }

    @Override
    public SocketAddress getCurrentClient() {
        return currentClient;
    }

    @Override
    public User readUser() {
        return manager.getCurrentUser();
    }


}
