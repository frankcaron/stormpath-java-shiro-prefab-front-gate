package com.stormpath.shirojavafrontgate.controllers.APICommunicator;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.Factory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.*;

/**
 * Created with IntelliJ IDEA.
 * User: frankcaron
 * Date: 8/13/13
 * Time: 2:08 PM
 *
 * ShrioCommunicator
 *
 * This helper class validates form input and interfaces with the Shiro/Stormpath impl.
 *
 */

public class ShiroCommunicator {

    //Set the Singleton instance
    private static final ShiroCommunicator instance = new ShiroCommunicator();

    //Config Vars
    private String apiKey;
    private String applicationURL;
    private String directoryURL;

    //Account Vars
    private Subject currentUser = null;

    //Init
    private ShiroCommunicator() {

        //Read in the ini file and initalize Shiro
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

    }

    //Public constructor
    public static ShiroCommunicator getInstance() {
        return instance;
    }

    //Helper function to authenticate an account
    public Subject processLogin(String username, String password) {

        //Request authentication
        try {
            //Create login token using params
            UsernamePasswordToken token =
                    new UsernamePasswordToken( username, password );

            //With most of Shiro, you'll always want to make sure you're working with the currently executing user, referred to as the subject
            Subject currentUser = SecurityUtils.getSubject();

            //Authenticate the subject by passing
            //the user name and password token
            //into the login method
            currentUser.login(token);

            //Persist the current user
            this.setCurrentUser(currentUser);

            //Return the new user
            return currentUser;

        //Catch errors
        } catch ( UnknownAccountException uae ) {
            System.out.print(uae.getMessage());
            return null;
        } catch ( IncorrectCredentialsException ice ) {
            System.out.print(ice.getMessage());
            return null;
        } catch ( LockedAccountException lae ) {
            System.out.print(lae.getMessage());
            return null;
        } catch ( ExcessiveAttemptsException eae ) {
            System.out.print(eae.getMessage());
            return null;
        } catch ( AuthenticationException ae ) {
            System.out.print(ae.getMessage());
            return null;
        }
    }

    //Work with current subject
    public String getCurrentUserHref() {
        return (String)this.currentUser.getPrincipal();
    }
    public void setCurrentUser(Subject user) {
        this.currentUser = user;
    }

}
