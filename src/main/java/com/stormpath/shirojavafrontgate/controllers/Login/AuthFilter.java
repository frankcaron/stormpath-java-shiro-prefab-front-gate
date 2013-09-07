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

        if (path.equals("/index.jsp") || path.equals("/index.jsp?session=false") || path.equals("/LoginProcessorServlet")) {
            //Proceed with normal rendering
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            //Make sure a session is valid, otherwise redier
            HttpSession session = ((HttpServletRequest) servletRequest).getSession();
            if(session.getAttribute("Account")==null) {
                //No session was found. Redirect to login page.
                ((HttpServletResponse)(servletResponse)).sendRedirect("/index.jsp?session=expired");
            } else if (path.equals("/protected/logout.jsp")) {
                session.invalidate();
                ((HttpServletResponse)(servletResponse)).sendRedirect("/index.jsp");
            } else {
                //Continue as per normal.
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }
}
