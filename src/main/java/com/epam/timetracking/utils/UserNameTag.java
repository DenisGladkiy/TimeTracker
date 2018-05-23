package com.epam.timetracking.utils;

import com.epam.timetracking.exception.IncorrectInputException;
import com.epam.timetracking.mvc.model.dao.DaoManager;
import com.epam.timetracking.mvc.model.dao.UserDao;
import com.epam.timetracking.mvc.model.entity.User;
import org.apache.log4j.Logger;

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
    private DaoManager manager;

    {
        manager = DaoManager.getInstance();
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
            UserDao dao = (UserDao) manager.getDao("USER");
            User user;
            try {
                user = dao.getById(userId);
                dao.closeConnection();
                pageContext.getOut().write(user.getFirstName() + " " + user.getLastName());
            } catch (IOException | SQLException | IncorrectInputException e) {
                e.printStackTrace();
            }
        }
        return SKIP_BODY;
    }
}
