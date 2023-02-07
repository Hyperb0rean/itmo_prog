package com.greg.lab8.server.commands;

import com.greg.lab8.common.util.data.Organization;
import com.greg.lab8.common.commands.exceptions.IllegalArgumentException;
import com.greg.lab8.server.util.CollectionManager;
import com.greg.lab8.server.util.DatabaseManager;
import com.greg.lab8.server.util.ServerCommandManager;


public class AddCommand extends Command{

    private final CollectionManager target;

    public AddCommand( ServerCommandManager manager, CollectionManager target) {
        super("add", "Добавить новый элемент в коллекцию",manager);
        this.target = target;

    }

    public CollectionManager getTarget() {
        return target;
    }

    @Override
    public boolean execute(String argument) {
        try{
            if(argument == null || argument.isEmpty()){
                Organization result = this.getManager().getInput().readOrganisation();
                result.generateId();

                if(target.getClass().equals(DatabaseManager.class))
                {
                    DatabaseManager databaseManager = (DatabaseManager) target;
                    databaseManager.add(result,getManager().getCurrentUser());
                }

//                target.getOrganizations().add(result);
                this.getManager().getOutput().write("Элемент успешно добавлен!");
                return true;

            }
            else throw new IllegalArgumentException("Невозможно применить команду без аргументов");
        } catch (Exception  e) {
            this.getManager().getOutput().error(e.getMessage());
            return false;
        }
    }
}
