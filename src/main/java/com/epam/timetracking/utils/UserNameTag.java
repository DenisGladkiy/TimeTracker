package com.epam.timetracking.utils;

import com.epam.timetracking.mvc.model.dao.DaoManager;
import com.epam.timetracking.mvc.model.dao.UserDao;
import com.epam.timetracking.mvc.model.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by Denis on 13.05.2018.
 */
public class UserNameTag extends TagSupport {
    private static Logger logger = Logger.getLogger(UserNameTag.class);
    private int userId;
    private DaoManager manager;

    {
        manager = DaoManager.getInstance();
    }

    public void setUserId(int userId){
        logger.debug("Tag user Id = " + userId);
        this.userId = userId;
    }

    @Override
    public int doStartTag(){
        if(userId > 0) {
            UserDao dao = (UserDao) manager.getDao("USER");
            User user = dao.getById(userId);
            logger.debug("Tag user = " + user);
            dao.closeConnection();
            try {
                pageContext.getOut().write(user.getFirstName() + " " + user.getLastName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return SKIP_BODY;
    }
}
