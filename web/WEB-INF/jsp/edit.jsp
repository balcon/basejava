<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javaops.webapp.model.ContactType" %>
<%@ page import="ru.javaops.webapp.model.SectionType" %>
<c:if test="${resume!=null}">
    <jsp:useBean id="resume" scope="request" type="ru.javaops.webapp.model.Resume"/>
</c:if>
<html>
<head>
    <title>${resume.fullName}</title>
</head>
<body>
<a href="<c:url value="/resumes"/>">Назад</a> <br>
<form action="resumes" method="post">
    <input name="uuid" type="hidden" value="${resume.uuid}">
    <p><label>ФИО:<input name="fullName" type="text" value="${resume.fullName}"></label></p>
    <table>
        <c:forEach var="cType" items="${ContactType.values()}">
            <jsp:useBean id="cType" type="ru.javaops.webapp.model.ContactType"/>
            <tr>
                <td><b><label for="${cType.name()}">${cType.title}:</label></b></td>
                <td>
                    <input name="${cType.name()}" id="${cType.name()}"
                           type="text" size="30" value="${resume.getContact(cType)}">
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:forEach var="sType" items="${SectionType.values()}">
        <jsp:useBean id="sType" type="ru.javaops.webapp.model.SectionType"/>
        <c:choose>
            <c:when test="${sType.equals(SectionType.OBJECTIVE)||sType.equals(SectionType.PERSONAL)}">
                <c:set var="textSection" value="${resume.getSection(sType)}"/>
                <c:if test="${textSection!=null}">
                    <jsp:useBean id="textSection" type="ru.javaops.webapp.model.TextSection"/>
                </c:if>
                <p>
                    <b><label for="${sType.name()}">${sType.title}</label></b><br>
                    <textarea name="${sType.name()}" id="${sType.name()}" rows="4"
                              cols="80">${textSection.text}</textarea>
                </p>
            </c:when>
            <c:when test="${sType.equals(SectionType.ACHIEVEMENT)||sType.equals(SectionType.QUALIFICATION)}">
                <c:set var="listTextSection" value="${resume.getSection(sType)}"/>
                <c:if test="${listTextSection!=null}">
                    <jsp:useBean id="listTextSection" type="ru.javaops.webapp.model.ListTextSection"/>
                </c:if>
                <p>
                    <b><label for="${sType.name()}">${sType.title}</label></b><br>
                    <textarea name="${sType.name()}" id="${sType.name()}" rows="8" cols="80"
                    ><c:forEach var="text" items="${listTextSection.textList}"
                    >${text}&#13;&#10;</c:forEach></textarea>
                </p>
            </c:when>
        </c:choose>
    </c:forEach>
    <p>
        <button type="submit">Сохранить</button>
        <button onclick="window.location.hash='';">Отмена</button>
    </p>
</form>
</body>
</html>
