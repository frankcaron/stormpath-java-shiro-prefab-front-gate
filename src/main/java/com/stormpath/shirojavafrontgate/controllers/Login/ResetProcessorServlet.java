package com.stormpath.shirojavafrontgate.controllers.Login;

/**
 * Created with IntelliJ IDEA.
 * User: frankcaron
 * Date: 8/13/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 *
 * LoginProcessorServlet
 *
 * This Servlet handles the form post and utilizes our authentication and helper classes to process auth.
 */

import com.stormpath.shirojavafrontgate.controllers.APICommunicator.APICommunicator;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.resource.ResourceException;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ResetProcessorServlet extends HttpServlet {

    private APICommunicator resetHelper = APICommunicator.getInstance();

    public void doPost (HttpServletRequest req,
                        HttpServletResponse res)
            throws ServletException, IOException
    {
        // --------
        // Handle email sending
        // -------

        // If a valid email address is provided
        if (req.getParameter("email") != null && req.getParameter("sptoken") == null) {
            try {
                //Store the account in the HTTP session
                this.resetHelper.getApplication().sendPasswordResetEmail(req.getParameter("email"));

                //Redirect to reset page and note success
                String site = "/reset.jsp?email=true";
                res.setStatus(res.SC_ACCEPTED);
                res.sendRedirect(site);
            } catch (Exception e) {
                //Redirect back to reset page and note the error
                String site = "/reset.jsp?email=error";
                res.setStatus(res.SC_UNAUTHORIZED);
                res.sendRedirect(site);
            }
        } else if (req.getParameter("email") == null && req.getParameter("sptoken") == null) {
            //Redirect back to email reset page
            String site = "/reset.jsp?email=false";
            res.setStatus(res.SC_UNAUTHORIZED);
            res.sendRedirect(site);
        }

        // --------
        // Handle password setting
        // -------

        // If a new password is provided
        if (req.getParameter("password") != null) {
            if (req.getParameter("password").equals(req.getParameter("password_confirm")) && !req.getParameter("password").equals("")) {
                try {
                    //Store the account in the HTTP session
                    Account account = this.resetHelper.getApplication().verifyPasswordResetToken(req.getParameter("sptoken"));
                    account.setPassword(req.getParameter("password"));
                    account.save();

                    //Redirect to reset page and note success
                    String site = "/set.jsp?password=true";
                    res.setStatus(res.SC_ACCEPTED);
                    res.sendRedirect(site);
                } catch (ResourceException name) {
                    //Redirect back to reset page and note the error
                    String site = "/set.jsp?sptoken=" + req.getParameter("sptoken") + "&error=" + name.getMessage();
                    res.setStatus(res.SC_UNAUTHORIZED);
                    res.sendRedirect(site);
                }
            } else {
                //Redirect back to email reset page
                String site = "/set.jsp?sptoken=" + req.getParameter("sptoken") + "&password=false";
                res.setStatus(res.SC_UNAUTHORIZED);
                res.sendRedirect(site);
            }
        }
    }
}