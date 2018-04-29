package com.epam.timetracking.mvc.model.entity;

import java.util.Date;

/**
 * Created by Denis on 26.04.2018.
 * Describes activity of time tracking system
 */
public class Activity {
    private int id;
    private String name;
    private String description;
    private Date creationDate;
    private Date deadLine;
    private Date workingTime;
    private int userId;
    private boolean addRequest;
    private boolean removeRequest;

    public Activity(){}

    public Activity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        return workingTime;
    }

    public void setTime(Date workingTime) {
        this.workingTime = workingTime;
    }

    public boolean isAddRequest() {
        return addRequest;
    }

    public void setAddRequest(boolean addRequest) {
        this.addRequest = addRequest;
    }

    public boolean isRemoveRequest() {
        return removeRequest;
    }

    public void setRemoveRequest(boolean removeRequest) {
        this.removeRequest = removeRequest;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCreationDate(Date creationDate){
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
