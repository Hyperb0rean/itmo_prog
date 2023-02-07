package com.greg.lab8.server.commands;

import com.greg.lab8.common.util.data.Organization;
import com.greg.lab8.common.commands.exceptions.IllegalArgumentException;
import com.greg.lab8.server.util.CollectionManager;
import com.greg.lab8.server.util.FileManager;
import com.greg.lab8.server.util.ServerCommandManager;

import java.util.Collections;

public class PrintFieldDescendingCommand extends Command {
    private final CollectionManager target;

    public PrintFieldDescendingCommand(ServerCommandManager manager, CollectionManager target) {
        super("print_field_descending_employees_count", "Вывести значения поля employeesCount всех элементов в порядке убывания",manager);
        this.target = target;
    }

    public CollectionManager getTarget() {
        return target;
    }

    @Override
    public boolean execute(String argument) {
        try {

            if (argument == null ||  argument.isEmpty()) {
                StringBuilder result = new StringBuilder();
                target.getOrganizations().stream().map(Organization::getEmployeesCount).sorted(Collections.reverseOrder()).forEach(o-> result.append(o).append("\n"));
                this.getManager().getOutput().write(result.toString());
                return true;
            } else throw new IllegalArgumentException("Эта команда не принимает аргументов");
        } catch (IllegalArgumentException e) {
            this.getManager().getOutput().error(e.getMessage());
            return false;
        }
    }
}