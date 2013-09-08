package com.stormpath.shirojavafrontgate.controllers.Login;

/**
 * Created with IntelliJ IDEA.
 * User: frankcaron
 * Date: 8/15/13
 * Time: 5:01 PM
 * To change this template use File | Settings | File Templates.
 */

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import org.apache.shiro.subject.*;
import org.apache.shiro.SecurityUtils;

public class AuthFilter implements Filter {

    FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        servletResponse.setContentType("text/html");
        String path = ((HttpServletRequest) servletRequest).getRequestURI();

        //If not in a protected page, process without interrupting
        if (path.equals("/index.jsp") || path.equals("/index.jsp?session=false") || path.equals("/LoginProcessorServlet")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {

            //Get the current user
            Subject currentUser = SecurityUtils.getSubject();

            //Check for the current Shiro subject for auth.
            if(!currentUser.isAuthenticated()) {
                //No session was found. Redirect to login page.
                ((HttpServletResponse)(servletResponse)).sendRedirect("/index.jsp?session=expired");

            //Clear the current shiro subject and redirect if logging out
            } else if (path.equals("/protected/logout.jsp")) {
                if (currentUser != null) {
                    if (currentUser.isAuthenticated()) {
                        currentUser.logout();
                    }
                }
                ((HttpServletResponse)(servletResponse)).sendRedirect("/index.jsp");

            //All is well. Continue as per normal.
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }
}
