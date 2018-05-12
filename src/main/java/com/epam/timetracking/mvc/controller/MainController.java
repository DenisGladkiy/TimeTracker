package com.epam.timetracking.mvc.controller;

import com.epam.timetracking.mvc.controller.command.CommandContainer;
import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Denis on 29.04.2018.
 */
@WebServlet(name="Time-Tracking", urlPatterns={"/pages/timetracking"})
public class MainController extends HttpServlet {
    private static Logger logger = Logger.getLogger(MainController.class);

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //test useBean
        logger.debug("Use bean = " + request.getSession(false).getAttribute("list"));
        //finish test
        String command = request.getParameter("command");
        logger.debug("Controller command = " + command);
        CommandContainer container = new CommandContainer();
        String url = container.getCommand(command).execute(request, response);
        response.sendRedirect(url);
    }
}
