package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.model.dao.UserDao;
import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.utils.Constants;
import com.epam.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Denis on 04.05.2018.
 * Executor Class that handles removal of a user
 */
public class UserDelete implements GeneralCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ControllerHelper helper = new ControllerHelper();
        User user = helper.createUserBean(request);
        UserDao dao = (UserDao)manager.getDao("USER");
        dao.delete(user);
        List<User> users = dao.getAll();
        request.getSession().setAttribute("Users", users);
        dao.closeConnection();
        return Constants.USERS;
    }
}
