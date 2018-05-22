package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.model.dao.UserDao;
import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.utils.Constants;
import com.epam.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 04.05.2018.
 * Executor Class that handles removal of a user
 */
public class UserDelete implements GeneralCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = Constants.USERS;
        ControllerHelper helper = new ControllerHelper();
        User user = helper.createUserBean(request);
        UserDao dao = (UserDao)manager.getDao("USER");
        HttpSession session = request.getSession();
        List<User> users = null;
        try {
            dao.delete(user);
            users = dao.getAll();
        } catch (SQLException e) {
            session.setAttribute("Error", "Bad request");
            forward = Constants.ERROR;
            logger.debug(e);
        }
        session.setAttribute("Users", users);
        dao.closeConnection();
        logger.info("User delete forward = " + forward);
        return forward;
    }
}
