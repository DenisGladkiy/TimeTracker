package com.denis.timetracking.mvc.model.dao;

import com.denis.timetracking.exception.IncorrectInputException;
import com.denis.timetracking.mvc.model.entity.User;
import com.denis.timetracking.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Denis on 27.04.2018.
 * DAO of User Class
 * Provides interface to work with users table in Data Base
 */
public class UserDao implements AbstractDao<User, Integer> {
    private static Logger logger = Logger.getLogger(ActivityDao.class);
    private Session currentSession;
    private Transaction transaction;

    public UserDao() {
    }

    /**
     *
     * @return the list of all users
     */
    public List<User> getAll() {
        List<User> users = Collections.EMPTY_LIST;
        try {
            transaction = currentSession.beginTransaction();
            users = currentSession.createQuery("FROM User").list();
            transaction.commit();
        }catch (HibernateException e){
            Optional.ofNullable(transaction).get().rollback();
            logger.info("UserDao getAll exception", e);
        }
        return users;
    }

    /**
     * Gets user by id.
     *
     * @param id the user id
     * @return user by id
     * @throws IncorrectInputException
     */
    public User getById(Integer id) {
        try{
            transaction = currentSession.beginTransaction();
            User user = currentSession.get(User.class, id);
            transaction.commit();
            return user;
        } catch (HibernateException e){
            Optional.ofNullable(transaction).get().rollback();
            logger.info("UserDao getById exception", e);
            throw new IncorrectInputException("User with id = " + id + " doesn't exist");
        }
    }

    /**
     * Gets user by login.
     *
     * @param login the login
     * @return the by login
     * @throws IncorrectInputException the incorrect input exception
     */
    public User getByLogin(String login) {
        try{
            transaction = currentSession.beginTransaction();
            Query query = currentSession.createQuery("FROM User where email=:email");
            query.setParameter("email", login);
            User user = (User) query.uniqueResult();
            transaction.commit();
            return user;
        } catch (HibernateException e){
            Optional.ofNullable(transaction).get().rollback();
            logger.info("UserDao getById exception", e);
            throw new IncorrectInputException("User " + login + " doesn't exist");
        }
    }

    /**
     * @param user
     */
    public void insert(User user) {
        try {
            transaction = currentSession.beginTransaction();
            logger.debug("User to persist = " + user);
            currentSession.persist(user);
            transaction.commit();
        }catch (HibernateException e){
            Optional.ofNullable(transaction).get().rollback();
            logger.info("UserDao insert exception", e);
        }
    }

    public void update(User user) {
        throw new UnsupportedOperationException("Method Update User is not implemented");
    }

    /**
     *
     * @param user
     */
    public void delete(User user) {
        try {
            transaction = currentSession.beginTransaction();
            user = currentSession.get(User.class, user.getId());
            currentSession.delete(user);
            transaction.commit();
        }catch (HibernateException e){
            Optional.ofNullable(transaction).get().rollback();
            logger.info("UserDao delete exception", e);
        }
    }

    /**
     *
     * @param userId
     * @return true if user exists
     */
    public boolean isExist(Integer userId) {
        return getById(userId) != null;
    }

    public Session openCurrentSession() {
        currentSession = HibernateUtil.getSessionFactory().openSession();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }
}
