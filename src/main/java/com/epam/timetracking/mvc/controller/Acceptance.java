package com.epam.timetracking.mvc.controller;

/**
 * Created by Denis on 26.04.2018.
 */
public interface Acceptance {

    void acceptNewActivity(int activityId);

    void rejectNewActivity(int activityId);

    void acceptActivityRemoval(int activityId);

    void rejectActivityRemoval(int activityId);
}
