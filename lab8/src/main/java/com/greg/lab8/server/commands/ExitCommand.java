package com.greg.lab8.server.commands;

import com.greg.lab8.server.util.ServerCommandManager;

public class ExitCommand extends Command{


    public ExitCommand(ServerCommandManager manager) {
        super("exit", "Завершить программу (без сохранения в файл)",manager);
    }

    @Override
    public boolean execute(String argument) {
        this.getManager().setProgramState(false);
        System.out.println("Сервер остановлен");
        System.exit(0);
        return true;
    }
}
