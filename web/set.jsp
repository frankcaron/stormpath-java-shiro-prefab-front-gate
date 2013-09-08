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
    <% if (request.getParameter("password") != null || request.getParameter("error") != null) {
        if (request.getParameter("password").equals("false")) { %>
    <div class="errorBox">Your passwords must match.</div>
    <% } else if (request.getParameter("error") != null) { %>
    <div class="errorBox"><%=request.getParameter("error")%></div>
    <% } else if (request.getParameter("password").equals("true")) { %>
    <div class="successBox">Your password has been reset. Please <a href="/index.jsp">log in</a>.</div>
    <% } } %>
    <form class="loginForm" method="post" action="ResetProcessorServlet">
        <table>
            <tr>
                <td colspan="2">Please enter a new password in order to complete the password reset process.</td>
            </tr>
            <tr>
                <td>Password: </td>
                <td>
                    <input type="password" name="password" />
                    <input type="hidden" name="sptoken" value="<%=request.getParameter("sptoken")%>" />
                </td>
            </tr>
            <tr>
                <td>Confirm Password: </td>
                <td>
                    <input type="password" name="password_confirm" />
                </td>
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