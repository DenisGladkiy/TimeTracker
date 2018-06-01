package com.denis.timetracking.utils;

import com.denis.timetracking.mvc.model.entity.Activity;
import com.denis.timetracking.mvc.model.entity.User;
import com.denis.timetracking.mvc.model.entity.UserRoleEnum;
import javafx.util.Duration;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private static final String ACTIVITY_NAME_PATTERN = ".{1,50}";
    private static final String DESCRIPTION_PATTERN = ".{1,200}";

    /**
     * Create activity bean activity.
     *
     * @param request the request
     * @return the activity
     */
    public Activity createActivityBean(HttpServletRequest request){
        Activity activity = null;
        if(isCreateActivityRequestValid(request)){
            activity = new Activity();
            Map<String, String> activityData = prepareActivityData(request);
            activity.setId(Integer.valueOf(activityData.get("id")));
            activity.setName(activityData.get("name"));
            activity.setDescription(activityData.get("description"));
            activity.setCreationDate(parseDate(activityData.get("creationDate")));
            activity.setDeadLine(parseDate(activityData.get("deadLine")));
            activity.setTime(new Duration(Double.valueOf(activityData.get("time")) * 3600000));
            activity.setUserId(Integer.valueOf(activityData.get("userId")));
            activity.setAddRequest(Boolean.valueOf(activityData.get("added")));
            activity.setRemoveRequest(Boolean.valueOf(activityData.get("removed")));
            activity.setCompleted(Boolean.valueOf(activityData.get("complete")));
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
        boolean isValid = isUserIdValid(strId) &&
                isInputValid(firstName, NAME_PATTERN) &&
                isInputValid(lastName, NAME_PATTERN) &&
                isInputValid(email, EMAIL_PATTERN) &&
                isInputValid(password, PASSWORD_PATTERN) &&
                isInputValid(role, ROLE_PATTERN);
            logger.info("User fields are valid = " + isValid);
            return isValid;
    }

    public boolean isCreateActivityRequestValid(HttpServletRequest request){
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String deadline = request.getParameter("deadLine");
        boolean isValid = isInputValid(name, ACTIVITY_NAME_PATTERN) &&
                            isDescriptionValid(description) &&
                            isDeadlineValid(deadline);
        logger.info("Activity creation validation = " + isValid);
        return isValid;
    }

    private Map prepareActivityData(HttpServletRequest request){
        Map<String, String> data = new HashMap<>();
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String creationDate = request.getParameter("creationDate");
        String deadline = request.getParameter("deadLine");
        String time = request.getParameter("time");
        String user = request.getParameter("userId");
        String added = request.getParameter("added");
        String removed = request.getParameter("removed");
        String complete = request.getParameter("complete");
        data.put("id", (id == null) ? "0" : id);
        data.put("name", name);
        data.put("description", description);
        data.put("creationDate", creationDate);
        data.put("deadLine", deadline);
        data.put("time", (time == null) ? "0" : time);
        data.put("userId", (user == null) ? "0" : user);
        data.put("added", String.valueOf(isTrue(added)));
        data.put("removed", String.valueOf(isTrue(removed)));
        data.put("complete", String.valueOf(isTrue(complete)));
        return data;
    }

    private boolean isUserIdValid(String strId){
        logger.info("User Id validation " + strId);
        if(strId == null) {
            return true;
        }else {
            return Pattern.matches(ID_PATTERN, strId);
        }
    }

    private boolean isDescriptionValid(String description){
        logger.info("Description validation " + description);
        if(description == null || description.equals("")) {
            return true;
        }else {
            return Pattern.matches(DESCRIPTION_PATTERN, description);
        }
    }

    private boolean isInputValid(String input, String regex){
        logger.info("Field validation " + input);
        if(input == null){
            return false;
        }else {
            return Pattern.matches(regex, input);
        }
    }

    private boolean isDeadlineValid(String deadline){
        if(deadline == null || deadline.equals("")){
            return true;
        }else{
            return parseDate(deadline) != null;
        }
    }

    private Date parseDate(String stringDate){
        logger.info("String date = " + stringDate);
        if(stringDate == null || stringDate.equals("")){
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

    private boolean isTrue(String input){
        return input != null && (input.equals("on")||input.equals("true"));
    }

    private String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
