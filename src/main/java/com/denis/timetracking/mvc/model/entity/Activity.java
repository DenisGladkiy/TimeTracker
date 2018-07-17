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

    @Column(name = "working_time", columnDefinition = "bigint")
    @Convert(converter = DurationToLongConverter.class)
    private Duration workingTime;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "add_request")
    private Boolean addRequest;

    @Column(name = "remove_request")
    private Boolean removeRequest;

    private Boolean completed;

    @PrePersist
    protected void onCreate() {
        creationDate = new Date();
    }
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
    public Activity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId() {
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

    public Boolean isAddRequest() {
        return addRequest;
    }

    public void setAddRequest(Boolean addRequest) {
        this.addRequest = addRequest;
    }

    public Boolean isRemoveRequest() {
        return removeRequest;
    }

    public void setRemoveRequest(Boolean removeRequest) {
        this.removeRequest = removeRequest;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Double getHours(){
        return round(workingTime.toHours(), 1);
    }

    private Double round (Double value, Integer precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + description + ", " + creationDate +
                ", " + deadLine + ", " + workingTime + ", " + userId +
                ", " + addRequest + ", " + removeRequest + ", " + completed;
    }

    @Converter
    public static class DurationToLongConverter implements AttributeConverter<Duration, Long>
    {

        @Override
        public Long convertToDatabaseColumn(Duration duration)
        {
            return duration == null ? null : Math.round(duration.toMillis());
        }

        @Override
        public Duration convertToEntityAttribute(Long dbData)
        {
            return dbData == null ? null : Duration.millis(dbData);
        }
    }
}


