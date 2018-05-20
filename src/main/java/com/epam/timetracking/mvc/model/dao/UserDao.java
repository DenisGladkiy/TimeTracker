package com.epam.timetracking.mvc.model.dao;

import com.epam.timetracking.exception.IncorrectInputException;
import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.mvc.model.entity.UserRoleEnum;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 27.04.2018.
 * DAO of User Class
 * Provides interface to work with users table in Data Base
 */
public class UserDao implements AbstractDao<User, Integer> {
    private Connection connection;
    private static Logger logger = Logger.getLogger(ActivityDao.class);

    public UserDao(Connection connection){
        this.connection = connection;
    }

    public List<User> getAll() throws SQLException {
        String query = "SELECT * FROM Users";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<User> users = getByQuery(rs);
        closeResources(rs, ps);
        return users;
    }

    public User getById(Integer id) throws SQLException, IncorrectInputException {
        String query = "SELECT * FROM Users WHERE user_id =?";
        ResultSet rs;
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        rs = ps.executeQuery();
        if(rs.next()){
            User user = createUserFromRs(rs);
            closeResources(rs, ps);
            return user;
        }else{
            throw new IncorrectInputException("User with id = " + id + " doesn't exist");
        }
    }

    public User getByLogin(String login) throws SQLException, IncorrectInputException {
        String query = "SELECT * FROM Users WHERE email=?";
        ResultSet rs;
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, login);
        rs = ps.executeQuery();
        if(rs.next()) {
            User user = createUserFromRs(rs);
            closeResources(rs, ps);
            return user;
        }else{
            throw new IncorrectInputException("User doesn't exist");
        }
    }

    public void insert(User user) throws SQLException, IncorrectInputException {
        if(user == null) throw new IncorrectInputException("Impossible to create user with provided data");
        String insert = "INSERT INTO Users (first_name, last_name, email, password, role) VALUES (?,?,?,?,?)";
        PreparedStatement insertStatement = connection.prepareStatement(insert);
        insertStatement.setString(1, user.getFirstName());
        insertStatement.setString(2, user.getLastName());
        insertStatement.setString(3, user.getEmail());
        insertStatement.setString(4, user.getPassword());
        insertStatement.setString(5, user.getRole().toString());
        insertStatement.executeUpdate();
        insertStatement.close();
    }

    public void update(User user) { }

    public void delete(User user) throws SQLException {
        int id = user.getId();
        logger.debug("User to delete = " + user.getFirstName() + ", " + id);
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM users where user_id = " + id);
        statement.close();
    }

    public boolean doesExist(Integer userId) throws SQLException {
        String query = "SELECT user_id FROM users WHERE user_id = ?";
        ResultSet rs = null;
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, userId);
        rs = ps.executeQuery();
        if(rs.next()) {
            closeResources(rs, ps);
            return true;
        }
        closeResources(rs, ps);
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

    private List<User> getByQuery(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next()){
            users.add(createUserFromRs(rs));
        }
        return users;
    }

    private User createUserFromRs(ResultSet rs) throws SQLException {
        User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
        user.setEmail(rs.getString(4));
        user.setPassword(rs.getString(5));
        String role = rs.getString(6);
        if (role != null) {
            user.setRole(UserRoleEnum.valueOf(rs.getString(6)));
        }
        return user;
    }

    private void closeResources(ResultSet rs, PreparedStatement ps) throws SQLException {
        rs.close();
        ps.close();
    }
}
