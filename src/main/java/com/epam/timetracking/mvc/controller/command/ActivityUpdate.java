package com.epam.timetracking.mvc.controller.command;

import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Denis on 03.05.2018.
 */
public class ActivityUpdate implements GeneralCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("id = " + request.getParameter("id"));
        logger.debug("name = " + request.getParameter("name"));
        logger.debug("description = " + request.getParameter("description"));
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        ControllerHelper helper = new ControllerHelper();
        Activity activity = helper.createActivityBean(request);
        dao.update(activity);
        dao.closeConnection();
        String selection = request.getParameter("select");
        logger.debug("Update selection = " + selection);
        switch (selection){
            case "selectCompleted":
                request.setAttribute("select", "selectCompleted");
                return "/pages/completedActivities.jsp";
            case "selectActual":
                return "/pages/activities.jsp";
        }
        return "/pages/activities.jsp";
    }
}
