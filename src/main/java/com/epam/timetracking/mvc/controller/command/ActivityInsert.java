package com.epam.timetracking.mvc.controller.command;

import com.epam.timetracking.mvc.model.dao.AbstractDao;
import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Denis on 03.05.2018.
 */
public class ActivityInsert implements GeneralCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ControllerHelper helper = new ControllerHelper();
        Activity activity = helper.createActivityBean(request);
        logger.debug("Activity to insert = " + activity);
        AbstractDao dao = manager.getDao("ACTIVITY");
        dao.insert(activity);
        dao.closeConnection();
        return "/index.jsp";
    }
}
