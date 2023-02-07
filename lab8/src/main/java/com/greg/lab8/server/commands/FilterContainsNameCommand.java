package com.greg.lab8.server.commands;

import com.greg.lab8.common.commands.exceptions.IllegalArgumentException;
import com.greg.lab8.server.util.CollectionManager;
import com.greg.lab8.server.util.FileManager;
import com.greg.lab8.server.util.ServerCommandManager;

public class FilterContainsNameCommand extends  Command{
    private final CollectionManager target;

    public FilterContainsNameCommand(ServerCommandManager manager, CollectionManager target) {
        super("filter_contains_name", "Выывести элементы, значение поля name которых содержит заданную подстроку",manager);
        this.target = target;
    }

    public CollectionManager getTarget() {
        return target;
    }

    @Override
    public boolean execute(String argument) {
        try{
            if(!argument.isEmpty()){
                StringBuilder result = new StringBuilder();
                target.getOrganizations().stream().filter(x -> x.getName().contains(argument)).forEach(o-> result.append(o).append("\n"));
                this.getManager().getOutput().write(result.toString());
                return true;
            }
            else throw new IllegalArgumentException("Нужно ввести подстроку");
        } catch (IllegalArgumentException e) {
            this.getManager().getOutput().error(e.getMessage());
            return false;
        }
    }
}
