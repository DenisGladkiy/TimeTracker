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
import java.util.regex.Pattern;

/**
 * Created by Denis on 01.05.2018.
 * Class helps to executors in creation of Activity or User instances
 * from HttpServletRequest parameters
 */
public class ControllerHelper {
    private static Logger logger = Logger.getLogger(ControllerHelper.class);
    private static final String ID_PATTERN = "^[1-9]\\d{1,20}$";
    private static final String NAME_PATTERN = "\\D{1,20}";
    private static final String EMAIL_PATTERN = "\\S+@\\S+\\.\\S+";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{4,20}$";
    private static final String ROLE_PATTERN = "^((ADMIN)|(USER))$";

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
        logger.info("Activity from helper = " + activity);
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
        int userId = (strId == null) ? 0 : Integer.valueOf(strId);
        User user = new User(userId, request.getParameter("firstName"), request.getParameter("lastName"));
        user.setEmail(request.getParameter("email"));
        String hashedPassword = hashPassword(request.getParameter("pass"));
        user.setPassword(hashedPassword);
        String role = request.getParameter("role");
        user.setRole((role == null) ? null : UserRoleEnum.valueOf(role));
        logger.info("Helper user bean = " + user);
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

    public boolean isCreateUserRequestValid(HttpServletRequest request){
        String strId = request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("pass");
        String role = request.getParameter("role");
        if(isUserIdValid(strId) &&
                isInputValid(firstName, NAME_PATTERN) &&
                isInputValid(lastName, NAME_PATTERN) &&
                isInputValid(email, EMAIL_PATTERN) &&
                isInputValid(password, PASSWORD_PATTERN) &&
                isInputValid(role, ROLE_PATTERN)){
            logger.info("User fields are valid");
            return true;
        }
        logger.info("User fields are invalid");
        return false;
    }

    private boolean isUserIdValid(String strId){
        logger.info("User Id validation " + strId);
        if(strId == null) {
            return true;
        }else {
            return Pattern.matches(ID_PATTERN, strId);
        }
    }

    private boolean isInputValid(String input, String regex){
        logger.info("User fields validation " + input);
        if(input == null){
            return false;
        }else {
            return Pattern.matches(regex, input);
        }
    }

    private Date parseDate(String stringDate){
        logger.info("String date = " + stringDate);
        if(stringDate == null){
            logger.info("String date == null");
            return null;
        }else {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return format.parse(stringDate);
            } catch (ParseException e) {
                logger.info("Parse exception");
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
