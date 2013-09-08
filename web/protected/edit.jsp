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
    <title>Stormpath - Sample Shiro-integrated Java App</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/style/style.css" />
</head>
<body>
<div class="pageContainer">
    <div class="logo"><img src="${pageContext.request.contextPath}/static/images/stormpath.jpg" /></div>

    <%
        Account currentUser = (Account)session.getAttribute("Account");
    %>

    <form class="loginForm" method="post" action="/EditProcessorServlet">
        <table>
            <tr>
                <td colspan="2">Make changes to your profile and click the "Submit" button to update your account.</td>
            </tr>
            <tr>
                <td>Username: </td>
                <td><input type="text" name="username" value="<%= currentUser.getUsername() %>" /></td>
            </tr>
            <tr>
                <td>First Name: </td>
                <td><input type="text" name="firstName" value="<%= currentUser.getGivenName() %>" /></td>
            </tr>
            <tr>
                <td>Last Name: </td>
                <td><input type="text" name="lastName" value="<%= currentUser.getSurname() %>" /></td>
            </tr>
            <tr>
                <td>Email: </td>
                <td><input type="text" name="email" value="<%= currentUser.getEmail() %>" /></td>
            </tr>
            <tr>
                <td colspan="2" class="loginForm-controls">
                    <input type="submit" name="submit" value="Save" />
                    <input type="button" name="cancel" value="Cancel" onclick="window.location='/protected/main.jsp'" />
                </td>
            </tr>
        </table>
    </form>


</div>

</body>
</html>