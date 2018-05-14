package com.epam.timetracking.mvc.controller;

import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.mvc.model.entity.UserRoleEnum;
import com.epam.timetracking.utils.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Denis on 10.05.2018.
 * Filter Class allows access to users with role - USER
 */
@WebFilter("/userPages/*")
public class UserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        if(session != null){
            User user = (User) session.getAttribute("User");
            if(user != null && user.getRole().equals(UserRoleEnum.USER)){
                filterChain.doFilter(request, response);
            }else{response.sendRedirect(Constants.INDEX);}
        }else {
            response.sendRedirect(Constants.INDEX);
        }
    }

    @Override
    public void destroy() { }
}
