package com.epam.timetracking.utils;

import com.epam.timetracking.mvc.model.entity.Activity;
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
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        if(name.equals("")){
            return null;
        }
        String deadline = request.getParameter("deadLine");
        String user = request.getParameter("userId");
        Activity activity = new Activity();
        activity.setName(name);
        activity.setDescription(description);
        activity.setDeadLine(parseDate(deadline));
        if(user != null) {
            activity.setUserId(Integer.valueOf(user));
        }
        logger.debug("Date from activity = " + activity.getDeadLine());
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
