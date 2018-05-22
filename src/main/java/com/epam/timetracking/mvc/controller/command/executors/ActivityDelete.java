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
 * Created by Denis on 03.05.2018.
 * Executor Class that handles activity removal by ADMIN
 */
public class ActivityDelete implements GeneralCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String selection = request.getParameter("select");
        HttpSession session = request.getSession();
        ControllerHelper helper = new ControllerHelper();
        Activity activity = helper.createActivityBean(request);
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        List<Activity> activities = null;
        try {
            dao.delete(activity);
            activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
        } catch (SQLException e) {
            session.setAttribute("Error", "Bad request");
            selection = Constants.ERROR;
            logger.debug(e);
        }
        dao.closeConnection();
        session.setAttribute("Activities", activities);
        logger.info("Activity delete forward = " + selection);
        return selection;
    }
}
