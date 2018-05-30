package com.denis.timetracking.mvc.model.service;

import com.denis.timetracking.mvc.model.dao.DaoManager;
import com.denis.timetracking.mvc.model.dao.UserDao;
import com.denis.timetracking.mvc.model.entity.User;
import com.denis.timetracking.mvc.model.entity.UserRoleEnum;
import com.denis.timetracking.utils.Constants;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Denis on 29.05.2018.
 */
public class UserService implements AbstractService<User> {
    //private DaoManager manager;
    private UserDao userDao;

    public UserService(){
        DaoManager manager = new DaoManager();
        userDao = (UserDao)manager.getDao("USER");
    }

    @Override
    public List<User> select(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public void insert(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void update(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void delete(HttpServletRequest request, HttpServletResponse response) {

    }

    public List<User> selectAllUsers() {
        return userDao.getAll();
    }

    public String login(HttpServletRequest request, HttpServletResponse response){
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
