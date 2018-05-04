package com.epam.timetracking.mvc.controller.command;

import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.entity.Activity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Denis on 04.05.2018.
 */
public class UsersActivities implements GeneralCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.valueOf(request.getParameter("id"));
        ActivityDao dao = (ActivityDao)manager.getDao("ACTIVITY");
        List<Activity> activities = dao.getByUserId(userId);
        dao.closeConnection();
        request.setAttribute("Activities", activities);
        return "/pages/activities.jsp";
    }
}
