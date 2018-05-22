package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.exception.IncorrectInputException;
import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.dao.UserDao;
import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.mvc.model.entity.UserRoleEnum;
import com.epam.timetracking.utils.Constants;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Created by Denis on 09.05.2018.
 * Executor Class that handles users authorization
 */
public class Login implements GeneralCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = Constants.INDEX;
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        UserDao userDao = (UserDao)manager.getDao("USER");
        HttpSession session = request.getSession();
        User user = null;
        try {
            user = userDao.getByLogin(login);
        } catch (SQLException e) {
            session.setAttribute("Error", "Bad request");
            forward = Constants.ERROR;
            logger.debug(e);
        } catch (IncorrectInputException e) {
            session.setAttribute("Error", "Incorrect login/password");
            forward = Constants.ERROR;
            logger.debug(e);
        }
        logger.info("Login user = " + user);
        userDao.closeConnection();
        if(isValid(user, password)){
            logger.info("Password is valid");
            if(user.getRole().equals(UserRoleEnum.USER)){
                logger.debug("USER");
                session.setAttribute("User", user);
                ActivityDao activityDao = (ActivityDao) manager.getDao("ACTIVITY");
                try {
                    session.setAttribute("Activities", activityDao.getByUserId(user.getId()));
                } catch (SQLException e) {
                    session.setAttribute("Error", "Bad request");
                    forward = Constants.ERROR;
                    logger.debug(e);
                    return forward;
                }
                activityDao.closeConnection();
                forward = Constants.USER_INDEX;
            }else if(user.getRole().equals(UserRoleEnum.ADMIN)) {
                logger.debug("ADMIN");
                request.getSession().setAttribute("User", user);
                logger.debug("ADMIN user " + user);
                forward = Constants.ADMIN_INDEX;
            }
        }
        return forward;
    }

    private boolean isValid(User user, String password){
        if(user != null) {
            return BCrypt.checkpw(password, user.getPassword());
        }else {
            return false;
        }
    }
}
