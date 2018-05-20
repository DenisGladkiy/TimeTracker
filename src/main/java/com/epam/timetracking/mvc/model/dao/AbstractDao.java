package com.epam.timetracking.mvc.model.dao;

import com.epam.timetracking.exception.IncorrectInputException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 27.04.2018.
 * Interface defines general methods for DAO classes
 */
public interface AbstractDao<T, K> {

    List<T> getAll() throws SQLException;

    T getById(K id) throws SQLException, IncorrectInputException;

    void insert(T t) throws SQLException, IncorrectInputException;

    void update(T t) throws SQLException;

    void delete(T t) throws SQLException;

    boolean doesExist(K id) throws SQLException;

    void closeConnection();
}
