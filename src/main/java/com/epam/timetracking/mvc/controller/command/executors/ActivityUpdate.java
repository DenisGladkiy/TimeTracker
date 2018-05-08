package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;
import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.dao.UserDao;
import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Denis on 03.05.2018.
 */
public class ActivityUpdate implements GeneralCommand {
    //private String url;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String selection = request.getParameter("select");
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        ControllerHelper helper = new ControllerHelper();
        Activity activity = helper.createActivityBean(request);
        dao.update(activity);
        List<Activity> activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
        logger.debug("Update selection = " + selection);
        request.setAttribute("Activities", activities);
        dao.closeConnection();
        return "/pages/" + selection;
    }

//    private List<Activity> getActivities(ActivityDao dao, HttpServletRequest request) {
//        String selection = request.getParameter("select");
//        int userId;
//        switch (selection) {
//            case "selectCompleted":
//                url = "/pages/completedActivities.jsp";
//                return dao.getCompleted();
//            case "selectActual":
//                url = "/pages/activities.jsp";
//                return dao.getActual();
//            case "selectAdded":
//                url = "/pages/addedActivities.jsp";
//                return dao.getAdded();
//            case "selectRemoved":
//                url = "/pages/removedActivities.jsp";
//                return dao.getRemoved();
//            case "userIndex":
//                url = "/pages/userIndex.jsp";
//                userId = Integer.valueOf(request.getParameter("userId"));
//                return dao.getByUserId(userId);
//            case "activitiesByUser":
//                url = "/pages/activitiesByUser.jsp";
//                userId = Integer.valueOf(request.getParameter("userId"));
//                return dao.getByUserId(userId);
//        }
//        return null;
//    }
}
