package com.epam.timetracking.mvc.controller;

import com.epam.timetracking.mvc.controller.command.ActivityInsert;
import com.epam.timetracking.mvc.controller.command.CommandContainer;
import com.epam.timetracking.mvc.model.dao.AbstractDao;
import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.dao.DaoManager;
import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.utils.ControllerHelper;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Denis on 29.04.2018.
 */
@WebServlet(name="Time-Tracking", urlPatterns={"/timetracking"})
public class MainController extends HttpServlet {
    private static Logger logger = Logger.getLogger(MainController.class);
    private DaoManager manager;
    private ControllerHelper helper;


    public MainController(){
        manager = DaoManager.getInstance();
        helper = new ControllerHelper();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter("command");
        CommandContainer container = new CommandContainer();
        String url = container.getCommand(command).execute(request, response);
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
//            switch (command){
//                case "insert": new ActivityInsert().execute(request, response);
//                    break;
//                case "delete": deleteActivity(request, response);
//                    break;
//                case "select": selectActivity(request, response);
//                    break;
//                case "update": updateActivity(request, response);
//            }
    }

    private void insertActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Activity activity = helper.createActivityBean(request);
        logger.debug("Activity to insert = " + activity);
        AbstractDao dao = manager.getDao("ACTIVITY");
        dao.insert(activity);
        dao.closeConnection();
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }

    private void deleteActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Activity activity = helper.createActivityBean(request);
        AbstractDao dao = manager.getDao("ACTIVITY");
        dao.delete(activity);
        request.setAttribute("Activities", dao.getAll());
        dao.closeConnection();
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/activities.jsp");
        rd.forward(request, response);
    }

    private void selectActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        List<Activity> activities = null;
        logger.debug("Select = " + request.getParameter("select"));
        if(request.getParameter("select").equals("selectall")) {
            activities = dao.getAll();
        }else if(request.getAttribute("select").equals("selectuser")){
            activities = dao.getByUserId(Integer.valueOf((String)request.getAttribute("user")));
        }
        dao.closeConnection();
        logger.debug("List of activities = " + activities);
        request.setAttribute("Activities", activities);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/activities.jsp");
        rd.forward(request, response);
    }

    private void updateActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("id = " + request.getParameter("id"));
        logger.debug("name = " + request.getParameter("name"));
        logger.debug("description = " + request.getParameter("description"));
        ActivityDao dao = (ActivityDao) manager.getDao("ACTIVITY");
        ControllerHelper helper = new ControllerHelper();
        Activity activity = helper.createActivityBean(request);
        dao.update(activity);
        List<Activity> activities = dao.getAll();
        dao.closeConnection();
        request.setAttribute("Activities", activities);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/activities.jsp");
        rd.forward(request, response);
    }
}
