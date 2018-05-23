package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.exception.IncorrectInputException;
import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;
import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.dao.UserDao;
import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.utils.Constants;
import com.epam.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 03.05.2018.
 * Executor Class that handles creation of new activity
 */
public class ActivityInsert implements GeneralCommand {
    /**
     *
     * @param request
     * @param response
     * @return url to forward
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String selection = request.getParameter("select");
        ControllerHelper helper = new ControllerHelper();
        Activity activity = helper.createActivityBean(request);
        logger.debug("Activity to insert = " + activity);
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        HttpSession session = request.getSession();
        List<Activity> activities = null;
        try {
            dao.insert(isForeignKeyValid(activity)? activity : null);
            activities = new ExecutorHelper().getActivitiesBySelection(request, dao);
        } catch (SQLException e) {
            session.setAttribute("Error", "Bad request");
            selection = Constants.ERROR;
            logger.debug(e);
        } catch (IncorrectInputException e) {
            session.setAttribute("Error", "Incorrect input");
            selection = Constants.ERROR;
            logger.debug(e);
        }
        dao.closeConnection();
        session.setAttribute("Activities", activities);
        logger.info("Activity insert forward = " + selection);
        return selection;
    }

    private boolean isForeignKeyValid(Activity activity) throws SQLException {
        int userId = activity.getUserId();
        if(userId > 0) {
            UserDao dao = (UserDao) manager.getDao("USER");
            boolean exist = dao.doesExist(userId);
            dao.closeConnection();
            return exist;
        }else {
            return true;
        }
    }
}
