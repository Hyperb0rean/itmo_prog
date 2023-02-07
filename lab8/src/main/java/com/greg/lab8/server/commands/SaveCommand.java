package com.greg.lab8.server.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greg.lab8.common.commands.exceptions.IllegalArgumentException;
import com.greg.lab8.server.util.CollectionManager;
import com.greg.lab8.server.util.FileManager;
import com.greg.lab8.server.util.ServerCommandManager;

import java.io.*;

public class SaveCommand extends Command{
    private final CollectionManager target;

    public SaveCommand(ServerCommandManager manager, CollectionManager target) {
        super("save", "Сохранить коллекцию в файл", manager);
        this.target = target;
    }

    public CollectionManager getTarget() {
        return target;
    }

    @Override
    public boolean execute(String argument) {
        try{
            if(argument == null || argument.isEmpty()){
                //File file = new File(System.getenv("LAB5"));
                File file = new File("C:\\Users\\jewel\\Documents\\Java\\itmo_university\\lab5\\lab\\lab-server\\src\\main\\java\\com\\greg\\server\\util\\test.json");

                OutputStreamWriter oswr = new OutputStreamWriter(new FileOutputStream(file));

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                String result = gson.toJson(target.getOrganizations());
                oswr.write(result);
                oswr.close();
                System.out.println("Коллекция сохранена успешно");
                return true;
            }
            else throw new IllegalArgumentException("Эта команда не принимает аргументов");
        } catch (IllegalArgumentException | IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
