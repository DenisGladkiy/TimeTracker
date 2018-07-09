package com.denis.timetracking.mvc.model.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Denis on 29.05.2018.
 */
public interface AbstractService<T> {

    List<T> select(HttpServletRequest request);

    String insert(HttpServletRequest request);

    String update(HttpServletRequest request);

    String delete(HttpServletRequest request);
}
