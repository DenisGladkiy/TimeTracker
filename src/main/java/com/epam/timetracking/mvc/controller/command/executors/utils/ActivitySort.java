package com.epam.timetracking.mvc.controller.command.executors.utils;

import com.epam.timetracking.mvc.model.entity.Activity;

import java.util.ArrayList;

/**
 * Created by Denis on 10.05.2018.
 */
public class ActivitySort {
    ArrayList<Activity> activities;

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "ActivitySort{" +
                "activities=" + activities +
                '}';
    }
}
