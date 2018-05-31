package com.denis.timetracking.mvc.model.dao;

import com.denis.timetracking.exception.IncorrectInputException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 27.04.2018.
 * Interface defines general methods for DAO classes
 *
 * @param <T> the type parameter
 * @param <K> the type parameter
 */
public interface AbstractDao<T, K> {

    /**
     * Gets all.
     *
     * @return the all
     * @throws SQLException the sql exception
     */
    List<T> getAll() throws SQLException;

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     * @throws SQLException            the sql exception
     * @throws IncorrectInputException the incorrect input exception
     */
    T getById(K id) throws SQLException, IncorrectInputException;

    /**
     * Insert.
     *
     * @param t the t
     * @throws SQLException            the sql exception
     * @throws IncorrectInputException the incorrect input exception
     */
    void insert(T t) throws SQLException, IncorrectInputException;

    /**
     * Update.
     *
     * @param t the t
     * @throws SQLException the sql exception
     */
    void update(T t) throws SQLException;

    /**
     * Delete.
     *
     * @param t the t
     * @throws SQLException the sql exception
     */
    void delete(T t) throws SQLException;

    /**
     * Does exist boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws SQLException the sql exception
     */
    boolean isExist(K id) throws SQLException;

    /**
     * Close connection.
     */
    void closeConnection();
}
