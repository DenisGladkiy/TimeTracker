package com.denis.timetracking.mvc.controller.command.executors;

import com.denis.timetracking.mvc.controller.command.GeneralCommand;
import com.denis.timetracking.mvc.model.dao.ActivityDao;
import com.denis.timetracking.mvc.model.entity.Activity;
import com.denis.timetracking.utils.Constants;
import com.denis.timetracking.utils.ControllerHelper;
import com.denis.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 03.05.2018.
 * Executor Class that handles activity removal by ADMIN
 */
public class ActivityDelete implements GeneralCommand {
    /**
     *
     * @param request
     * @param response
     * @return url to forward
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String selection = request.getParameter("select");
        HttpSession session = request.getSession();
        ControllerHelper helper = new ControllerHelper();
        Activity activity = helper.createActivityBean(request);
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        List<Activity> activities = null;
        dao.delete(activity);
        activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
        dao.closeConnection();
        session.setAttribute("Activities", activities);
        logger.info("Activity delete forward = " + selection);
        return selection;
    }
}
