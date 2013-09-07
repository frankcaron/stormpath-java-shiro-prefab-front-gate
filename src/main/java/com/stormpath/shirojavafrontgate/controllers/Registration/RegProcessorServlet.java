package com.stormpath.shirojavafrontgate.controllers.Registration;

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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegProcessorServlet extends HttpServlet {

    private APICommunicator regHelper = APICommunicator.getInstance();

    public void doPost (HttpServletRequest req,
                        HttpServletResponse res)
            throws ServletException, IOException
    {
        //Make registration request
        String accountCreated = this.regHelper.createAccount(req.getParameterMap());

        //Pass along result
        String site = "/register.jsp?registration=" + accountCreated;
        res.setStatus(res.SC_ACCEPTED);
        res.sendRedirect(site);
    }
}