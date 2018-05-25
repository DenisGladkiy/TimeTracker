package com.denis.timetracking.utils;

import com.denis.timetracking.mvc.model.entity.Activity;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by Denis on 10.05.2018.
 * Class describes custom tag that converts working time
 * from milliseconds to hours
 */
public class WorkingHoursTag extends TagSupport {
    private Activity activity;

    /**
     * Set activity.
     *
     * @param activity the activity
     */
    public void setActivity(Activity activity){
        this.activity = activity;
    }

    @Override
    public int doStartTag() {
        try {
            pageContext.getOut().write(String.valueOf(activity.getHours()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
