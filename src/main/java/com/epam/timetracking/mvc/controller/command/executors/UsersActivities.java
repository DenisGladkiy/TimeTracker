package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
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
        int userId = Integer.valueOf(request.getParameter("userId"));
        logger.debug("Users Activities userId = " + userId);
        String source = request.getParameter("source");
        ActivityDao dao = (ActivityDao)manager.getDao("ACTIVITY");
        List<Activity> activities = dao.getByUserId(userId);
        logger.debug("Users Activities activities = " + activities);
        dao.closeConnection();
        request.setAttribute("Activities", activities);
        return getUrl(source);
    }

    private String getUrl(String source){
        switch (source){
            case "users":
                return "/pages/activities.jsp";
            case "userIndex":
                return "/pages/userIndex.jsp";
            default:
                return null;
        }
    }
}
