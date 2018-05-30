package com.denis.timetracking.mvc.controller.command.executors;

import com.denis.timetracking.exception.IncorrectInputException;
import com.denis.timetracking.mvc.controller.command.GeneralCommand;
import com.denis.timetracking.mvc.model.dao.UserDao;
import com.denis.timetracking.mvc.model.dao.ActivityDao;
import com.denis.timetracking.mvc.model.entity.User;
import com.denis.timetracking.mvc.model.entity.UserRoleEnum;
import com.denis.timetracking.mvc.model.service.ActivityService;
import com.denis.timetracking.mvc.model.service.ServiceManager;
import com.denis.timetracking.mvc.model.service.UserService;
import com.denis.timetracking.utils.Constants;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 09.05.2018.
 * Executor Class that handles users authorization
 */
public class Login implements GeneralCommand {

    private UserService userService;
    /**
     *
     * @param request
     * @param response
     * @return url to forward
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        userService = (UserService) new ServiceManager().getService("USER");
        String forward = userService.login(request, response);
        //HttpSession session = request.getSession(false);
        setSessionAttributes(request, forward);
        /*String login = request.getParameter("login");
        String password = request.getParameter("password");
        UserDao userDao = (UserDao)manager.getDao("USER");
        HttpSession session = request.getSession();
        User user = userDao.getByLogin(login);
        logger.info("Login user = " + user);
        userDao.closeConnection();
        if(isValid(user, password)){
            logger.info("Password is valid");
            forward = checkUserRole(user, session);
        }else{
            session.setAttribute("Error", "Incorrect login/password");
            forward = Constants.ERROR;
        }*/
        return forward;
    }

    /*private String checkUserRole(User user, HttpSession session){
        String forward = Constants.INDEX;
        if(user.getRole().equals(UserRoleEnum.USER)){
            logger.info("USER");
            session.setAttribute("User", user);
            ActivityDao activityDao = (ActivityDao) manager.getDao("ACTIVITY");
            try {
                session.setAttribute("Activities", activityDao.getByUserId(user.getId()));
            } catch (SQLException e) {
                logger.info(e);
                return handleSqlException(session);
            }
            activityDao.closeConnection();
            forward = Constants.USER_INDEX;
        }else if(user.getRole().equals(UserRoleEnum.ADMIN)) {
            logger.info("ADMIN");
            session.setAttribute("User", user);
            UserDao userDao = (UserDao) manager.getDao("USER");
            try {
                session.setAttribute("Users", userDao.getAll());
            } catch (SQLException e) {
                logger.info(e);
                return handleSqlException(session);
            }
            logger.info("ADMIN user " + user);
            forward = Constants.ADMIN_INDEX;
        }
        return forward;
    }*/



//    private String handleSqlException(HttpSession session){
//        session.setAttribute("Error", "Bad request");
//        return Constants.ERROR;
//    }
//
//    private boolean isValid(User user, String password){
//        if(user != null) {
//            return BCrypt.checkpw(password, user.getPassword());
//        }else {
//            return false;
//        }
//    }

    private void setSessionAttributes(HttpServletRequest request, String forward){
        HttpSession session = request.getSession(false);
        switch (forward){
            case Constants.USER_INDEX:
                ActivityService aService = (ActivityService) new ServiceManager().getService("ACTIVITY");
                session.setAttribute("Activities", aService.selectByUser(request));
                break;
            case Constants.ADMIN_INDEX:
                session.setAttribute("Users", userService.selectAllUsers());
        }
    }
}
