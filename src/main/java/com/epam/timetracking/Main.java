package com.epam.timetracking;

import com.epam.timetracking.mvc.model.dao.AbstractDao;
import com.epam.timetracking.mvc.model.dao.DaoManager;
import com.epam.timetracking.mvc.model.entity.Activity;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static javax.swing.text.html.HTML.Tag.BR;

/**
 * Created by Denis on 29.04.2018.
 */
@WebServlet(name="Time-Tracking", urlPatterns={"/timetracking"})
public class Main extends HttpServlet {
    static Logger logger = Logger.getLogger(Main.class);
    DaoManager manager;

    public Main(){
        manager = new DaoManager();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        AbstractDao dao = manager.getDao("Activity");
        List<Activity> activities = dao.getAll();
        dao.closeConnection();
        logger.debug(activities);
        request.setAttribute("Activities", activities);
        request.setAttribute("Test", "Test JSTL");
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/test.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String deadline = request.getParameter("deadline");
        String user = request.getParameter("user");
        logger.debug("Do post params = " + name + ", " + description + ", " + deadline + ", " + user);
        Activity activity = new Activity();
        activity.setName(name);
        activity.setDescription(description);
        AbstractDao dao = manager.getDao("Activity");
        dao.insert(activity);
        dao.closeConnection();
    }


}
