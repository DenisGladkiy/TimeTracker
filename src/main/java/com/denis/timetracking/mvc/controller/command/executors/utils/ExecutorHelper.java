package com.denis.timetracking.mvc.controller.command.executors.utils;

import com.denis.timetracking.mvc.model.dao.ActivityDao;
import com.denis.timetracking.mvc.model.entity.Activity;
import com.denis.timetracking.utils.Constants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 08.05.2018.
 * Helper class that provides to executor a list of activities
 * according to the source of the request
 */
public class ExecutorHelper {
    /**
     * The Logger.
     */
    Logger logger = Logger.getLogger(ExecutorHelper.class);

    /**
     * Gets activities by selection.
     *
     * @param request
     * @param dao
     * @return the activities by selection
     * @throws SQLException the sql exception
     */
    public List<Activity> getActivitiesBySelection(HttpServletRequest request, ActivityDao dao) {
        String selection = request.getParameter("select");
        logger.info("ExecutorHelper selection = " + selection);
        int userId;
        switch (selection) {
            case Constants.COMPLETED_ACTIVITIES:
                return dao.getCompleted();
            case Constants.ACTIVITIES:
                return dao.getActual();
            case Constants.ADDED_ACTIVITIES:
                return dao.getAdded();
            case Constants.REMOVED_ACTIVITIES:
                return dao.getRemoved();
            case Constants.USER_INDEX:
                userId = Integer.valueOf(request.getParameter("userId"));
                return dao.getByUserId(userId);
            case Constants.ACTIVITIES_BY_USER:
                userId = Integer.valueOf(request.getParameter("userId"));
                return dao.getByUserId(userId);
            case Constants.ADMIN_INDEX:
                return null;
        }
        return null;
    }
}
