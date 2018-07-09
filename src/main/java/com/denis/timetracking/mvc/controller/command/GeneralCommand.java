package com.denis.timetracking.mvc.controller.command;

import com.denis.timetracking.mvc.model.dao.DaoFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Denis on 03.05.2018.
 * General interface for command executors.
 * It contains Logger and DaoFactory instances
 * as well as abstract method - execute
 */
public interface GeneralCommand {
    Logger logger = Logger.getLogger(GeneralCommand.class);
    DaoFactory manager = new DaoFactory();
    String execute(HttpServletRequest request, HttpServletResponse response);
}
