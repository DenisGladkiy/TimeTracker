package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;
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
        String selection = request.getParameter("select");
        ActivityDao activityDao = (ActivityDao)manager.getDao("ACTIVITY");
        List<Activity> activities = new ExecutorHelper().getActivitiesBySelection(request, activityDao);
        activityDao.closeConnection();
        UserDao userDao = (UserDao)manager.getDao("USER");
        User user = userDao.getById(userId);
        userDao.closeConnection();
        request.getSession().setAttribute("Activities", activities);
        request.getSession().setAttribute("SelectedUser", user);
        return selection;
    }
}
