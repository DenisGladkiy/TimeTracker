package com.denis.timetracking.mvc.model.dao;

import com.denis.timetracking.exception.DaoException;
import com.denis.timetracking.exception.IncorrectInputException;
import com.denis.timetracking.mvc.model.entity.Activity;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Denis on 27.04.2018.
 * DAO of Activity Class
 * Provides interface to work with activities table in Data Base
 */
public class ActivityDao implements AbstractDao<Activity, Integer> {
    private Connection connection;
    private static Logger logger = Logger.getLogger(ActivityDao.class);

    /**
     * Instantiates a new Activity dao.
     *
     * @param connection the connection
     */
    public ActivityDao(Connection connection){
        this.connection = connection;
    }

    public List<Activity> getAll() throws SQLException {
        String queryAll = "SELECT * FROM activities";
        return getByQuery(queryAll);
    }

    /**
     * Gets actual activities.
     *
     * @return the actual activities
     * @throws SQLException the sql exception
     */
    public List<Activity> getActual() throws SQLException {
        String queryActual = "SELECT * FROM activities WHERE add_request <> true " +
                "                                        AND remove_request <> true " +
                "                                        AND completed <> true";
        return getByQuery(queryActual);
    }

    /**
     * Gets added activities.
     *
     * @return the added activities
     * @throws SQLException the sql exception
     */
    public List<Activity> getAdded() throws SQLException {
        String query = "SELECT * FROM activities WHERE add_request = true";
        return getByQuery(query);
    }

    /**
     * Gets removed activities.
     *
     * @return the removed activities
     * @throws SQLException the sql exception
     */
    public List<Activity> getRemoved() throws SQLException {
        String query = "SELECT * FROM activities WHERE remove_request = true";
        return getByQuery(query);
    }

    /**
     * Gets completed activities.
     *
     * @return the completed activities
     * @throws SQLException the sql exception
     */
    public List<Activity> getCompleted() throws SQLException {
        String query = "SELECT * FROM activities WHERE completed = true";
        return getByQuery(query);
    }

    /**
     * Gets activities by user id.
     *
     * @param userId the user id
     * @return by user id activities
     * @throws SQLException the sql exception
     */
    public List<Activity> getByUserId(int userId) {
        String query = "SELECT * FROM activities WHERE user_id = " + userId;
        return getByQuery(query);
    }

    /**
     *
     * @param id the id
     * @return activity by id
     * @throws SQLException
     */
    public Activity getById(Integer id) throws SQLException {
        String query = "SELECT * FROM activities WHERE activity_id = " + id;
        return getByQuery(query).get(0);
    }

    /**
     *
     * @param id activity id
     * @return true if activity exists
     * @throws SQLException
     */
    public boolean doesExist(Integer id) throws SQLException {
        return getById(id) != null;
    }

    /**
     *
     * @param activity
     * @throws SQLException
     * @throws IncorrectInputException
     */
    public void insert(Activity activity) throws SQLException, IncorrectInputException {
        Statement statement = connection.createStatement();
        String query = createInsertionQuery(activity);
        statement.execute(query);
        statement.close();
    }

    /**
     *
     * @param activity
     * @throws SQLException
     */
    public void update(Activity activity) throws SQLException {
        String query = createUpdateQuery(activity);
        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
    }

    /**
     *
     * @param activity
     * @throws SQLException
     */
    public void delete(Activity activity) throws SQLException {
        int id = activity.getId();
        logger.info("Activity to delete = " + activity.getName() + ", " + id);
        Statement statement = connection.createStatement();
        statement.execute("delete from activities where activity_id = " + id);
        statement.close();
    }

    /**
     * Accept activities.
     *
     * @param activities added activities to accept
     * @throws SQLException the sql exception
     */
    public void acceptActivities(List<Activity> activities) throws SQLException {
        PreparedStatement acceptStatement = null;
        String update = "UPDATE activities SET add_request=? WHERE activity_id=?";
        try{
            connection.setAutoCommit(false);
            acceptStatement = connection.prepareStatement(update);
            for(Activity act : activities){
                acceptStatement.setBoolean(1, act.isAddRequest());
                acceptStatement.setInt(2, act.getId());
                acceptStatement.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            if(connection != null){
                connection.rollback();
            }
            throw e;
        }finally {
            if(acceptStatement != null){
                acceptStatement.close();
                connection.setAutoCommit(true);
            }
        }
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.info(e);
            e.printStackTrace();
        }
    }

    private List<Activity> getByQuery(String query) {
        List<Activity> activities = new ArrayList<>();
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                activities.add(createActivityFromRs(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Activity get by query exception", e);
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
        if(activity == null)
            throw new IncorrectInputException("Impossible to create activity with provided data");
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO activities (name, description, creation_date, deadline, " +
                                               "working_time, user_id, add_request, remove_request, completed) VALUES (\"");
        builder.append(activity.getName() + "\", \"");
        builder.append(activity.getDescription() + "\", ");
        builder.append("curdate(), ");
        builder.append(convertDateToString(activity.getDeadLine()) + ", ");
        Duration duration = activity.getTime();
        builder.append(duration == null ? "0, " : (int)activity.getTime().toSeconds() + ", ");
        int userId = activity.getUserId();
        builder.append(((userId == 0) ? "null" : userId) + ", ");
        builder.append(activity.isAddRequest() + ", ");
        builder.append(activity.isRemoveRequest() + ", ");
        builder.append(activity.isCompleted() + ")");
        logger.info("Insert = " + builder);
        return builder.toString();
    }

    private String createUpdateQuery(Activity activity){
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE activities SET description = " + "\"");
        builder.append(activity.getDescription() + "\", ");
        builder.append("deadline = " + convertDateToString(activity.getDeadLine()) + ", ");
        Duration duration = activity.getTime();
        builder.append("working_time = working_time + " + (duration == null ? "0," : (int)activity.getTime().toSeconds() + ", "));
        int userId = activity.getUserId();
        builder.append("user_id = " + ((userId == 0) ? "null" : userId) + ", ");
        builder.append("add_request = " + activity.isAddRequest() + ", ");
        builder.append("remove_request = " + activity.isRemoveRequest() + ", ");
        builder.append("completed = " + activity.isCompleted());
        builder.append(" WHERE activity_id = " + activity.getId());
        logger.info("Update = " + builder);
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
