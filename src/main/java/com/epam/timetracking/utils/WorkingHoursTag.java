package com.epam.timetracking.utils;

import com.epam.timetracking.mvc.model.entity.Activity;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by Denis on 10.05.2018.
 */
public class WorkingHoursTag extends TagSupport {
    private Activity activity;

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