package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;
import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Denis on 03.05.2018.
 * Executor Class that handles activity removal by ADMIN
 */
public class ActivityDelete implements GeneralCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String selection = request.getParameter("select");
        ControllerHelper helper = new ControllerHelper();
        Activity activity = helper.createActivityBean(request);
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        dao.delete(activity);
        List<Activity> activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
        dao.closeConnection();
        request.getSession().setAttribute("Activities", activities);
        return selection;
    }
}
