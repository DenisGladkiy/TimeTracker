package com.epam.timetracking.mvc.controller;

import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.mvc.model.entity.User;

import java.util.List;

/**
 * Created by Denis on 26.04.2018.
 */
public interface Selection {

    List<Activity> getAllActivities();

    List<Activity> getActivitiesByUser(int userId);

    List<User> getAllUsers();
}
