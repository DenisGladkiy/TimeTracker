package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.dao.UserDao;
import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.mvc.model.entity.UserRoleEnum;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Denis on 09.05.2018.
 */
public class Login implements GeneralCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        UserDao userDao = (UserDao)manager.getDao("USER");
        User user = userDao.getByLogin(login);
        logger.debug("Login user = " + user);
        userDao.closeConnection();
        if(isValid(user, password)){
            logger.debug("Password is valid");
            if(user.getRole().equals(UserRoleEnum.USER)){
                request.getSession().setAttribute("User", user);
                ActivityDao activityDao = (ActivityDao) manager.getDao("ACTIVITY");
                request.getSession().setAttribute("Activities", activityDao.getByUserId(user.getId()));
                return "/userPages/userIndex.jsp";
            }else if(user.getRole().equals(UserRoleEnum.ADMIN)) {
                request.getSession().setAttribute("User", user);
                return "/pages/adminIndex.jsp";
            }
        }
        return "/index.jsp";
    }

    private boolean isValid(User user, String password){
        if(user != null) {
            return BCrypt.checkpw(password, user.getPassword());
        }else {
            return false;
        }
    }
}
