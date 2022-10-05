<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javaops.webapp.model.ContactType" %>
<%@ page import="ru.javaops.webapp.model.SectionType" %>
<html>
<head>
    <%--@elvariable id="resume" type="ru.javaops.webapp.model.Resume"--%>
    <title>${resume.fullName}</title>
</head>
<body>
<a href="<c:url value="/resumes"/>">Назад</a> <br>
<form action="resumes" method="post">
    <input name="uuid" type="hidden" value="${resume.uuid}">
    <p><label>ФИО:<input name="fullName" type="text" value="${resume.fullName}" required></label></p>
    <table>
        <c:forEach var="cType" items="${ContactType.values()}">
            <%--@elvariable id="cType" type="ru.javaops.webapp.model.ContactType"--%>
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
        <%--@elvariable id="sType" type="ru.javaops.webapp.model.SectionType"--%>
        <c:choose>
            <c:when test="${sType.equals(SectionType.OBJECTIVE)||sType.equals(SectionType.PERSONAL)}">
                <c:set var="textSection" value="${resume.getSection(sType)}"/>
                <%--@elvariable id="textSection" type="ru.javaops.webapp.model.TextSection"--%>
                <p>
                    <b><label for="${sType.name()}">${sType.title}</label></b><br>
                    <textarea name="${sType.name()}" id="${sType.name()}" rows="4"
                              cols="80">${textSection.text}</textarea>
                </p>
            </c:when>
            <c:when test="${sType.equals(SectionType.ACHIEVEMENT)||sType.equals(SectionType.QUALIFICATION)}">
                <c:set var="listTextSection" value="${resume.getSection(sType)}"/>
                <%--@elvariable id="listTextSection" type="ru.javaops.webapp.model.ListTextSection"--%>
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
        <%--        TODO --%>
        <%--        <button type="reset" onclick="window.location.hash='';">Отмена</button>--%>
    </p>
</form>
</body>
</html>
