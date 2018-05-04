package com.epam.timetracking.mvc.controller.command;

import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.entity.Activity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 03.05.2018.
 */
public class ActivitySelect implements GeneralCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String selection = request.getParameter("select");
        logger.debug("Selection selection = " + selection);
        String url = getUrl(selection);
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        List<Activity> allActivities = dao.getAll();
        dao.closeConnection();
        final ArrayList<Activity> selectedActivities = new ArrayList<>();
        Condition condition = getCondition(selection);
        allActivities.forEach(activity -> {
            if (condition.isTrue(activity)) {
                selectedActivities.add(activity);
            }});
        logger.debug("List of selected activities = " + selectedActivities);
        request.setAttribute("Activities", selectedActivities);
        return url;
    }

    private boolean isActual(Activity activity){
        return !activity.isAddRequest() && !activity.isRemoveRequest() && !activity.isCompleted();
    }

    private boolean isAddRequest(Activity activity){
        return activity.isAddRequest();
    }

    private boolean isRemoveRequest(Activity activity){
        return activity.isRemoveRequest();
    }

    private boolean isCompleted(Activity activity){
        return activity.isCompleted();
    }

    private Condition getCondition(String selection){
        switch (selection){
            case "selectActual":
                return activity -> isActual(activity);
            case "selectAdded":
                return activity -> isAddRequest(activity);
            case "selectToRemove":
                return activity -> isRemoveRequest(activity);
            case "selectCompleted":
                return activity -> isCompleted(activity);
            default:
                return activity -> false;
        }
    }

    private String getUrl(String selection){
        switch (selection){
            case "selectActual":
                return "/pages/activities.jsp";
            case "selectAdded":
                return "/pages/activities.jsp";
            case "selectToRemove":
                return "/pages/activities.jsp";
            case "selectCompleted":
                return "/pages/completedActivities.jsp";
            default:
                return "/pages/activities.jsp";
        }
    }

    interface Condition{
        boolean isTrue(Activity activity);
    }
}
