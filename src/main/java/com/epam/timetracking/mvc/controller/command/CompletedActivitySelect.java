package com.epam.timetracking.mvc.controller.command;

import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.entity.Activity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 03.05.2018.
 */
public class CompletedActivitySelect implements GeneralCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        List<Activity> allActivities = dao.getAll();
        dao.closeConnection();
        final ArrayList<Activity> completedActivities = new ArrayList<>();
        allActivities.forEach(activity -> {
            if (activity.isCompleted()) {
                completedActivities.add(activity);
            }});
        logger.debug("List of activities to remove = " + completedActivities);
        request.setAttribute("Activities", completedActivities);
        return "/activities.jsp";
    }
}
