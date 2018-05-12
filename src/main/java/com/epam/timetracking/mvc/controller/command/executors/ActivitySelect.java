package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;
import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.entity.Activity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Denis on 03.05.2018.
 */
public class ActivitySelect implements GeneralCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String selection = request.getParameter("select");
        logger.debug("Selection selection = " + selection);
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        List<Activity> activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
        logger.debug("List of selected activities = " + activities);
        dao.closeConnection();
        request.getSession().setAttribute("Activities", activities);
        if(selection.startsWith("/")){
            return selection;
        }else {
            return "/pages/" + selection;
        }
    }
}
