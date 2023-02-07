package com.greg.lab8.server.commands;

import com.greg.lab8.server.util.ServerCommandManager;

public abstract class Command implements Executable {

    private final String name;
    private final String description;
    private final ServerCommandManager manager;

    protected Command(String name, String description, ServerCommandManager manager) {
        this.name = name;
        this.description = description;
        this.manager = manager;
    }


    public String getDescription() {
        return this.description;
    }

    public ServerCommandManager getManager() {
        return manager;
    }


    public String getName() {
        return this.name;
    }

    @Override
    public boolean execute(String argument) {
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return this.getName() + " (" + this.getDescription() + ")";
    }

}
