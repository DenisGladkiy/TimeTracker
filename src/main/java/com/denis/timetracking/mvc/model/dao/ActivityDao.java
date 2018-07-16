package com.denis.timetracking.mvc.model.dao;

import com.denis.timetracking.exception.DaoException;
import com.denis.timetracking.exception.IncorrectInputException;
import com.denis.timetracking.mvc.model.entity.Activity;
import com.denis.timetracking.utils.HibernateUtil;
import javafx.util.Duration;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Denis on 27.04.2018.
 * DAO of Activity Class
 * Provides interface to work with activities table in Data Base
 */
public class ActivityDao implements AbstractDao<Activity, Integer> {
    private Connection connection;
    private static Logger logger = Logger.getLogger(ActivityDao.class);
    private Session currentSession;
    private Transaction transaction;

    /**
     * Instantiates a new Activity dao.
     *
     * @param connection the connection
     */
    public ActivityDao(Connection connection){
        this.connection = connection;
    }

    public List<Activity> getAll() {
        Query query = currentSession.createQuery("FROM Activity");
        return selectList(query);
    }

    /**
     * Gets actual activities.
     *
     * @return the actual activities
     */
    public List<Activity> getActual() {
        Query query = currentSession.createQuery("FROM Activity where add_request!=:state " +
                "AND remove_request!=:state AND completed!=:state");
        query.setParameter("state", true);
        return selectList(query);
    }

    /**
     * Gets added activities.
     *
     * @return the added activities
     */
    public List<Activity> getAdded() {
        Query query = currentSession.createQuery("FROM Activity where add_request=:state");
        query.setParameter("state", true);
        return selectList(query);
    }

    /**
     * Gets removed activities.
     *
     * @return the removed activities
     */
    public List<Activity> getRemoved() {
        Query query = currentSession.createQuery("FROM Activity where remove_request=:state");
        query.setParameter("state", true);
        return selectList(query);
    }

    /**
     * Gets completed activities.
     *
     * @return the completed activities
     */
    public List<Activity> getCompleted(){
        Query query = currentSession.createQuery("FROM Activity where completed=:state");
        query.setParameter("state", true);
        return selectList(query);
    }

    /**
     * Gets activities by user id.
     *
     * @param userId the user id
     * @return by user id activities
     */
    public List<Activity> getByUserId(int userId) {
        Query query = currentSession.createQuery("FROM Activity where user_id=:userId");
        query.setParameter("userId", userId);
        return selectList(query);
    }

    /**
     *
     * @param id the id
     * @return activity by id
     */
    public Activity getById(Integer id) {
        try{
            transaction = currentSession.beginTransaction();
            Activity activity = currentSession.get(Activity.class, id);
            transaction.commit();
            return activity;
        } catch (HibernateException e){
            Optional.ofNullable(transaction).get().rollback();
            logger.info("ActivityDao getById exception", e);
            throw new IncorrectInputException("Activity with id = " + id + " doesn't exist");
        }
    }

    /**
     *
     * @param id activity id
     * @return true if activity exists
     */
    public boolean isExist(Integer id) {
        return getById(id) != null;
    }

    /**
     *
     * @param activity
     * @throws SQLException
     * @throws IncorrectInputException
     */
    public void insert(Activity activity){
        try {
            transaction = currentSession.beginTransaction();
            currentSession.persist(activity);
            transaction.commit();
        }catch (HibernateException e){
            Optional.ofNullable(transaction).get().rollback();
            logger.info("ActivityDao insert exception", e);
        }
    }

    /**
     *
     * @param activity
     * @throws SQLException
     */
    public void update(Activity activity) {
        /*String query = createUpdateQuery(activity);
        try(Statement statement = connection.createStatement()) {
            statement.execute(query);
            statement.close();
        }catch (SQLException e){
            logger.info(e);
            throw new DaoException("Activity update exception", e);
        }*/
        try {
            transaction = currentSession.beginTransaction();
            currentSession.update(activity);
            transaction.commit();
        }catch (HibernateException e){
            Optional.ofNullable(transaction).get().rollback();
            logger.info("ActivityDao update exception", e);
        }
    }

    /**
     *
     * @param activity
     */
    public void delete(Activity activity) {
//        int id = activity.getId();
//        logger.info("Activity to delete = " + activity.getName() + ", " + id);
//        Statement statement = null;
//        try {
//            statement = connection.createStatement();
//            statement.execute("delete from activities where activity_id = " + id);
//            statement.close();
//        } catch (SQLException e) {
//            logger.info(e);
//            throw new DaoException("Activity delete exception", e);
//        }
        try {
            Query query = currentSession.createQuery("DELETE Activity where activity_id=:id");
            query.setParameter("id", activity.getId());
            transaction = currentSession.beginTransaction();
            query.executeUpdate();
            transaction.commit();
        }catch (HibernateException e){
            Optional.ofNullable(transaction).get().rollback();
            logger.info("ActivityDao delete exception", e);
        }
    }

    /**
     * Accept activities.
     *
     * @param activities added activities to accept
     * @throws SQLException the sql exception
     */
    public void acceptActivities(List<Activity> activities) {
//        PreparedStatement acceptStatement = null;
//        String update = "UPDATE activities SET add_request=? WHERE activity_id=?";
//        try{
//            connection.setAutoCommit(false);
//            acceptStatement = connection.prepareStatement(update);
//            for(Activity act : activities){
//                acceptStatement.setBoolean(1, act.isAddRequest());
//                acceptStatement.setInt(2, act.getId());
//                acceptStatement.executeUpdate();
//                connection.commit();
//            }
//        } catch (SQLException e) {
//            if(connection != null){
//                connection.rollback();
//            }
//            throw e;
//        }finally {
//            if(acceptStatement != null){
//                acceptStatement.close();
//                connection.setAutoCommit(true);
//            }
//        }
        try{
            transaction = currentSession.beginTransaction();
            for(Activity act : activities){
                currentSession.update(act);
            }
            transaction.commit();
        }catch (HibernateException e) {
            Optional.ofNullable(transaction).get().rollback();
            logger.info("ActivityDao acceptActivity exception", e);
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

    public Session openCurrentSession() {
        currentSession = HibernateUtil.getSessionFactory().openSession();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeSessionFactory(){
        HibernateUtil.getSessionFactory().close();
    }

    private List<Activity> selectList(Query query){
        List<Activity> activities = Collections.EMPTY_LIST;
        try{
            transaction = currentSession.beginTransaction();
            activities = query.list();
            transaction.commit();
        } catch (HibernateException e){
            Optional.ofNullable(transaction).get().rollback();
            logger.info("ActivityDao select exception", e);
        }
        return activities;
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
            logger.info(e);
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
