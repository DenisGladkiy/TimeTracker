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
public class ActualActivitySelect implements GeneralCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        List<Activity> allActivities = dao.getAll();
        dao.closeConnection();
        final ArrayList<Activity> actualActivities = new ArrayList<>();
        allActivities.forEach(activity -> {
            if (isActual(activity)) {
                actualActivities.add(activity);
            }});
        logger.debug("List of actual activities = " + actualActivities);
        request.setAttribute("Activities", actualActivities);
        return "/activities.jsp";
    }

    private boolean isActual(Activity activity){
        return !activity.isAddRequest() && !activity.isRemoveRequest() && !activity.isCompleted();
    }
}
