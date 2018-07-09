package com.denis.timetracking.mvc.controller.command.executors;

import com.denis.timetracking.mvc.controller.command.GeneralCommand;
import com.denis.timetracking.mvc.model.service.ServiceFactory;
import com.denis.timetracking.mvc.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Denis on 04.05.2018.
 * Executor Class that handles removal of a user
 */
public class UserDelete implements GeneralCommand {
    /**
     * @param request
     * @param response
     * @return url to forward
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        UserService uService = (UserService) new ServiceFactory().getService("USER");
        return uService.delete(request);
    }
}
