package com.epam.timetracking.utils;

import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.mvc.model.entity.UserRoleEnum;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Denis on 01.05.2018.
 */
public class ControllerHelper {
    private static Logger logger = Logger.getLogger(ControllerHelper.class);

    public Activity createActivityBean(HttpServletRequest request){
        Activity activity;
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        if(id != null){
            activity = new Activity(Integer.valueOf(id), name);
        }else {
            activity = new Activity();
            activity.setName(name);
        }
        String description = request.getParameter("description");
        String creationDate = request.getParameter("creationDate");
        String deadline = request.getParameter("deadLine");
        String time = request.getParameter("time");
        String user = request.getParameter("userId");
        String accept = request.getParameter("added");
        String remove = request.getParameter("removed");
        String complete = request.getParameter("complete");
        logger.debug("Params from request = " + id + ", " + name + ", " + description + "," + creationDate + "," + deadline + ", " +
                    time + ", " + user + ", " + accept + ", " + remove + ", " + complete);
        activity.setDescription(description);
        activity.setDeadLine(parseDate(deadline));
        activity.setCreationDate(parseDate(creationDate));
        if(time != null) {
            activity.setTime(new Duration(Double.valueOf(time) * 3600000));
        }
        if(accept != null && accept.equals("true")) {
            activity.setAddRequest(true);
        }else{
            activity.setAddRequest(false);
        }
        if(remove != null && remove.equals("on")) {
            activity.setRemoveRequest(true);
        }else{
            activity.setRemoveRequest(false);
        }
        if(complete != null && complete.equals("on")){
        activity.setCompleted(true);
        }else{
            activity.setCompleted(false);
        }
        if(user != null && !user.equals("")) {
            activity.setUserId(Integer.valueOf(user));
        }
        logger.debug("Activity from helper = " + activity);
        return activity;
    }

    public User createUserBean(HttpServletRequest request){
        String strId = request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        logger.debug("Helper user bean = " + firstName + " " + lastName);
        int id = 0;
        if (strId != null) id = Integer.valueOf(strId);
        User user = new User(id, firstName, lastName);
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("pass"));
        String role = request.getParameter("role");
        if(role != null) {
            user.setRole(UserRoleEnum.valueOf(role));
        }
        return user;
    }

    public List<Activity> getListActivities(String acceptedActivities){
        String[] activitiesArr = acceptedActivities.split(",");
        List<Activity> activities = new ArrayList<>();
        String[] activityArr;
        Activity activity;
        for(String act : activitiesArr){
            activityArr = act.split(";");
            activity = new Activity(Integer.valueOf(activityArr[0]), activityArr[1]);
            activity.setAddRequest(false);
            activities.add(activity);
        }
        return activities;
    }

    private Date parseDate(String stringDate){
        logger.debug("String date = " + stringDate);
        if(stringDate == null){
            logger.debug("String date == null");
            return null;
        }else {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return format.parse(stringDate);
            } catch (ParseException e) {
                logger.debug("Parse exception");
                e.printStackTrace();
                return null;
            }
        }
    }
}
