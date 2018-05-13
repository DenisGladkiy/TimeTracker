package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.model.dao.UserDao;
import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Denis on 04.05.2018.
 * Executor Class that handles update of user information
 * (currently not in use)
 */
public class UserUpdate implements GeneralCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        UserDao dao = (UserDao) manager.getDao("USER");
        ControllerHelper helper = new ControllerHelper();
        User user = helper.createUserBean(request);
        dao.update(user);
        dao.closeConnection();
        return "/pages/users.jsp";
    }
}
