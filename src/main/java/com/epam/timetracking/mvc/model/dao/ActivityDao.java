package com.epam.timetracking.mvc.model.dao;

import com.epam.timetracking.exception.IncorrectInputException;
import com.epam.timetracking.mvc.model.entity.Activity;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        try {
            String query = createInsertionQuery(activity);
            logger.debug("Insert = " + query);
            Statement statement = connection.createStatement();
            statement.execute(query);
            return true;
        } catch (SQLException | IncorrectInputException e) {
            logger.debug(e);
            return false;
        }
    }

    public boolean update(Activity activity) {
        return false;
    }

    public boolean delete(Activity activity) {
        String name = activity.getName();
        logger.debug("Activity to delete = " + name);
        try {
            Statement statement = connection.createStatement();
            statement.execute("delete from activities where name = " + "\"" + name + "\"");
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
            e.printStackTrace();
        }
        return activities;
    }

    private Activity createActivityFromRs(ResultSet rs) throws SQLException {
        Activity activity = new Activity(rs.getInt(1), rs.getString(2));
        activity.setDescription(rs.getString(3));
        logger.debug("Date = " + rs.getDate(4));
        activity.setCreationDate(rs.getDate(4));
        logger.debug("Deadline Date = " + rs.getDate(5));
        activity.setDeadLine(rs.getDate(5));
        logger.debug("Activity with deadline date = " + activity);
        activity.setTime(rs.getTimestamp(6));
        logger.debug("UserID = " + rs.getInt(7));
        activity.setUserId(rs.getInt(7));
        activity.setAddRequest(rs.getBoolean(8));
        activity.setRemoveRequest(rs.getBoolean(9));
        return activity;
    }

    private String createInsertionQuery(Activity activity) throws IncorrectInputException {
        if(activity == null || activity.getDescription() == null) throw new IncorrectInputException();
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO Activities (name, description, creation_date, deadline, " +
                                               "working_time, user_id, add_request, remove_request) VALUES (\"");
        builder.append(activity.getName() + "\", \"");
        builder.append(activity.getDescription() + "\", ");
        builder.append("curdate(), ");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String deadline = format.format(activity.getDeadLine());
        builder.append("STR_TO_DATE(\"" + deadline +"\" ,\"%Y-%m-%d\")" + ", ");
        builder.append(activity.getTime() + ", ");
        int userId = activity.getUserId();
        builder.append(((userId == 0) ? "null" : userId) + ", ");
        builder.append(activity.isAddRequest() + ", ");
        builder.append(activity.isRemoveRequest() + ")");
        return builder.toString();
    }
}
