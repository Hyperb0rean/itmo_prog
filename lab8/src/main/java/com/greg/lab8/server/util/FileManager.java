package com.greg.lab8.server.util;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.greg.lab8.common.util.data.Organization;

import java.io.*;
import java.util.*;

import com.greg.lab8.common.commands.exceptions.IllegalInputFromFileException;


public class FileManager extends CollectionManager {
    public FileManager() {

        StringBuilder text = new StringBuilder();
        try {
            //File file = new File(System.getenv("LAB5"));
            File file = new File("C:\\Users\\jewel\\Documents\\Java\\itmo_university\\lab5\\lab\\lab-server\\src\\main\\java\\com\\greg\\server\\util\\test.json");
            FileReader fr = new FileReader(file);
            Scanner scanner = new Scanner(fr);
            while (scanner.hasNextLine()){
                text.append(scanner.nextLine());
            }
            fr.close();
        } catch (IOException e) {
            System.err.println("Файл не был найден " + e.getMessage());
        }
        GsonBuilder builder = new GsonBuilder();
//        builder.setDateFormat("MMM dd, yyyy HH:mm:ss");
        this.organizations = new LinkedList<>(
                (ArrayList<Organization>) builder.create().fromJson(
                        text.toString(), new TypeToken<List<Organization>>(){}.getType()
                )
        );
        for (Organization o: this.organizations) {
            o.generateId();
            o.setCreationDate();
           try {
              if(!o.vallidateInput()) throw new IllegalInputFromFileException("Некоторые поля не соответствуют условию");

           }catch (NullPointerException | IllegalInputFromFileException e){
               System.err.println("Не корректные данные в файле\n" + e.getMessage());
           }
        }
        this.initDate = new Date();
        this.modDate = initDate;
    }
}
