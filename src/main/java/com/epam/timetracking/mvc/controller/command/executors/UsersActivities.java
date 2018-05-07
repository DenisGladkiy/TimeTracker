package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.dao.UserDao;
import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.mvc.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Denis on 04.05.2018.
 */
public class UsersActivities implements GeneralCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        logger.debug("Users Activities userId = " + userId);
        String source = request.getParameter("source");
        ActivityDao activityDao = (ActivityDao)manager.getDao("ACTIVITY");
        List<Activity> activities = activityDao.getByUserId(userId);
        logger.debug("Users Activities activities = " + activities);
        activityDao.closeConnection();
        UserDao userDao = (UserDao)manager.getDao("USER");
        User user = userDao.getById(userId);
        logger.debug("Users Activities user = " + user);
        request.setAttribute("Activities", activities);
        request.setAttribute("User", user);
        return getUrl(source);
    }

    private String getUrl(String source){
        switch (source){
            case "users":
                return "/pages/activitiesByUser.jsp";
            case "userIndex":
                return "/pages/userIndex.jsp";
            default:
                return null;
        }
    }
}
