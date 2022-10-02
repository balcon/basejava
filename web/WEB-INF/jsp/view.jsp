<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javaops.webapp.model.SectionType" %>
<%@ page import="ru.javaops.webapp.web.Hyperlink" %>
<jsp:useBean id="resume" scope="request" type="ru.javaops.webapp.model.Resume"/>
<html>
<head>
    <title>${resume.fullName}</title>
</head>
<body>
<a href="<c:url value="/resumes"/>">Назад</a> <br>
<h2>${resume.fullName}</h2>
<hr>
<c:forEach var="contact" items="${resume.contacts}">
    ${Hyperlink.of(contact)}<br>
</c:forEach>
<hr>
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
            <c:forEach var="organization" items="${organizationSection.content}">
                <h4><a href="${organization.homepage}">${organization.name}</a></h4>
                <c:forEach var="period" items="${organization.periods}">
                    ${period.startDate}-${period.endDate}<br>
                    <h5>${period.title}</h5>
                    ${period.description}
                </c:forEach>
            </c:forEach>
        </c:when>
    </c:choose>
</c:forEach>
</body>
</html>
