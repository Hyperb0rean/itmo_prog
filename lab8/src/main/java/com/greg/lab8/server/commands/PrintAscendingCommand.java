package com.greg.lab8.server.commands;

import com.greg.lab8.common.commands.exceptions.IllegalArgumentException;
import com.greg.lab8.server.util.CollectionManager;
import com.greg.lab8.server.util.FileManager;
import com.greg.lab8.server.util.ServerCommandManager;

public class PrintAscendingCommand extends Command{
    private final CollectionManager target;

    public PrintAscendingCommand(ServerCommandManager manager, CollectionManager target) {
        super("print_ascending", "Вывести элементы коллекции в порядке возрастания",manager);
        this.target = target;
    }

    public CollectionManager getTarget() {
        return target;
    }

    @Override
    public boolean execute(String argument) {
        try{

            if( argument == null || argument.isEmpty()){
                StringBuilder result = new StringBuilder();
                target.getOrganizations().stream().sorted().forEach(o-> result.append(o).append("\n"));
                this.getManager().getOutput().write(result.toString());
                return true;
            }
            else throw new IllegalArgumentException("Эта команда не принимает аргументов");
        } catch (IllegalArgumentException e) {
            this.getManager().getOutput().error(e.getMessage());
            return false;
        }
    }
}
