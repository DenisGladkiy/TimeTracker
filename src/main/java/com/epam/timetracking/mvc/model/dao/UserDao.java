package com.epam.timetracking.mvc.model.dao;

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

    public List<User> getAll() {
        String query = "SELECT * FROM Users";
        try(PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){
            return getByQuery(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getById(Integer id) {
        String query = "SELECT * FROM Users WHERE user_id =?";
        ResultSet rs = null;
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                User user = createUserFromRs(rs);
                return user;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public User getByLogin(String login){
        String query = "SELECT * FROM Users WHERE email=?";
        ResultSet rs = null;
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, login);
            rs = ps.executeQuery();
            if(rs.next()) {
                User user = createUserFromRs(rs);
                return user;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean insert(User user) {
        if(user == null) return false;
        String insert = "INSERT INTO Users (first_name, last_name, email, password, role) VALUES (?,?,?,?,?)";
        try(PreparedStatement insertStatement = connection.prepareStatement(insert)){
            insertStatement.setString(1, user.getFirstName());
            insertStatement.setString(2, user.getLastName());
            insertStatement.setString(3, user.getEmail());
            insertStatement.setString(4, user.getPassword());
            insertStatement.setString(5, user.getRole().toString());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
}
