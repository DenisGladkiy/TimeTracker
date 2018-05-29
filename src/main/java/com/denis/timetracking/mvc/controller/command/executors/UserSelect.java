package com.denis.timetracking.mvc.controller.command.executors;

import com.denis.timetracking.mvc.controller.command.GeneralCommand;
import com.denis.timetracking.mvc.model.dao.UserDao;
import com.denis.timetracking.mvc.model.entity.User;
import com.denis.timetracking.utils.Constants;

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
    /**
     * @param request
     * @param response
     * @return url to forward
     */
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
            logger.info(e);
        }
        dao.closeConnection();
        session.setAttribute("Users", users);
        logger.info("Users = " + users);
        logger.info("UserSelection forward = " + forward);
        return forward;
    }
}
