package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;
import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.utils.Constants;
import com.epam.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Denis on 12.05.2018.
 * Executor Class that handles batch acceptance
 * of requests to add new activity from the users
 */
public class ActivityAccept implements GeneralCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String acceptedActivities = request.getParameter("accepted");
        if(acceptedActivities != null && acceptedActivities.length() > 0) {
            ControllerHelper helper = new ControllerHelper();
            List<Activity> activities = helper.getListActivities(acceptedActivities);
            ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
            dao.acceptActivities(activities);
            activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
            request.getSession().setAttribute("Activities", activities);
            dao.closeConnection();
        }
        return Constants.ADDED_ACTIVITIES;
    }
}
