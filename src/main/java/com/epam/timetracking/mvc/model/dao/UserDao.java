package com.epam.timetracking.mvc.model.dao;

import com.epam.timetracking.mvc.model.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 27.04.2018.
 */
public class UserDao implements AbstractDao<User, Integer> {
    private Connection connection;

    public UserDao(Connection connection){
        this.connection = connection;
    }
    public List<User> getAll() {
        return null;
    }

    public User getById(Integer id) {
        return null;
    }

    public boolean insert(User user) {
        return false;
    }

    public boolean update(User user) {
        return false;
    }

    public boolean delete(User user) {
        return false;
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
}
