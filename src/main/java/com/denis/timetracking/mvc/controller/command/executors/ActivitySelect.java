package com.denis.timetracking.mvc.controller.command.executors;

import com.denis.timetracking.mvc.controller.command.GeneralCommand;
import com.denis.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;
import com.denis.timetracking.mvc.model.dao.ActivityDao;
import com.denis.timetracking.mvc.model.entity.Activity;
import com.denis.timetracking.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 03.05.2018.
 * Executor Class that handles selection of activities
 * according to required conditions
 */
public class ActivitySelect implements GeneralCommand {
    /**
     *
     * @param request
     * @param response
     * @return url to forward
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String selection = request.getParameter("select");
        logger.debug("Selection selection = " + selection);
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        HttpSession session = request.getSession();
        List<Activity> activities = null;
        try {
            activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
        } catch (SQLException e) {
            session.setAttribute("Error", "Bad request");
            selection = Constants.ERROR;
            logger.debug(e);
        }
        logger.debug("List of selected activities = " + activities);
        dao.closeConnection();
        session.setAttribute("Activities", activities);
        logger.info("Activity select forward = " + selection);
        return selection;
    }
}
