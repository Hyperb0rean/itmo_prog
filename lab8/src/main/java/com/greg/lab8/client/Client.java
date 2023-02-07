package com.greg.lab8.client;


import com.greg.lab8.client.util.ClientCommandManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public final class Client {
    private static DatagramSocket socket;
    private static InetAddress address;

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ClientCommandManager manager = new ClientCommandManager();

        while (manager.isProgrammState()) {
            manager.vallidateCommand(scanner.nextLine());
        }
    }

}
