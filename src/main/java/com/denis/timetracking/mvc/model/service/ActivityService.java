package com.denis.timetracking.mvc.model.service;

import com.denis.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;
import com.denis.timetracking.mvc.model.dao.ActivityDao;
import com.denis.timetracking.mvc.model.dao.DaoFactory;
import com.denis.timetracking.mvc.model.entity.Activity;
import com.denis.timetracking.mvc.model.entity.User;
import com.denis.timetracking.utils.Constants;
import com.denis.timetracking.utils.ControllerHelper;
import com.sun.deploy.net.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 29.05.2018.
 */
public class ActivityService implements AbstractService<Activity> {
    private DaoFactory daoFactory;

    public ActivityService(){
        daoFactory = new DaoFactory();
    }

    @Override
    public List<Activity> select(HttpServletRequest request) {
        ActivityDao dao = (ActivityDao) daoFactory.getDao("ACTIVITY");
        dao.openCurrentSession();
        HttpSession session = request.getSession();
        List<Activity> activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
        dao.closeCurrentSession();
        dao.closeConnection();
        session.setAttribute("Activities", activities);
        return activities;
    }

    public List<Activity> selectByUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        String strId = request.getParameter("userId");
        int userId = (strId != null) ? Integer.valueOf(strId) : user.getId();
        ActivityDao dao = (ActivityDao) daoFactory.getDao("ACTIVITY");
        dao.openCurrentSession();
        List<Activity> activities = dao.getByUserId(userId);
        dao.closeCurrentSession();
        dao.closeConnection();
        return activities;
    }

    @Override
    public String insert(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ControllerHelper helper = new ControllerHelper();
        Activity activity = helper.createActivityBean(request);
        if(activity == null){
            session.setAttribute("Error", "Incorrect input");
            return Constants.ERROR;
        }
        ActivityDao dao = (ActivityDao) daoFactory.getDao("ACTIVITY");
        dao.openCurrentSession();
        dao.insert(activity);
        List<Activity> activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
        session.setAttribute("Activities", activities);
        dao.closeCurrentSession();
        dao.closeConnection();
        return request.getParameter("select");
    }

    @Override
    public String update(HttpServletRequest request) {
        ActivityDao dao = (ActivityDao) daoFactory.getDao("ACTIVITY");
        ControllerHelper helper = new ControllerHelper();
        Activity activity = helper.createActivityBean(request);
        HttpSession session = request.getSession();
        dao.openCurrentSession();
        dao.update(activity);
        List<Activity> activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
        session.setAttribute("Activities", activities);
        dao.closeCurrentSession();
        dao.closeConnection();
        return request.getParameter("select");
    }

    @Override
    public String delete(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ControllerHelper helper = new ControllerHelper();
        Activity activity = helper.createActivityBean(request);
        ActivityDao dao = (ActivityDao) daoFactory.getDao("ACTIVITY");
        List<Activity> activities;
        dao.openCurrentSession();
        dao.delete(activity);
        activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
        dao.closeCurrentSession();
        dao.closeConnection();
        session.setAttribute("Activities", activities);
        return request.getParameter("select");
    }

    public String accept(HttpServletRequest request){
        String forward = Constants.ADDED_ACTIVITIES;
        String acceptedActivities = request.getParameter("accepted");
        if(acceptedActivities != null && acceptedActivities.length() > 0) {
            HttpSession session = request.getSession();
            ControllerHelper helper = new ControllerHelper();
            List<Activity> activities = helper.getListActivities(acceptedActivities);
            ActivityDao dao = (ActivityDao) daoFactory.getDao("ACTIVITY");
            dao.openCurrentSession();
            dao.acceptActivities(activities);
            activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
            session.setAttribute("Activities", activities);
            dao.closeCurrentSession();
            dao.closeConnection();
        }
        return forward;
    }
}
