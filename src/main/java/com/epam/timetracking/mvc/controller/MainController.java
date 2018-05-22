package com.epam.timetracking.mvc.controller;

import com.epam.timetracking.mvc.controller.command.CommandContainer;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by Denis on 29.04.2018.
 * Controller Class
 * In Post request it gets a command from jsp View
 * and calls corresponding executor.
 * Then it redirects result to the view
 */
@WebServlet(name="Time-Tracking", urlPatterns={"/pages/timetracking"})
public class MainController extends HttpServlet {
    private static Logger logger = Logger.getLogger(MainController.class);

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String command = request.getParameter("command");
        logger.info("Main controller command = " + command);
        CommandContainer container = new CommandContainer();
        String url = container.getCommand(command).execute(request, response);
        response.sendRedirect(url);
    }
}
