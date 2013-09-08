Stormpath Shiro Prefab Front Gate
===============================

A Shiro-integrated Java implementation of Stormpath's Java SDK within a basic user-centric web application. This prefabricated front gate can easily be plugged in to your existing web application to add user auth and management.

### Prelude

This demonstrative implementation has been written as a starting point for any Java web developers who are looking to implement [Stormpath](http://www.stormpath.com) with [Apache Shiro](http://shiro.apache.org/download.html). 

The following capabilities have been implemented:

* Account creation
* Authentication
* Account editing
* Password reset
* Basic Group Check

### Assumptions

This readme assumes that you have already set up a developer account with Stormpath and know how to retrieve your API key and your application URL. If you don't know how to do this, refer to [Stormpath's Java Quickstart Guide](http://www.stormpath.com/docs/java/quickstart).

### Set up Instructions

1. Clone the repository to your local machine.
2. Navigate to the cloned directory via the command line.
3. Run `maven install` to install the dependencies.
4. Set the variables in the included "application.properties" file to match your environment as appropriate:

        apiKey = /path/to/yourApiKey
        applicationURL = http://fullURLtoyourStormpathApplication
        directoryURL = http://fullURLtoyourStormpathDirectory
        adminGroupURL = http://fullURLtoyourStormpathAdminGroupWithinADirectory

5. Configure the password reset page on your directory within the [Stormpath API Console](http://api.stormpath.com) to use the following URL for its Password Reset workflow (Directory > Workflows > Password Reset show link):

		localhost:8080/reset.jsp

7. Run the app locally by clicking the "build and run" button in your IDE. The generated WAR must be deployed against the appropriate java servlet container (e.g., Tomcat)

### Dependencies

This impl relies on the following, which can be installed via maven:

* [Stormpath Java SDK](https://github.com/stormpath/stormpath-sdk-java)
* javax.servlet.*
* [Shiro Core](http://shiro.apache.org/download.html)
* [Shiro Web](http://shiro.apache.org/download.html)

### Design

The application's design relies on plain old Java Servlets and Filters. The project's web.xml file maps out HTTP requests to the various controllers.

The `APICommunicator` controller is a shared resource for all of the other controllers. It is the singleton responsible for handling all of the instantiation and resource management of Stormpath SDK objects, most importantly the client. It also reads from the external properties file.

The `ShiroCommunicator` is another shared resource for all the other controllers. It is the singleton responsible for handling all interaction with Shiro — namely authentication and authorization.

The rest of the controllers are clearly demarcated: 

* `LoginProcessorServlet` deals with log in and session requests
* `RegProcessorServlet` deals with account creation
* `EditProcessorServlet` deals with account updates
* `ResetProcessorServlet` deals with password resets (both triggering the email and setting the new pass)

Lastly, for security, the `AuthFilter` has been implemeted to ensure auth for protected resources. This filter is used before all calls to /protected/*. 

### Disclosure

Code provided as-is. No warranty, not guaranteed by anyone for anything other than being an example.
