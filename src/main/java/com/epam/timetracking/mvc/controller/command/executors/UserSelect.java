package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.model.dao.UserDao;
import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 04.05.2018.
 * Executor Class that handles selection of all users
 */
public class UserSelect implements GeneralCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = Constants.USERS;
        UserDao dao = (UserDao) manager.getDao("USER");
        HttpSession session = request.getSession();
        List<User> users = null;
        try {
            users = dao.getAll();
        } catch (SQLException e) {
            session.setAttribute("Error", "Bad request");
            forward = Constants.ERROR;
            logger.debug(e);
        }
        dao.closeConnection();
        session.setAttribute("Users", users);
        logger.debug("Users = " + users);
        logger.info("UserSelection forward = " + forward);
        return forward;
    }
}
