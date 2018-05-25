package com.denis.timetracking.mvc.controller.command.executors;

import com.denis.timetracking.mvc.controller.command.GeneralCommand;
import com.denis.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;
import com.denis.timetracking.mvc.model.dao.ActivityDao;
import com.denis.timetracking.mvc.model.entity.Activity;
import com.denis.timetracking.utils.Constants;
import com.denis.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 03.05.2018.
 * Executor Class that handles updated of activities
 */
public class ActivityUpdate implements GeneralCommand {
    /**
     *
     * @param request
     * @param response
     * @return url to forward
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String selection = request.getParameter("select");
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        ControllerHelper helper = new ControllerHelper();
        Activity activity = helper.createActivityBean(request);
        HttpSession session = request.getSession();
        List<Activity> activities = null;
        try {
            dao.update(activity);
            activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
        } catch (SQLException e) {
            session.setAttribute("Error", "Bad request");
            selection = Constants.ERROR;
            logger.debug(e);
        }
        logger.info("Update selection = " + selection);
        session.setAttribute("Activities", activities);
        dao.closeConnection();
        return selection;
    }
}