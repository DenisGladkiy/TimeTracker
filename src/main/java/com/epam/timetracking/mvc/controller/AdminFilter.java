package com.epam.timetracking.mvc.controller;


import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.mvc.model.entity.UserRoleEnum;
import com.epam.timetracking.utils.Constants;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Denis on 09.05.2018.
 * Filter Class allows access to users with role - ADMIN
 */
@WebFilter(filterName = "AdminFilter")
public class AdminFilter implements Filter {
    private static Logger logger = Logger.getLogger(AdminFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        if(session != null){
            User user = (User) session.getAttribute("User");
            logger.debug("AdminFilter session user = " + user);
            if(user != null && user.getRole().equals(UserRoleEnum.ADMIN)){
                filterChain.doFilter(request, response);
            }else{response.sendRedirect(Constants.INDEX);}
        }else {
            response.sendRedirect(Constants.INDEX);
        }
    }

    @Override
    public void destroy() { }
}
