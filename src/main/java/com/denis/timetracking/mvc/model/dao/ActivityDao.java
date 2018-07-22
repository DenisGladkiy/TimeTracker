package com.denis.timetracking.mvc.model.dao;

import com.denis.timetracking.exception.DaoException;
import com.denis.timetracking.exception.IncorrectInputException;
import com.denis.timetracking.mvc.model.entity.Activity;
import com.denis.timetracking.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * Created by Denis on 27.04.2018.
 * DAO of Activity Class
 * Provides interface to work with activities table in Data Base
 */
public class ActivityDao implements AbstractDao<Activity, Integer> {
    private static Logger logger = Logger.getLogger(ActivityDao.class);
    private Session currentSession;
    private Transaction transaction;

    public ActivityDao() {
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
        Optional.ofNullable(activity)
                .map(a -> {activity.setCreationDate(new Date(System.currentTimeMillis())); return activity;})
                .orElseThrow(DaoException::new);
        try {
            transaction = currentSession.beginTransaction();
            logger.debug("Activity to persist ="  + activity ) ;
            currentSession.persist(activity);
            transaction.commit();
        }catch (Exception e){
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
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaUpdate<Activity> criteria = builder.createCriteriaUpdate(Activity.class);
        Root<Activity> root = criteria.from(Activity.class);

        try{
            transaction = currentSession.beginTransaction();
            for(Activity act : activities){
                criteria.set(root.get("addRequest"), act.isAddRequest());
                criteria.where(builder.equal(root.get("id"), act.getId()));
                currentSession.createQuery(criteria).executeUpdate();
            }
            transaction.commit();
        }catch (HibernateException e) {
            Optional.ofNullable(transaction).get().rollback();
            logger.info("ActivityDao acceptActivity exception", e);
        }
    }

    public void openCurrentSession() {
        currentSession = HibernateUtil.getSessionFactory().openSession();
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
}
