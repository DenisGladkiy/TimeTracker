package com.epam.timetracking.mvc.model.dao;

import com.epam.timetracking.exception.IncorrectInputException;
import com.epam.timetracking.mvc.model.entity.Activity;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Denis on 27.04.2018.
 */
public class ActivityDao implements AbstractDao<Activity, Integer> {
    private Connection connection;
    private static Logger logger = Logger.getLogger(ActivityDao.class);

    public ActivityDao(Connection connection){
        this.connection = connection;
    }

    public List<Activity> getAll() {
        String queryAll = "SELECT * FROM Activities";
        return getByQuery(queryAll);
    }

    public List<Activity> getActual(){
        String queryActual = "SELECT * FROM Activities WHERE add_request <> true " +
                "                                        AND remove_request <> true " +
                "                                        AND completed <> true";
        return getByQuery(queryActual);
    }

    public List<Activity> getByUserId(int userId) {
        String query = "SELECT * FROM Activities WHERE user_id = " + userId;

        return getByQuery(query);
    }

    public Activity getById(Integer id) {
        return null;
    }

    public boolean insert(Activity activity) {
        try (Statement statement = connection.createStatement()){
            String query = createInsertionQuery(activity);
            statement.execute(query);
            return true;
        } catch (SQLException | IncorrectInputException e) {
            logger.debug(e);
            return false;
        }
    }

    public boolean update(Activity activity) {
        String query = createUpdateQuery(activity);
        try (Statement statement = connection.createStatement()){
            statement.execute(query);
            return true;
        } catch (SQLException e) {
            logger.debug(e);
            return false;
        }
    }

    public boolean delete(Activity activity) {
        int id = activity.getId();
        logger.debug("Activity to delete = " + activity.getName() + ", " + id);
        try(Statement statement = connection.createStatement()) {
            statement.execute("delete from activities where activity_id = " + id);
            return true;
        } catch (SQLException e) {
            logger.debug(e);
            return false;
        }
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
            logger.debug(e);
        }
        return activities;
    }

    private Activity createActivityFromRs(ResultSet rs) throws SQLException {
        Activity activity = new Activity(rs.getInt(1), rs.getString(2));
        activity.setDescription(rs.getString(3));
        activity.setCreationDate(rs.getDate(4));
        activity.setDeadLine(rs.getDate(5));
        activity.setTime(new Duration(rs.getDouble(6) * 1000));
        activity.setUserId(rs.getInt(7));
        activity.setAddRequest(rs.getBoolean(8));
        activity.setRemoveRequest(rs.getBoolean(9));
        activity.setCompleted(rs.getBoolean(10));
        return activity;
    }

    private String createInsertionQuery(Activity activity) throws IncorrectInputException {
        if(activity == null || activity.getDescription() == null) throw new IncorrectInputException();
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO Activities (name, description, creation_date, deadline, " +
                                               "working_time, user_id, add_request, remove_request, completed) VALUES (\"");
        builder.append(activity.getName() + "\", \"");
        builder.append(activity.getDescription() + "\", ");
        builder.append("curdate(), ");
        builder.append(convertDateToString(activity.getDeadLine()) + ", ");
        Duration duration = activity.getTime();
        builder.append(duration == null ? "null, " : (int)activity.getTime().toSeconds() + ", ");
        int userId = activity.getUserId();
        builder.append(((userId == 0) ? "null" : userId) + ", ");
        builder.append(activity.isAddRequest() + ", ");
        builder.append(activity.isRemoveRequest() + ", ");
        builder.append(activity.isCompleted() + ")");
        logger.debug("Insert = " + builder);
        return builder.toString();
    }

    private String createUpdateQuery(Activity activity){
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE Activities SET description = " + "\"");
        builder.append(activity.getDescription() + "\", ");
        builder.append("deadline = " + convertDateToString(activity.getDeadLine()) + ", ");
        Duration duration = activity.getTime();
        builder.append("working_time = " + (duration == null ? "null," : (int)activity.getTime().toSeconds() + ", "));
        int userId = activity.getUserId();
        builder.append("user_id = " + ((userId == 0) ? "null" : userId) + ", ");
        builder.append("add_request = " + activity.isAddRequest() + ", ");
        builder.append("remove_request = " + activity.isRemoveRequest() + ", ");
        builder.append("completed = " + activity.isCompleted());
        builder.append(" WHERE activity_id = " + activity.getId());
        logger.debug("Update = " + builder);
        return builder.toString();
    }

    private String convertDateToString(Date date){
        if(date != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String deadline = format.format(date);
            return ("STR_TO_DATE(\"" + deadline + "\" ,\"%Y-%m-%d\")");
        }else {
            return "null";
        }
    }
}
