package com.epam.timetracking.mvc.controller.command;

import com.epam.timetracking.mvc.model.dao.DaoManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Denis on 03.05.2018.
 */
public interface GeneralCommand {
    Logger logger = Logger.getLogger(GeneralCommand.class);
    DaoManager manager = DaoManager.getInstance();
    String execute(HttpServletRequest request, HttpServletResponse response);
}