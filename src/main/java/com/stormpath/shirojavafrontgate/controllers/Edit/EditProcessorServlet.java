package com.stormpath.shirojavafrontgate.controllers.Edit;

/**
 * Created with IntelliJ IDEA.
 * User: frankcaron
 * Date: 8/13/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 *
 * EditProcessorServlet
 *
 * This Servlet handles the form post and utilizes our authentication and helper classes to process auth.
 */

import com.stormpath.shirojavafrontgate.controllers.APICommunicator.APICommunicator;
import com.stormpath.sdk.account.Account;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class EditProcessorServlet extends HttpServlet {

    private APICommunicator editHelper = APICommunicator.getInstance();

    public void doPost (HttpServletRequest req,
                        HttpServletResponse res)
            throws ServletException, IOException
    {
        //Fetch the user's account URL
        HttpSession session = req.getSession();
        String href = (String)(session.getAttribute("AccountHref"));

        //Make edit request
        Account accountEdited = this.editHelper.editAccount(href, req.getParameterMap());

        //Validate auth and redirect as appropriate
        if (accountEdited != null) {
            //Replace session object
            session.setAttribute("Account", accountEdited);

            //Redirect to site page
            String site = "/protected/main.jsp";
            res.setStatus(res.SC_ACCEPTED);
            res.sendRedirect(site);
        } else {
            //Redirect back to log in page and note the error
            String site = "/protected/edit.jsp?edit=false";
            res.setStatus(res.SC_ACCEPTED);
            res.sendRedirect(site);
        }

    }
}