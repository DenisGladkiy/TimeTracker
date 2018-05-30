package com.denis.timetracking.mvc.model.service;

import com.denis.timetracking.mvc.model.dao.ActivityDao;
import com.denis.timetracking.mvc.model.dao.DaoManager;
import com.denis.timetracking.mvc.model.entity.Activity;
import com.denis.timetracking.mvc.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Denis on 29.05.2018.
 */
public class ActivityService implements AbstractService<Activity> {
    private DaoManager daoManager;

    public ActivityService(){
        daoManager = new DaoManager();
    }
    @Override
    public List<Activity> select(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    public List<Activity> selectByUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("User");
        ActivityDao dao = (ActivityDao) daoManager.getDao("ACTIVITY");
        return dao.getByUserId(user.getId());
    }

    @Override
    public void insert(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void update(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void delete(HttpServletRequest request, HttpServletResponse response) {

    }
}
