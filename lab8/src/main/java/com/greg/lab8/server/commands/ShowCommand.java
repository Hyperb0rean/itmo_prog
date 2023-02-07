package com.greg.lab8.server.commands;

import com.greg.lab8.common.commands.exceptions.IllegalArgumentException;
import com.greg.lab8.server.util.CollectionManager;
import com.greg.lab8.server.util.FileManager;
import com.greg.lab8.server.util.ServerCommandManager;

import java.util.concurrent.atomic.AtomicReference;

public class ShowCommand extends Command{

    private final CollectionManager target;

    public ShowCommand(ServerCommandManager manager, CollectionManager target) {
        super("show", "Вывести в стандартный поток вывода все элементы коллекции в строковом представлении",manager);
        this.target = target;
    }

    public CollectionManager getTarget() {
        return target;
    }

    @Override
    public boolean execute(String argument) {
        try{
            if(argument == null || argument.isEmpty()){
                StringBuilder result = new StringBuilder("[");
                target.getOrganizations().stream().forEach(o -> result.append(o.toString()).append(","));
                this.getManager().getOutput().write(result.append("]").toString());
                return true;
            }
            else throw new IllegalArgumentException("Эта команда не принимает аргументов");
        } catch (IllegalArgumentException e) {
            this.getManager().getOutput().error(e.getMessage());
            return false;
        }
    }
}
