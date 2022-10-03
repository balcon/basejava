<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javaops.webapp.model.ContactType" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table border="1">
    <tr>
        <th>ФИО</th>
        <th>${ContactType.MAIL.title}</th>
        <th>${ContactType.PHONE.title}</th>
        <th></th>
        <th></th>
    </tr>
    <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
    <c:forEach var="resume" items="${resumes}">
        <jsp:useBean id="resume" type="ru.javaops.webapp.model.Resume"/>
        <tr>
            <td><a href=<c:url value="resumes?uuid=${resume.uuid}"/>>${resume.fullName}</a></td>
            <td>${resume.getContact(ContactType.MAIL)}</td>
            <td>${resume.getContact(ContactType.PHONE)}</td>
            <td><a href=<c:url value="resumes?uuid=${resume.uuid}&action=edit"/>>Edit</a></td>
            <td><a href=<c:url value="resumes?uuid=${resume.uuid}&action=delete"/>>Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
