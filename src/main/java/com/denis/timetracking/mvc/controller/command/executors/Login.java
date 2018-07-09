package com.denis.timetracking.mvc.controller.command.executors;

import com.denis.timetracking.mvc.controller.command.GeneralCommand;
import com.denis.timetracking.mvc.model.service.ActivityService;
import com.denis.timetracking.mvc.model.service.ServiceFactory;
import com.denis.timetracking.mvc.model.service.UserService;
import com.denis.timetracking.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Created by Denis on 09.05.2018.
 * Executor Class that handles users authorization
 */
public class Login implements GeneralCommand {

    private UserService uService;
    /**
     *
     * @param request
     * @param response
     * @return url to forward
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        uService = (UserService) new ServiceFactory().getService("USER");
        String forward = uService.login(request, response);
        setSessionAttributes(request, forward);
        return forward;
    }

    private void setSessionAttributes(HttpServletRequest request, String forward){
        HttpSession session = request.getSession();
        switch (forward){
            case Constants.USER_INDEX:
                ActivityService aService = (ActivityService) new ServiceFactory().getService("ACTIVITY");
                session.setAttribute("Activities", aService.selectByUser(request));
                break;
            case Constants.ADMIN_INDEX:
                session.setAttribute("Users", uService.select(request));
        }
    }
}
