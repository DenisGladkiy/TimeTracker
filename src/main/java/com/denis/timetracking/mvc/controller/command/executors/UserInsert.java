package com.denis.timetracking.mvc.controller.command.executors;

import com.denis.timetracking.exception.IncorrectInputException;
import com.denis.timetracking.mvc.controller.command.GeneralCommand;
import com.denis.timetracking.mvc.model.dao.AbstractDao;
import com.denis.timetracking.utils.ControllerHelper;
import com.denis.timetracking.mvc.model.entity.User;
import com.denis.timetracking.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Created by Denis on 04.05.2018.
 * Executor Class that handles creation of a user
 */
public class UserInsert implements GeneralCommand {
    /**
     * @param request
     * @param response
     * @return url to forward
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = Constants.ADMIN_INDEX;
        ControllerHelper helper = new ControllerHelper();
        User user = helper.createUserBean(request);
        logger.info("User to insert = " + user);
        HttpSession session = request.getSession();
        AbstractDao dao = manager.getDao("USER");
        try {
            dao.insert(user);
        } catch (SQLException e) {
            session.setAttribute("Error", "Bad request");
            forward = Constants.ERROR;
            logger.info(e);
        } catch (IncorrectInputException e) {
            session.setAttribute("Error", "Incorrect input");
            forward = Constants.ERROR;
            logger.info(e);
        }
        dao.closeConnection();
        logger.info("User insert forward = " + forward);
        return forward;
    }
}
