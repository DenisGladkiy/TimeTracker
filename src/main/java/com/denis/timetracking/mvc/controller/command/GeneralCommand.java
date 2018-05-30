package com.denis.timetracking.mvc.controller.command;

import com.denis.timetracking.mvc.model.dao.DaoManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Denis on 03.05.2018.
 * General interface for command executors.
 * It contains Logger and DaoManager instances
 * as well as abstract method - execute
 */
public interface GeneralCommand {
    Logger logger = Logger.getLogger(GeneralCommand.class);
    DaoManager manager = new DaoManager();
    String execute(HttpServletRequest request, HttpServletResponse response);
}
