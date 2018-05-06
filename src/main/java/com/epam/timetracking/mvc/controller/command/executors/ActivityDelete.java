package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.model.dao.AbstractDao;
import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Denis on 03.05.2018.
 */
public class ActivityDelete implements GeneralCommand {
    String url;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String selection = request.getParameter("select");
        ControllerHelper helper = new ControllerHelper();
        Activity activity = helper.createActivityBean(request);
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        dao.delete(activity);
        List<Activity> activities = getActivities(dao, selection);
        dao.closeConnection();
        request.setAttribute("Activities", activities);
        return url;
    }

    private List<Activity> getActivities(ActivityDao dao, String selection){
        switch (selection){
            case "selectActual":
                url = "/pages/activities.jsp";
                return dao.getActual();
            case "selectAdded":
                url = "/pages/addedActivities.jsp";
                return dao.getAdded();
            case "selectRemoved":
                url = "/pages/removedActivities.jsp";
                return dao.getRemoved();
            case "selectCompleted":
                url = "/pages/completedActivities.jsp";
                return dao.getCompleted();
        }
        return null;
    }
}
