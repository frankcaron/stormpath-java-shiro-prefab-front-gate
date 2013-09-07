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

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginProcessorServlet extends HttpServlet {

    private APICommunicator loginHelper = APICommunicator.getInstance();

    public void doPost (HttpServletRequest req,
                       HttpServletResponse res)
            throws ServletException, IOException
    {

        //Make auth request
        Account retrievedAccount = this.loginHelper.processLogin(req.getParameter("username"), req.getParameter("credential"));

        //Validate auth and redirect as appropriate
        if (retrievedAccount != null) {
            //Store the account in the HTTP session
            HttpSession session = req.getSession();
            session.setAttribute("Account", retrievedAccount);
            session.setAttribute("AccountHref", retrievedAccount.getHref());

            //Redirect to site page
            String site = "/protected/main.jsp";
            res.setStatus(res.SC_ACCEPTED);
            res.sendRedirect(site);
        } else {
            //Redirect back to log in page and note the error
            String site = "/index.jsp?session=false";
            res.setStatus(res.SC_UNAUTHORIZED);
            res.sendRedirect(site);
        }
    }
}