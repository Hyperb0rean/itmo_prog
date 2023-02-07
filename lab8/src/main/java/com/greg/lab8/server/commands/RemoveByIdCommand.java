package com.greg.lab8.server.commands;

import com.greg.lab8.common.util.data.Organization;
import com.greg.lab8.common.commands.exceptions.IllegalArgumentException;
import com.greg.lab8.common.commands.exceptions.NoSuchElementException;
import com.greg.lab8.server.util.CollectionManager;
import com.greg.lab8.server.util.DatabaseManager;
import com.greg.lab8.server.util.FileManager;
import com.greg.lab8.server.util.ServerCommandManager;

import java.sql.SQLException;
import java.util.Iterator;

public class RemoveByIdCommand extends Command{

    private final CollectionManager target;

    public RemoveByIdCommand(ServerCommandManager manager, CollectionManager target) {
        super("remove_by_id", "Удалить элемент из коллекции по его id",manager);
        this.target = target;
    }

    public CollectionManager getTarget() {
        return target;
    }

    @Override
    public boolean execute(String argument) {
        try{
            if(!argument.isEmpty()){
                int id = Integer.parseInt(argument.split(" ")[0]);
                boolean idFoundFlag = false;
                Iterator<Organization> iterator = target.getOrganizations().iterator();
                while (iterator.hasNext()) {
                    Organization s = iterator.next(); // must be called before you can call iterator.remove()
                    if(s.getId() == id)
                    {
                        if(target.getClass().equals(DatabaseManager.class))
                        {
                            DatabaseManager databaseManager = (DatabaseManager) target;
                            databaseManager.remove(id, getManager().getCurrentUser());
                        }
                        iterator.remove();
                        idFoundFlag=true;
                    }
                }
                if (!idFoundFlag) throw new NoSuchElementException("Не существует элемента с таким id");
                this.getManager().getOutput().write("Элемент успешно удален!");
                return true;
            }
            else throw new IllegalArgumentException("Невозможно применить команду без аргументов");
        } catch (IllegalArgumentException | NumberFormatException | NoSuchElementException | SQLException e) {
            this.getManager().getOutput().error(e.getMessage());
            return false;
        }
    }
}
