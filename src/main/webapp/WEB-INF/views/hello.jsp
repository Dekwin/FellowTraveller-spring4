<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%--
  Created by IntelliJ IDEA.
  User: igorkasyanenko
  Date: 11.12.16
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ROFL</title>
</head>
<body>
<h1>It's not an image! ROFL</h1>
<h4>You are a stupid guy!</h4>
<table>
    <tr>
        <td>NAME</td><td>Joining Date</td><td>Salary</td><td>SSN</td><td></td>
    </tr>
    <c:forEach items="${items}" var="employee">
        <tr>
            <td>${employee}</td>
            <td>${employee}</td>
            <td>${employee}</td>
            <td><a href="<c:url value='/edit-${employee}-employee' />">${employee}</a></td>
            <td><a href="<c:url value='/delete-${employee}-employee' />">delete</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
