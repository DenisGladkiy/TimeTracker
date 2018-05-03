package com.epam.timetracking.mvc.controller.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Denis on 03.05.2018.
 */
public class CommandContainer {
    private Map<String, GeneralCommand> commands;

    public CommandContainer(){
        initContainer();
    }

    private void initContainer(){
        commands = new HashMap<>();
        commands.put("insert", new ActivityInsert());
        commands.put("delete", new ActivityDelete());
        commands.put("update", new ActivityUpdate());
        commands.put("selectActual", new ActualActivitySelect());
        commands.put("selectAdded", new AddActivitySelect());
        commands.put("selectToRemove", new RemoveActivitySelect());
        commands.put("selectCompleted", new CompletedActivitySelect());
    }

    public GeneralCommand getCommand(String strCommand){
        return commands.get(strCommand);
    }
}
