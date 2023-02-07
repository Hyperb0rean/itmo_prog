package com.greg.lab8.server.commands;

import com.greg.lab8.common.util.data.Organization;
import com.greg.lab8.common.commands.exceptions.IllegalArgumentException;
import com.greg.lab8.common.commands.exceptions.NoSuchElementException;
import com.greg.lab8.server.util.CollectionManager;
import com.greg.lab8.server.util.DatabaseManager;
import com.greg.lab8.server.util.ServerCommandManager;
import com.greg.lab8.server.util.FileManager;

import java.sql.SQLException;
import java.util.Iterator;

public class UpdateCommand extends Command {

    private final CollectionManager target;


    public UpdateCommand(ServerCommandManager manager, CollectionManager target) {
        super("update", "Обновить значение элемента коллекции, id которого равен заданному",manager);
        this.target = target;
    }

    public CollectionManager getTarget() {
        return target;
    }

    @Override
    public boolean execute(String argument) {
        try {
            if (argument != null || !argument.isEmpty()) {
                Organization result = this.getManager().getInput().readOrganisation();
                result.setId(Integer.parseInt(argument));
                boolean idFoundFlag = false;
                Iterator<Organization> iterator = target.getOrganizations().iterator();
                while (iterator.hasNext()) {
                    Organization s = iterator.next(); // must be called before you can call iterator.remove()
                    if(s.getId()==result.getId())
                    {
                        iterator.remove();
                        idFoundFlag=true;

                    }
                }
                if (!idFoundFlag) throw new NoSuchElementException("Не существует элемента с таким id");
                else {
                    if(target.getClass().equals(DatabaseManager.class))
                    {
                        DatabaseManager databaseManager = (DatabaseManager) target;
                        databaseManager.remove(Integer.parseInt(argument), getManager().getCurrentUser());
                        databaseManager.add(result, getManager().getCurrentUser());
                    }

                    target.getOrganizations().add(result);
                }

                this.getManager().getOutput().write("Элемент успешно обновлен!");
                return true;
            } else throw new IllegalArgumentException("Невозможно применить команду без аргументов");
        } catch (IllegalArgumentException | NumberFormatException | NoSuchElementException | SQLException e) {
            this.getManager().getOutput().error(e.getMessage());
            return false;
        }
    }


}
