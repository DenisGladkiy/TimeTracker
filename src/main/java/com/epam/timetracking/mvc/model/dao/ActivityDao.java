package com.epam.timetracking.mvc.model.dao;

import com.epam.timetracking.mvc.model.entity.Activity;
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
public class ActivityDao implements AbstractDao<Activity, Integer> {
    private Connection connection;
    static Logger logger = Logger.getLogger(ActivityDao.class);

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
        String query = createInsertionQuery(activity);
        logger.debug("Insert = " + query);
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            return true;
        } catch (SQLException e) {
            logger.debug(e);
            return false;
        }
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
            logger.debug(e);
        }
    }

    private List<Activity> getByQuery(String query){
        List<Activity> activities = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
            while (resultSet.next()){
                activities.add(createActivityFromRs(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }

    private Activity createActivityFromRs(ResultSet rs) throws SQLException {
        Activity activity = new Activity(rs.getInt(1), rs.getString(2));
        activity.setDescription(rs.getString(3));
        logger.debug("Date = " + rs.getDate(4));
        activity.setCreationDate(rs.getDate(4));
        activity.setDeadLine(rs.getDate(5));
        activity.setTime(rs.getTimestamp(6));
        activity.setUserId(rs.getInt(7));
        logger.debug("UserID = " + rs.getDate(7));
        activity.setAddRequest(rs.getBoolean(8));
        activity.setRemoveRequest(rs.getBoolean(9));
        return activity;
    }

    private String createInsertionQuery(Activity activity) {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO Activities (name, description, creation_date, deadline, " +
                                               "working_time, user_id, add_request, remove_request) VALUES (\"");
        builder.append(activity.getName() + "\", \"");
        builder.append(activity.getDescription() + "\", ");
        builder.append("curdate(), ");
        builder.append(activity.getDeadLine() + ", ");
        builder.append(activity.getTime() + ", ");
        int userId = activity.getUserId();
        builder.append(((userId == 0) ? "null" : userId) + ", ");
        builder.append(activity.isAddRequest() + ", ");
        builder.append(activity.isRemoveRequest() + ")");
        return builder.toString();
    }
}
