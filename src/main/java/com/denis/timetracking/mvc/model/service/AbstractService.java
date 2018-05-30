package com.denis.timetracking.mvc.model.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Denis on 29.05.2018.
 */
public interface AbstractService<T> {

    List<T> select(HttpServletRequest request, HttpServletResponse response);

    void insert(HttpServletRequest request, HttpServletResponse response);

    void update(HttpServletRequest request, HttpServletResponse response);

    void delete(HttpServletRequest request, HttpServletResponse response);
}
