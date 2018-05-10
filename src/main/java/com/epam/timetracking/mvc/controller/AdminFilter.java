package com.epam.timetracking.mvc.controller;


import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.mvc.model.entity.UserRoleEnum;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Denis on 09.05.2018.
 */
@WebFilter(filterName = "AdminFilter")
public class AdminFilter implements Filter {
    private static Logger logger = Logger.getLogger(MainController.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("Filter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        if(session != null){
            logger.debug("Filter session");
            User user = (User) session.getAttribute("User");
            logger.debug("Filter session user = " + user);
            if(user != null && user.getRole().equals(UserRoleEnum.ADMIN)){
                logger.debug("Filter admin");
                filterChain.doFilter(request, response);
            }else{response.sendRedirect("/index.jsp");}
        }else {
            response.sendRedirect("/index.jsp");
        }
    }

    @Override
    public void destroy() {

    }
}
