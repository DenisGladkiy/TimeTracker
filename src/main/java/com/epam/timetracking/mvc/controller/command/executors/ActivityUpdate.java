package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Denis on 03.05.2018.
 */
public class ActivityUpdate implements GeneralCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String url = "/pages/activities.jsp";
        logger.debug("id = " + request.getParameter("id"));
        logger.debug("name = " + request.getParameter("name"));
        logger.debug("description = " + request.getParameter("description"));
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        ControllerHelper helper = new ControllerHelper();
        Activity activity = helper.createActivityBean(request);
        dao.update(activity);

        String selection = request.getParameter("select");
        logger.debug("Update selection = " + selection);
        switch (selection){
            case "selectCompleted":
                request.setAttribute("Activities", dao.getCompleted());
                url = "/pages/completedActivities.jsp";
                break;
            case "selectActual":
                request.setAttribute("Activities", dao.getActual());
                url = "/pages/activities.jsp";
                break;
            case "selectAdded":
                request.setAttribute("Activities", dao.getAdded());
                url = "/pages/addedActivities.jsp";
                break;
            case "selectRemoved":
                request.setAttribute("Activities", dao.getRemoved());
                url = "/pages/removedActivities.jsp";
                break;
            case "selectByUser":
                return new UsersActivities().execute(request, response);
        }
        dao.closeConnection();
        return url;
    }
}
