package com.epam.timetracking.mvc.model.dao;

import java.util.List;

/**
 * Created by Denis on 27.04.2018.
 * Interface defines general methods for DAO classes
 */
public interface AbstractDao<T, K> {

    List<T> getAll();

    T getById(K id);

    boolean insert(T t);

    boolean update(T t);

    boolean delete(T t);

    boolean isExist(T t);

    void closeConnection();
}
