package com.denis.timetracking.utils;

import com.denis.timetracking.mvc.model.entity.Activity;
import com.denis.timetracking.mvc.model.entity.User;
import com.denis.timetracking.mvc.model.entity.UserRoleEnum;
import javafx.util.Duration;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Denis on 01.05.2018.
 * Class helps to executors in creation of Activity or User instances
 * from HttpServletRequest parameters
 */
public class ControllerHelper {
    private static Logger logger = Logger.getLogger(ControllerHelper.class);

    /**
     * Create activity bean activity.
     *
     * @param request the request
     * @return the activity
     */
    public Activity createActivityBean(HttpServletRequest request){
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        Activity activity = createActivityInstance(id, name);
        if(activity == null) return null;
        activity.setDescription(request.getParameter("description"));
        activity.setCreationDate(parseDate(request.getParameter("creationDate")));
        activity.setDeadLine(parseDate(request.getParameter("deadLine")));
        String time = request.getParameter("time");
        String user = request.getParameter("userId");
        String accept = request.getParameter("added");
        String remove = request.getParameter("removed");
        String complete = request.getParameter("complete");
        if (time != null) {
            activity.setTime(new Duration(Double.valueOf(time) * 3600000));
        }
        activity.setAddRequest(isTrue(accept));
        activity.setRemoveRequest(isTrue(remove));
        activity.setCompleted(isTrue(complete));
        if (user != null && !user.equals("")) {
            activity.setUserId(Integer.valueOf(user));
        }
        logger.debug("Activity from helper = " + activity);
        return activity;
    }

    /**
     * Create user bean user.
     *
     * @param request the request
     * @return the user
     */
    public User createUserBean(HttpServletRequest request){
        String strId = request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String hashedPassword = hashPassword(request.getParameter("pass"));
        logger.debug("Helper user bean = " + firstName + " " + lastName + " " + hashedPassword);
        int id = 0;
        if (strId != null) id = Integer.valueOf(strId);
        User user = new User(id, firstName, lastName);
        user.setEmail(request.getParameter("email"));
        user.setPassword(hashedPassword);
        String role = request.getParameter("role");
        if(role != null) {
            user.setRole(UserRoleEnum.valueOf(role));
        }
        return user;
    }

    /**
     * Get list of accepted activities.
     *
     * @param acceptedActivities String pairs id-name of accepted activities from JSP
     * @return the list of Activities
     */
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

    private Activity createActivityInstance(String id, String name){
        Activity activity;
        if(id != null){
            activity = new Activity(Integer.valueOf(id), name);
            return activity;
        }else if(name != null && !name.equals("")) {
            activity = new Activity();
            activity.setName(name);
            return activity;
        }else{
            return null;
        }
    }

    private boolean isTrue(String input){
        return input != null && (input.equals("on")||input.equals("true"));
    }

    private String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
