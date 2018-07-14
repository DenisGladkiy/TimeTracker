package com.denis.timetracking.mvc.model.entity;

import javafx.util.Duration;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Denis on 26.04.2018.
 * Class describes an activity of the time tracking system
 */
@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id", nullable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "deadline")
    private Date deadLine;

    @Column(name = "working_time")
    private Duration workingTime;

    @Column(name = "user_id")
    @OneToOne
    @JoinColumn(name = "user_id")
    private int userId;

    @Column(name = "add_request")
    private boolean addRequest;

    @Column(name = "remove_request")
    private boolean removeRequest;

    private boolean completed;

    /**
     * Instantiates a new Activity.
     */
    public Activity(){}

    /**
     * Instantiates a new Activity.
     *
     * @param id   Activity id
     * @param name Activity name
     */
    public Activity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name){
        this.name = name;
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

    public Duration getTime() {
        return workingTime;
    }

    public void setTime(Duration workingTime) {
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public double getHours(){
        return round(workingTime.toHours(), 1);
    }

    private double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + description + ", " + creationDate +
                ", " + deadLine + ", " + workingTime + ", " + userId +
                ", " + addRequest + ", " + removeRequest + ", " + completed;
    }
}
