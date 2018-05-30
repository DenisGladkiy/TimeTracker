package com.denis.timetracking.exception;

import com.denis.timetracking.mvc.controller.command.executors.utils.ExecutorHelper;

/**
 * Created by Denis on 29.05.2018.
 */
public class DaoException extends RuntimeException {

    public DaoException(){}

    public DaoException(String message){
        super(message);
    }

    public DaoException(String message, Exception e){
        super(message, e);
    }
}
