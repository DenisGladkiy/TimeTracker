package com.denis.timetracking.mvc.controller.command.executors;

import com.denis.timetracking.exception.IncorrectInputException;
import com.denis.timetracking.mvc.controller.command.GeneralCommand;
import com.denis.timetracking.mvc.model.dao.UserDao;
import com.denis.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;
import com.denis.timetracking.mvc.model.dao.ActivityDao;
import com.denis.timetracking.mvc.model.entity.Activity;
import com.denis.timetracking.mvc.model.entity.User;
import com.denis.timetracking.mvc.model.service.ActivityService;
import com.denis.timetracking.mvc.model.service.ServiceManager;
import com.denis.timetracking.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 04.05.2018.
 * Executor Class that handles selection of activities according to user ID
 */
public class UsersActivities implements GeneralCommand {
    /**
     * @param request
     * @param response
     * @return url to forward
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        /*int userId = Integer.valueOf(request.getParameter("userId"));
        logger.info("UsersActivities user id = " + userId);
        String selection = request.getParameter("select");
        ActivityDao activityDao = (ActivityDao)manager.getDao("ACTIVITY");
        List<Activity> activities = null;
        try {
            activities = new ExecutorHelper().getActivitiesBySelection(request, activityDao);
            activityDao.closeConnection();
        } catch (SQLException e) {
            session.setAttribute("Error", "Bad request");
            selection = Constants.ERROR;
            logger.info(e);
        }
        logger.info("Users activities forward = " + selection);*/
        int userId = Integer.valueOf(request.getParameter("userId"));
        ActivityService aService = (ActivityService) new ServiceManager().getService("ACTIVITY");
        List<Activity> activities = aService.selectByUser(request);
        session.setAttribute("Activities", activities);
        session.setAttribute("SelectedUser", userId);
        return request.getParameter("select");
    }
}
