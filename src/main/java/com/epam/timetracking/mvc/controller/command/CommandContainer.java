package com.epam.timetracking.mvc.controller.command;

import com.epam.timetracking.mvc.controller.command.executors.*;

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
        commands.put("insertActivity", new ActivityInsert());
        commands.put("deleteActivity", new ActivityDelete());
        commands.put("updateActivity", new ActivityUpdate());
        commands.put("selectActivity", new ActivitySelect());
        commands.put("acceptActivities", new ActivityAccept());
        commands.put("insertUser", new UserInsert());
        commands.put("selectUser", new UserSelect());
        commands.put("deleteUser", new UserDelete());
        commands.put("updateUser", new UserUpdate());
        commands.put("usersActivities", new UsersActivities());
        commands.put("loginData", new Login());
    }

    public GeneralCommand getCommand(String strCommand){
        return commands.get(strCommand);
    }
}
