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
        ActivityService aService = (ActivityService) new ServiceFactory().getService("ACTIVITY");
        List<Activity> activities = aService.select(request);
        HttpSession session = request.getSession();
        session.setAttribute("Activities", activities);
        return request.getParameter("select");
    }
}
