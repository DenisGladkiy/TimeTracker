package com.epam.timetracking.mvc.controller;

import com.epam.timetracking.mvc.model.entity.User;

/**
 * Created by Denis on 22.04.2018.
 */
public interface Controller {

    User createAccount(String login, String password);

    boolean logIn(String login, String password);
}
