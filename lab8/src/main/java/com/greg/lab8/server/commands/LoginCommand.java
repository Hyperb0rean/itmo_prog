package com.greg.lab8.server.commands;

import com.greg.lab8.common.commands.exceptions.IllegalArgumentException;
import com.greg.lab8.common.util.data.Organization;
import com.greg.lab8.common.util.data.User;
import com.greg.lab8.server.util.CollectionManager;
import com.greg.lab8.server.util.DatabaseManager;
import com.greg.lab8.server.util.ServerCommandManager;

public class LoginCommand extends Command{

    CollectionManager target;
    public LoginCommand(ServerCommandManager manager, CollectionManager target) {
        super("login", "Войти в приложение с существующего аккаунта", manager);
        this.target = target;
    }

    public CollectionManager getTarget() {
        return target;
    }

    @Override
    public boolean execute(String argument) {
        try{
            if(argument == null || argument.isEmpty()){
                User user = this.getManager().getInput().readUser();


                if(target.getClass().equals(DatabaseManager.class))
                {
                    DatabaseManager databaseManager = (DatabaseManager) target;
                    databaseManager.findUser(user);
                }


                this.getManager().getOutput().write("Вход выполнен успешно, здравствуйте," + user.getName());
                return true;

            }
            else throw new IllegalArgumentException("Невозможно применить команду без аргументов");
        } catch (Exception  e) {
            this.getManager().getOutput().error(e.getMessage());
            return false;
        }
    }
}
