package com.epam.timetracking.mvc.controller.command.executors;

import com.epam.timetracking.exception.IncorrectInputException;
import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.model.dao.AbstractDao;
import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.utils.Constants;
import com.epam.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Created by Denis on 04.05.2018.
 * Executor Class that handles creation of a user
 */
public class UserInsert implements GeneralCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = Constants.ADMIN_INDEX;
        ControllerHelper helper = new ControllerHelper();
        User user = helper.createUserBean(request);
        logger.debug("User to insert = " + user);
        HttpSession session = request.getSession();
        AbstractDao dao = manager.getDao("USER");
        try {
            dao.insert(user);
        } catch (SQLException e) {
            session.setAttribute("Error", "Bad request");
            forward = Constants.ERROR;
            logger.debug(e);
        } catch (IncorrectInputException e) {
            session.setAttribute("Error", "Incorrect input");
            forward = Constants.ERROR;
            logger.debug(e);
        }
        dao.closeConnection();
        return forward;
    }
}
