<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javaops.webapp.model.ContactType" %>
<jsp:useBean id="resume" scope="request" type="ru.javaops.webapp.model.Resume"/>
<html>
<head>
    <title>${resume.fullName}</title>
</head>
<body>
<a href="<c:url value="/resumes"/>">Назад</a> <br>
<form action="resumes" method="post">
    <input name="uuid" type="hidden" value="${resume.uuid}">
    <p><label>ФИО:<input name="fullName" type="text" width="40" value="${resume.fullName}"></label></p>
    <table>
        <c:forEach var="type" items="${ContactType.values()}">
            <jsp:useBean id="type" type="ru.javaops.webapp.model.ContactType"/>
            <tr>
                <td><b>${type.title}: </b></td>
                <td><input name="${type.name()}" type="text" width="40" value="${resume.getContact(type)}"></td>
            </tr>
        </c:forEach>
    </table>
    <button type="submit">Save</button>
</form>
</body>
</html>
