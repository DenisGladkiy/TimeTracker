package com.denis.timetracking.mvc.model.service;

import com.denis.timetracking.mvc.model.dao.DaoFactory;
import com.denis.timetracking.mvc.model.dao.UserDao;
import com.denis.timetracking.mvc.model.entity.User;
import com.denis.timetracking.mvc.model.entity.UserRoleEnum;
import com.denis.timetracking.utils.Constants;
import com.denis.timetracking.utils.ControllerHelper;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Denis on 29.05.2018.
 */
public class UserService implements AbstractService<User> {
    private DaoFactory manager;

    public UserService(){
        manager = new DaoFactory();
    }

    @Override
    public List<User> select(HttpServletRequest request) {
        UserDao dao = (UserDao) manager.getDao("USER");
        List<User> users = dao.getAll();
        dao.closeConnection();
        return users;
    }

    @Override
    public String insert(HttpServletRequest request) {
        ControllerHelper helper = new ControllerHelper();
        if(helper.isCreateUserRequestValid(request)) {
            User user = helper.createUserBean(request);
            UserDao userDao = (UserDao) manager.getDao("USER");
            userDao.insert(user);
            userDao.closeConnection();
            return Constants.ADMIN_INDEX;
        }else {
            HttpSession session = request.getSession();
            session.setAttribute("Error", "Incorrect input");
            return Constants.ERROR;
        }
    }

    @Override
    public String update(HttpServletRequest request) {
        return null;
    }

    @Override
    public String delete(HttpServletRequest request) {
        ControllerHelper helper = new ControllerHelper();
        User user = helper.createUserBean(request);
        UserDao dao = (UserDao)manager.getDao("USER");
        dao.delete(user);
        HttpSession session = request.getSession();
        List<User> users = dao.getAll();
        session.setAttribute("Users", users);
        dao.closeConnection();
        return Constants.USERS;
    }

    public String login(HttpServletRequest request, HttpServletResponse response){
        UserDao userDao = (UserDao) manager.getDao("USER");
        String forward;
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        User user = userDao.getByLogin(login);
        userDao.closeConnection();
        if(isValidPassword(user, password)){
            request.getSession().setAttribute("User", user);
            forward = checkUserRole(user);
        }else{
            request.getSession().setAttribute("Error", "Incorrect login/password");
            forward = Constants.ERROR;
        }
        return forward;
    }

    private boolean isValidPassword(User user, String password){
        if(user != null) {
            return BCrypt.checkpw(password, user.getPassword());
        }else {
            return false;
        }
    }

    private String checkUserRole(User user){
        String forward = Constants.INDEX;
        if(user.getRole().equals(UserRoleEnum.USER)){
            forward = Constants.USER_INDEX;
        }else if(user.getRole().equals(UserRoleEnum.ADMIN)) {
            forward = Constants.ADMIN_INDEX;
        }
        return forward;
    }
}
