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

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.client.*;
import com.stormpath.sdk.ds.*;
import com.stormpath.shirojavafrontgate.controllers.APICommunicator.APICommunicator;
import com.stormpath.shirojavafrontgate.controllers.APICommunicator.ShiroCommunicator;
import org.apache.shiro.subject.Subject;



import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginProcessorServlet extends HttpServlet {

    private ShiroCommunicator loginHelper = ShiroCommunicator.getInstance();
    private APICommunicator retrievalHelper = APICommunicator.getInstance();

    public void doPost (HttpServletRequest req,
                       HttpServletResponse res)
            throws ServletException, IOException
    {

        //Make auth request
        Subject retrievedAccount = this.loginHelper.processLogin(req.getParameter("username"), req.getParameter("credential"));

        //Validate auth and redirect as appropriate
        if (retrievedAccount != null) {
            //Grab the session
            HttpSession session = req.getSession();

            //Retrieve user object
            //Store the account in the HTTP session
            String href = (String)retrievedAccount.getPrincipal();
            session.setAttribute("Account", retrievalHelper.getAccount(href));

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