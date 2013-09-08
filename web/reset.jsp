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
    <% if (request.getParameter("email") != null) { if (request.getParameter("email").equals("false")) { %>
        <div class="errorBox">You must provide a valid email.</div>
    <% } else if (request.getParameter("email").equals("error")) { %>
        <div class="errorBox">An account with that email could not be found.</div>
    <% } else if (request.getParameter("email").equals("true")) { %>
        <div class="successBox">An email has been sent to the account specified.</div>
    <% } } %>
    <form class="loginForm" method="post" action="ResetProcessorServlet">
        <table>
            <tr>
                <td colspan="2">In order to reset your password, please provide us with a valid email address. Instructions on how to reset your password will be sent to the address provided.</td>
            </tr>
            <tr>
                <td>Email: </td>
                <td><input type="text" name="email" /></td>
            </tr>
            <tr>
                <td colspan="2" class="loginForm-controls">
                    <input type="submit" name="submit" value="Reset Password" />
                </td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>