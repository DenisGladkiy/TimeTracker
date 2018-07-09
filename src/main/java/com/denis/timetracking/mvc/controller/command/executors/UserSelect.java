package com.denis.timetracking.mvc.controller.command.executors;

import com.denis.timetracking.mvc.controller.command.GeneralCommand;
import com.denis.timetracking.mvc.model.entity.User;
import com.denis.timetracking.mvc.model.service.ServiceFactory;
import com.denis.timetracking.mvc.model.service.UserService;
import com.denis.timetracking.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        UserService uService = (UserService) new ServiceFactory().getService("USER");
        List<User> users = uService.select(request);
        HttpSession session = request.getSession();
        if(users == null) {
            session.setAttribute("Error", "Bad request");
            return Constants.ERROR;
        }
        session.setAttribute("Users", users);
        logger.info("Select Users = " + users);
        return Constants.USERS;
    }
}
