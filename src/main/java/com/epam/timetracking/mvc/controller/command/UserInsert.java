package com.epam.timetracking.mvc.controller.command;

import com.epam.timetracking.mvc.model.dao.AbstractDao;
import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.utils.ControllerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Denis on 04.05.2018.
 */
public class UserInsert implements GeneralCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ControllerHelper helper = new ControllerHelper();
        User user = helper.createUserBean(request);
        logger.debug("User to insert = " + user);
        AbstractDao dao = manager.getDao("USER");
        dao.insert(user);
        dao.closeConnection();
        return "/index.jsp";
    }
}
