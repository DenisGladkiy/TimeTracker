package com.denis.timetracking.utils;

import com.denis.timetracking.exception.IncorrectInputException;
import com.denis.timetracking.mvc.model.dao.DaoFactory;
import com.denis.timetracking.mvc.model.dao.UserDao;
import com.denis.timetracking.mvc.model.entity.User;
import com.denis.timetracking.mvc.model.service.ServiceFactory;
import com.denis.timetracking.mvc.model.service.UserService;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Denis on 13.05.2018.
 * Class describes custom tag
 * that converts user id to his/her name
 */
public class UserNameTag extends TagSupport {
    private int userId;
    private UserService uService;

    {
       uService = (UserService) new ServiceFactory().getService("USER");
    }

    /**
     * Set user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId){
        this.userId = userId;
    }

    @Override
    public int doStartTag(){
        if(userId > 0) {
            try {
                User user = uService.getById(userId);
                pageContext.getOut().write(user.getFirstName() + " " + user.getLastName());
            } catch (IOException | IncorrectInputException e) {
                e.printStackTrace();
            }
        }
        return SKIP_BODY;
    }
}
