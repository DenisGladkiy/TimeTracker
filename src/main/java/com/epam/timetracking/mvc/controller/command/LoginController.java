package com.epam.timetracking.mvc.controller.command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Denis on 10.05.2018.
 */
@WebServlet(name="Login", urlPatterns={"/login"})
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter("command");
        CommandContainer container = new CommandContainer();
        String url = container.getCommand(command).execute(request, response);
        response.sendRedirect(url);
    }
}
