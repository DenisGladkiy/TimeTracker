package com.denis.timetracking.mvc.model.service;

import com.denis.timetracking.exception.IncorrectInputException;
import com.denis.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;
import com.denis.timetracking.mvc.model.dao.ActivityDao;
import com.denis.timetracking.mvc.model.dao.DaoManager;
import com.denis.timetracking.mvc.model.entity.Activity;
import com.denis.timetracking.mvc.model.entity.User;
import com.denis.timetracking.utils.Constants;
import com.denis.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Created by Denis on 29.05.2018.
 */
public class ActivityService implements AbstractService<Activity> {
    private DaoManager daoManager;

    public ActivityService(){
        daoManager = new DaoManager();
    }
    @Override
    public List<Activity> select(HttpServletRequest request) {
        return null;
    }

    public List<Activity> selectByUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        String strId = request.getParameter("userId");
        int userId = (strId != null) ? Integer.valueOf(strId) : user.getId();
        ActivityDao dao = (ActivityDao) daoManager.getDao("ACTIVITY");
        List<Activity> activities = dao.getByUserId(userId);
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
        ActivityDao dao = (ActivityDao) daoManager.getDao("ACTIVITY");
        dao.insert(activity);
        List<Activity> activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
        session.setAttribute("Activities", activities);
        dao.closeConnection();
        return request.getParameter("select");
    }

    @Override
    public void update(HttpServletRequest request) {

    }

    @Override
    public String delete(HttpServletRequest request) {
        return "";
    }
}
