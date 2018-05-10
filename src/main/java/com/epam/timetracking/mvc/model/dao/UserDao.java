package com.epam.timetracking.mvc.model.dao;

import com.epam.timetracking.exception.IncorrectInputException;
import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.mvc.model.entity.UserRoleEnum;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 27.04.2018.
 */
public class UserDao implements AbstractDao<User, Integer> {
    private Connection connection;
    private static Logger logger = Logger.getLogger(ActivityDao.class);

    public UserDao(Connection connection){
        this.connection = connection;
    }

    public List<User> getAll() {
        String query = "SELECT * FROM Users";
        return getByQuery(query);
    }

    public User getById(Integer id) {
        String query = "SELECT * FROM Users WHERE user_id = " + id;
        List<User> users = getByQuery(query);
        if(users != null && users.size() > 0) {
            return getByQuery(query).get(0);
        }else{return null;}
    }

    public User getByLoginData(String login, String password){
        String query = "SELECT * FROM Users WHERE email=\"" + login + "\" AND password=\"" + password +"\"";
        logger.debug("Login query = " + query);
        List<User> users = getByQuery(query);
        if(users != null && users.size() > 0) {
            return getByQuery(query).get(0);
        }else {return null;}
    }

    public boolean insert(User user) {
        try (Statement statement = connection.createStatement()){
            String query = createInsertionQuery(user);
            statement.execute(query);
            return true;
        } catch (SQLException | IncorrectInputException e) {
            logger.debug(e);
            return false;
        }
    }

    public boolean update(User user) {
        return false;
    }

    public boolean delete(User user) {
        int id = user.getId();
        logger.debug("User to delete = " + user.getFirstName() + ", " + id);
        try(Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM users where user_id = " + id);
            return true;
        } catch (SQLException e) {
            logger.debug(e);
            return false;
        }
    }

    public boolean isExist(User user) {
        return false;
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<User> getByQuery(String query){
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
            while (resultSet.next()){
                users.add(createUserFromRs(resultSet));
            }
        } catch (SQLException e) {
            logger.debug(e);
        }
        return users;
    }

    private String createInsertionQuery(User user) throws IncorrectInputException {
        if(user == null) throw new IncorrectInputException();
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO Users (first_name, last_name, email, password, role) VALUES (\"");
        builder.append(user.getFirstName() + "\", \"");
        builder.append(user.getLastName() + "\", \"");
        builder.append(user.getEmail() + "\", \"");
        builder.append(user.getPassword() + "\", \"");
        builder.append(user.getRole() + "\")");
        logger.debug("Insert = " + builder);
        return builder.toString();
    }

    private User createUserFromRs(ResultSet rs) throws SQLException {
        User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
        user.setEmail(rs.getString(4));
        user.setPassword(rs.getString(5));
        String role = rs.getString(6);
        if(role != null) {
            user.setRole(UserRoleEnum.valueOf(rs.getString(6)));
        }
        return user;
    }
}
