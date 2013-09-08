<%--
  Created by IntelliJ IDEA.
  User: frankcaron
  Date: 8/13/13
  Time: 1:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<html>
  <head>
    <title>Stormpath - Sample Shiro-integrated Java App</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/style/style.css" />
  </head>
  <body>
        <div class="pageContainer">
            <div class="logo"><img src="${pageContext.request.contextPath}/static/images/stormpath.jpg" /></div>
            <% if (request.getParameter("session") != null) { if (request.getParameter("session").equals("false")) { %>
                 <div class="errorBox">Your log in request failed. Check your credentials, and try again.</div>
            <% } else if (request.getParameter("session").equals("expired")) { %>
                <div class="errorBox">Your session has expired. Check your credentials, and try again.</div>
            <% } } %>
            <form class="loginForm" method="post" action="LoginProcessorServlet">
                <table>
                    <tr>
                        <td colspan="2">Welcome to the simple framework-less Java application. Please log in to continue.</td>
                    </tr>
                    <tr>
                        <td>Username: </td>
                        <td><input type="text" name="username" /></td>
                    </tr>
                    <tr>
                        <td>Password: </td>
                        <td><input type="password" name="credential" /></td>
                    </tr>
                    <tr>
                        <td colspan="2" class="loginForm-controls">
                            <input type="submit" name="submit" value="Log In" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="loginForm-controls">
                            <a href="register.jsp">Need an account?</a> | <a href="reset.jsp">Forgot your password?</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
  </body>
</html>