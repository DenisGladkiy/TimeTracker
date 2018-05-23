package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;
import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.utils.Constants;
import com.epam.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 12.05.2018.
 * Executor Class that handles batch acceptance
 * of requests to add new activity from the users
 */
public class ActivityAccept implements GeneralCommand {
    /**
     *
     * @param request
     * @param response
     * @return url to forward
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = Constants.ADDED_ACTIVITIES;
        String acceptedActivities = request.getParameter("accepted");
        if(acceptedActivities != null && acceptedActivities.length() > 0) {
            HttpSession session = request.getSession();
            ControllerHelper helper = new ControllerHelper();
            List<Activity> activities = helper.getListActivities(acceptedActivities);
            ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
            try {
                dao.acceptActivities(activities);
                activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
            } catch (SQLException e) {
                session.setAttribute("Error", "Bad request");
                forward = Constants.ERROR;
                logger.debug(e);
            }
            session.setAttribute("Activities", activities);
            dao.closeConnection();
        }
        logger.info("Activity accept forward = " + forward);
        return forward;
    }
}
