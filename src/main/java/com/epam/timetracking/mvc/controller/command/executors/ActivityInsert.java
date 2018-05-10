package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;
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
public class ActivityInsert implements GeneralCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String selection = request.getParameter("select");
        ControllerHelper helper = new ControllerHelper();
        Activity activity = helper.createActivityBean(request);
        logger.debug("Activity to insert = " + activity);
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        dao.insert(activity);
        List<Activity> activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
        dao.closeConnection();
        request.getSession().setAttribute("Activities", activities);
        if(selection.startsWith("/")){
            return selection;
        }else {
            return "/pages/" + selection;
        }
    }
}
