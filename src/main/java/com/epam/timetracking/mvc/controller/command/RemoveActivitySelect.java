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
public class RemoveActivitySelect implements GeneralCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        List<Activity> allActivities = dao.getAll();
        dao.closeConnection();
        final ArrayList<Activity> removeActivities = new ArrayList<>();
        allActivities.forEach(activity -> {
            if (activity.isRemoveRequest()) {
                removeActivities.add(activity);
            }});
        logger.debug("List of activities to remove = " + removeActivities);
        request.setAttribute("Activities", removeActivities);
        return "/activities.jsp";
    }
}
