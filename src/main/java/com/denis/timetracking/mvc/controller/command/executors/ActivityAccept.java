package com.denis.timetracking.mvc.controller.command.executors;

import com.denis.timetracking.mvc.controller.command.GeneralCommand;
import com.denis.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;
import com.denis.timetracking.mvc.model.dao.ActivityDao;
import com.denis.timetracking.mvc.model.entity.Activity;
import com.denis.timetracking.mvc.model.service.ActivityService;
import com.denis.timetracking.mvc.model.service.ServiceFactory;
import com.denis.timetracking.utils.Constants;
import com.denis.timetracking.utils.ControllerHelper;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 12.05.2018.
 * Executor Class that handles batch acceptance
 * of requests to add new activity from the users
 */
public class ActivityAccept implements GeneralCommand {
    /**
     *
     * @param request
     * @param response
     * @return url to forward
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ServiceFactory factory = new ServiceFactory();
        ActivityService service = (ActivityService) factory.getService("ACTIVITY");
        return service.accept(request);
    }
}
