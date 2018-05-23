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
 * Created by Denis on 10.05.2018.
 * Controller Class that manages user login procedure
 */
@WebServlet(name="Login", urlPatterns={"/login"})
public class LoginController extends HttpServlet {
    private static Logger logger = Logger.getLogger(LoginController.class);
    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter("command");
        CommandContainer container = new CommandContainer();
        logger.info("Login controller command = " + command);
        String url = container.getCommand(command).execute(request, response);
        response.sendRedirect(url);
    }
}
