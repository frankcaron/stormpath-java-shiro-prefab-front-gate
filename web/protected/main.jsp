<%--
  Created by IntelliJ IDEA.
  User: frankcaron
  Date: 8/13/13
  Time: 1:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" session="true" %>
<%@ page import="com.stormpath.sdk.account.*" %>

<html>
<head>
    <title>Stormpath - Sample Framework-free Java App</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/style/style.css" />
</head>
<body>
    <div class="pageContainer">
        <div class="logo"><img src="${pageContext.request.contextPath}/static/images/stormpath.jpg" /></div>
        <%
            Account currentUser = (Account)session.getAttribute("Account");
        %>

        <form class="loginForm" method="post">
            <table>
                <tr>
                    <td colspan="2">Welcome to the simple framework-less Java application. Your user information follows.</td>
                </tr>
                <%
                   if (session.getAttribute("isAdmin").equals(true)) {
                %>
                <tr>
                    <td colspan="2">Only admins will see this.</td>
                </tr>
                <% } %>
                <tr>
                    <td>Username: </td>
                    <td><input type="text" name="username" value="<%= currentUser.getUsername() %>" disabled /></td>
                </tr>
                <tr>
                    <td>First Name: </td>
                    <td><input type="text" name="firstName" value="<%= currentUser.getGivenName() %>" disabled /></td>
                </tr>
                <tr>
                    <td>Last Name: </td>
                    <td><input type="text" name="lastName" value="<%= currentUser.getSurname() %>" disabled /></td>
                </tr>
                <tr>
                    <td>Email: </td>
                    <td><input type="text" name="email" value="<%= currentUser.getEmail() %>" disabled /></td>
                </tr>
                <tr>
                    <td colspan="2" class="loginForm-controls">
                        <a href="${pageContext.request.contextPath}/protected/edit.jsp">Edit Profile</a> | <a href="${pageContext.request.contextPath}/protected/logout.jsp">Log Out</a>
                    </td>
                </tr>
            </table>
        </form>


    </div>

</body>
</html>