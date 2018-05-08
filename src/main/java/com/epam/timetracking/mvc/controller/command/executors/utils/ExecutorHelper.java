package com.epam.timetracking.mvc.controller.command.executors.utils;

import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.entity.Activity;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Denis on 08.05.2018.
 */
public class ExecutorHelper {
    Logger logger = Logger.getLogger(ExecutorHelper.class);

    public List<Activity> getActivitiesBySelection(HttpServletRequest request, ActivityDao dao) {
        String selection = request.getParameter("select");
        logger.debug("Helper selection = " + selection);
        int userId;
        switch (selection) {
            case "completedActivities.jsp":
                return dao.getCompleted();
            case "activities.jsp":
                return dao.getActual();
            case "addedActivities.jsp":
                return dao.getAdded();
            case "removedActivities.jsp":
                return dao.getRemoved();
            case "userIndex.jsp":
                userId = Integer.valueOf(request.getParameter("userId"));
                return dao.getByUserId(userId);
            case "activitiesByUser.jsp":
                userId = Integer.valueOf(request.getParameter("userId"));
                return dao.getByUserId(userId);
            case "adminIndex.jsp":
                return null;
        }
        return null;
    }
}
