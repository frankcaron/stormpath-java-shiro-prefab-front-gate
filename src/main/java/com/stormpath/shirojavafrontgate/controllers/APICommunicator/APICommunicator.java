package com.stormpath.shirojavafrontgate.controllers.APICommunicator;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.Applications;
import com.stormpath.sdk.application.CreateApplicationRequest;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.directory.*;
import com.stormpath.sdk.ds.DataStore;
import com.stormpath.sdk.resource.ResourceException;
import com.stormpath.sdk.tenant.Tenant;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: frankcaron
 * Date: 8/13/13
 * Time: 2:08 PM
 *
 * APICommunicator
 *
 * This helper class validates form input and interfaces with the Shiro/Stormpath impl.
 *
 */

public class APICommunicator {

    //Set the Singleton instance
    private static final APICommunicator instance = new APICommunicator();

    //Config Vars
    private String apiKey;
    private String applicationURL;
    private String directoryURL;
    public String adminGroupURL;

    //Stormpath Vars
    private Client client;
    private DataStore dataStore;
    private Tenant tenant;
    private Application application;
    private Directory directory;

    //Init
    private APICommunicator() {
        //Read in the properties file
        Properties prop = new Properties();

        try {
            //load a properties file
            prop.load(APICommunicator.class.getClassLoader().getResourceAsStream("/application.properties"));
            apiKey = prop.getProperty("apiKey");
            applicationURL = prop.getProperty("applicationURL");
            directoryURL = prop.getProperty("directoryURL");
            adminGroupURL = prop.getProperty("adminGroupURL");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //Construct a client
        this.client = new ClientBuilder().setApiKeyFileLocation(this.apiKey).build();

        //Instatiate tenant, datastore, and application objects
        this.dataStore = this.client.getDataStore();
        this.tenant = this.client.getCurrentTenant();
        this.application = this.dataStore.getResource(this.applicationURL, Application.class);
        this.directory = this.client.getDataStore().getResource(this.directoryURL, Directory.class);
    }

    //Public constructor
    public static APICommunicator getInstance() {
        return instance;
    }

    //Return the application object when necessary
    public Application getApplication() {
        return this.application;
    }

    //Function to create a new application if one doesn't
    //already exist. Purely demonstrative; not used.
    public Application createApplication () {

        Application newApplication = this.client.getDataStore().instantiate(Application.class);
        newApplication.setName("Application Name");
        newApplication.setDescription("Application Description");

        CreateApplicationRequest myRequest = Applications.newCreateRequestFor(newApplication).createDirectory().build();
        this.tenant.createApplication(myRequest);

        return null;

    }

    //Helper function to authenticate an account
    public Account processLogin(String username, String password) {

        //Request authentication
        try {
            AuthenticationRequest request = new UsernamePasswordRequest(username,password);
            AuthenticationResult result = this.application.authenticateAccount(request);
            Account myAccount = result.getAccount();

            //Return retrieved account
            return result.getAccount();
        //Catch and report errors
        } catch (ResourceException name) {
            System.out.println("Auth error: " + name.getDeveloperMessage());
            return null;
        }

    }

    //Helper function to create an account
    public String createAccount( Map params) {

        //Compose account object
        Account account = this.client.getDataStore().instantiate(Account.class);
        account.setGivenName(((String [])params.get("firstName"))[0]);
        account.setSurname(((String [])params.get("lastName"))[0]);
        account.setUsername(((String [])params.get("username"))[0]);
        account.setEmail(((String [])params.get("email"))[0]);
        account.setPassword(((String [])params.get("credential"))[0]);

        //Eddit account
        try {
            System.out.println("Creating account");
            directory.createAccount(account);
            return "true";
        } catch (ResourceException name) {
            return name.getMessage();
        }

    }

    public Account editAccount(String href, Map params) {

        Account account = this.client.getDataStore().getResource(href, Account.class);

        //Update the account object
        account.setGivenName(((String [])params.get("firstName"))[0]);
        account.setSurname(((String [])params.get("lastName"))[0]);
        account.setUsername(((String [])params.get("username"))[0]);
        account.setEmail(((String [])params.get("email"))[0]);

        //Register account
        try {
            System.out.println("Saving account");
            account.save();
            return account;
        } catch (ResourceException name) {
            System.out.println("Error saving edits: " + name.getDeveloperMessage());
            return null;
        }
    }

    //Helper function to retrieve an account
    public Account getAccount(String href) {
        Account account = this.dataStore.getResource(href, Account.class);
        return account;
    }
}
