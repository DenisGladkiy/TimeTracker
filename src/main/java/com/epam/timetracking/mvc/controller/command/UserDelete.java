package com.epam.timetracking.mvc.controller.command;

import com.epam.timetracking.mvc.model.dao.AbstractDao;
import com.epam.timetracking.mvc.model.dao.UserDao;
import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Denis on 04.05.2018.
 */
public class UserDelete implements GeneralCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ControllerHelper helper = new ControllerHelper();
        User user = helper.createUserBean(request);
        UserDao dao = (UserDao)manager.getDao("USER");
        dao.delete(user);
        List<User> users = dao.getAll();
        request.setAttribute("Users", users);
        dao.closeConnection();
        return "/pages/users.jsp";
    }
}
