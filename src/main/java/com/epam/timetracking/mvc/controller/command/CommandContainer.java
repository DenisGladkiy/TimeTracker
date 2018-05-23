package com.epam.timetracking.mvc.controller.command;

import com.epam.timetracking.mvc.controller.command.executors.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Denis on 03.05.2018.
 * Class contains executors for all commands
 * that can come from jsp view
 */
public class CommandContainer {
    private static Logger logger = Logger.getLogger(CommandContainer.class);
    private Map<String, GeneralCommand> commands;

    /**
     * Instantiates a new Command container.
     */
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
        commands.put("usersActivities", new UsersActivities());
        commands.put("loginData", new Login());
    }

    /**
     * Get command executor.
     *
     * @param strCommand the String command
     * @return the general command
     */
    public GeneralCommand getCommand(String strCommand){
        logger.debug("CommandContainer command = " + strCommand) ;
        return commands.get(strCommand);
    }
}
