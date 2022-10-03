<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="ru.javaops.webapp.model.SectionType" %>
<%@ page import="ru.javaops.webapp.model.Period" %>
<%@ page import="ru.javaops.webapp.web.Hyperlink" %>
<jsp:useBean id="resume" scope="request" type="ru.javaops.webapp.model.Resume"/>
<html>
<head>
    <title>${resume.fullName}</title>
</head>
<body>
<a href="<c:url value="/resumes"/>">Назад</a>
<a href=<c:url value="resumes?uuid=${resume.uuid}&action=edit"/>>Edit</a><br>
<h2>${resume.fullName}</h2>
<hr>
<c:forEach var="contact" items="${resume.contacts}">
    <c:if test="${contact.value!=null}">
        ${Hyperlink.of(contact.key, contact.value)}<br>
    </c:if>
</c:forEach>
<c:forEach var="section" items="${resume.sections}">
    <c:set var="type" value="${section.key}"/>
    <c:choose>
        <c:when test="${type==SectionType.OBJECTIVE || type==SectionType.PERSONAL}">
            <h3>${type.title}</h3>
            ${section.value}
        </c:when>
        <c:when test="${type==SectionType.ACHIEVEMENT || type==SectionType.QUALIFICATION}">
            <c:set var="listTextSection" value="${section.value}"/>
            <jsp:useBean id="listTextSection" type="ru.javaops.webapp.model.ListTextSection"/>
            <h3>${type.title}</h3>
            <ul>
                <c:forEach var="line" items="${listTextSection.textList}">
                    <li>${line}</li>
                </c:forEach>
            </ul>
        </c:when>
        <c:when test="${type==SectionType.EXPERIENCE || type==SectionType.EDUCATION}">
            <c:set var="organizationSection" value="${section.value}"/>
            <jsp:useBean id="organizationSection" type="ru.javaops.webapp.model.OrganizationSection"/>
            <h3>${type.title}</h3>
            <table>
                <c:forEach var="organization" items="${organizationSection.content}">
                    <tr>
                        <td></td>
                        <td><a href="${organization.homepage}">${organization.name}</a></td>
                    </tr>
                    <c:forEach var="period" items="${organization.periods}">
                        <fmt:parseDate value="${period.startDate}" pattern="yyyy-MM-dd" var="startDate"/>
                        <fmt:formatDate value="${startDate}" var="start" pattern="MM.yyyy"/>
                        <fmt:parseDate value="${period.endDate}" pattern="yyyy-MM-dd" var="endDate"/>
                        <fmt:formatDate value="${endDate}" var="end" pattern="MM.yyyy"/>
                        <c:if test="${period.endDate.equals(Period.NOW)}">
                            <c:set var="end" value="Сейчас"/>
                        </c:if>
                        <tr>
                            <td>${start} - ${end}</td>
                            <td><b>${period.title}</b></td>
                        </tr>
                        <c:if test="${!period.description.isEmpty()}">
                            <tr>
                                <td></td>
                                <td>${period.description}<td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </table>
        </c:when>
    </c:choose>
</c:forEach>
</body>
</html>
