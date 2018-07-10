package com.denis.timetracking.mvc.controller.command.executors;

import com.denis.timetracking.mvc.controller.command.GeneralCommand;
import com.denis.timetracking.mvc.model.entity.Activity;
import com.denis.timetracking.mvc.model.service.ActivityService;
import com.denis.timetracking.mvc.model.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        int userId = Integer.valueOf(request.getParameter("userId"));
        ActivityService aService = (ActivityService) new ServiceFactory().getService("ACTIVITY");
        List<Activity> activities = aService.selectByUser(request);
        session.setAttribute("Activities", activities);
        session.setAttribute("SelectedUser", userId);
        return request.getParameter("select");
    }
}
