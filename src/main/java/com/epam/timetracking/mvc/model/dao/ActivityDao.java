package com.epam.timetracking.mvc.model.dao;

import com.epam.timetracking.mvc.model.entity.Activity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 27.04.2018.
 */
public class ActivityDao implements AbstractDao<Activity, Integer> {
    private Connection connection;

    public ActivityDao(Connection connection){
        this.connection = connection;
    }

    public List<Activity> getAll() {
        String queryAll = "select * from activities";
        return getByQuery(queryAll);
    }

    public List<Activity> getByUserId(int userId) {
        return null;
    }

    public Activity getById(Integer id) {
        return null;
    }

    public boolean insert(Activity activity) {
        return false;
    }

    public boolean update(Activity activity) {
        return false;
    }

    public boolean delete(Activity activity) {
        return false;
    }

    public boolean isExist(Activity activity) {
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

    private List<Activity> getByQuery(String query){
        List<Activity> activities = new ArrayList<>();
        Activity activity;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
            while (resultSet.next()){
                activity = new Activity(resultSet.getInt(1), resultSet.getString(2));
                activity.setDescription(resultSet.getString(3));
                activities.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }
}
