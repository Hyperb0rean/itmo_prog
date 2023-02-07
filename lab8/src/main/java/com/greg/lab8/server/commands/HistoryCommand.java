package com.greg.lab8.server.commands;

import com.greg.lab8.common.commands.exceptions.IllegalArgumentException;
import com.greg.lab8.server.util.ServerCommandManager;

import java.util.Queue;

public class HistoryCommand extends Command{
    private final Queue<String> target;

    public HistoryCommand(ServerCommandManager manager) {
        super("history", "Ввывести последние 10 команд (без их аргументов)",manager);
        this.target = manager.getHistory();
    }

    public Queue<String> getTarget() {
        return target;
    }

    @Override
    public boolean execute(String argument) {
        try{
            if(argument == null || argument.isEmpty()){
                StringBuilder result = new StringBuilder();
                 target.stream().forEach(s -> result.append(s).append("\n"));
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
