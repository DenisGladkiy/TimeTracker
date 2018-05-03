package com.epam.timetracking.utils;

import com.epam.timetracking.mvc.model.entity.Activity;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        String accept = request.getParameter("accept");
        String remove = request.getParameter("remove");
        String complete = request.getParameter("complete");
        logger.debug("Params from request = " + id + ", " + name + ", " + description + "," + creationDate + "," + deadline + ", " +
                    time + ", " + user + ", " + accept + ", " + remove);
        activity.setDescription(description);
        activity.setDeadLine(parseDate(deadline));
        activity.setCreationDate(parseDate(creationDate));
        if(time != null) {
            activity.setTime(new Duration(Double.valueOf(time) * 1000));
        }
        activity.setAddRequest(Boolean.getBoolean(accept));
        activity.setRemoveRequest(Boolean.getBoolean(remove));
        activity.setCompleted(Boolean.getBoolean(complete));
        if(user != null && !user.equals("")) {
            activity.setUserId(Integer.valueOf(user));
        }
        logger.debug("Activity from helper = " + activity);
        return activity;
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
