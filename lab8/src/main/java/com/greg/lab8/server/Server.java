package com.greg.lab8.server;

import com.greg.lab8.server.commands.*;
import com.greg.lab8.server.util.io.*;
import com.greg.lab8.server.util.*;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public final class Server {

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);
        ServerCommandManager manager = new ServerCommandManager();
        int port = 1337;
        manager.setInput(new RequestInput(port,manager));
        manager.setOutput(new ResponseOutput(manager));

        CollectionManager collection = new DatabaseManager();


        manager.getCommands().put("help", new HelpCommand(manager));
        manager.getCommands().put("info", new InfoCommand(manager,collection));
        manager.getCommands().put("show", new ShowCommand(manager,collection));
        manager.getCommands().put("add", new AddCommand(manager,collection));
        manager.getCommands().put("update", new UpdateCommand(manager,collection));
        manager.getCommands().put("remove_by_id", new RemoveByIdCommand(manager,collection));
        manager.getCommands().put("clear", new ClearCommand(manager,collection));
        manager.getCommands().put("save", new SaveCommand(manager,collection));
        manager.getCommands().put("execute_script", new ExecuteScriptCommand(manager));
        manager.getCommands().put("exit", new ExitCommand(manager));
        manager.getCommands().put("remove_head", new RemoveHeadCommand(manager,collection));
        manager.getCommands().put("remove_lower", new RemoveLowerCommand(manager,collection));
        manager.getCommands().put("history", new HistoryCommand(manager));
        manager.getCommands().put("filter_contains_name", new FilterContainsNameCommand(manager,collection));
        manager.getCommands().put("print_ascending", new PrintAscendingCommand(manager,collection));
        manager.getCommands().put("print_field_descending_employees_count", new PrintFieldDescendingCommand(manager,collection));
        manager.getCommands().put("register",new RegisterCommand(manager,collection));
        manager.getCommands().put("login",new LoginCommand(manager,collection));





        new Thread(() -> {
            while (manager.isProgramState()){
                String line = scanner.nextLine();
                if(line.equals("exit")){
                    manager.executeCommand(line+"  ");
                }
            }
        }).start();


        new Thread(() -> {
            while (manager.isProgramState()){
                try {
                    Thread.sleep(5000);
                    manager.executeCommand(manager.getInput().read());
               } catch (InterruptedException e) {
                    e.printStackTrace();
               }

            }

        }).start();
    }
}
