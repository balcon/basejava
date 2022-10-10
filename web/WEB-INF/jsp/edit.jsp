<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javaops.webapp.model.ContactType" %>
<%@ page import="ru.javaops.webapp.model.SectionType" %>
<%@ page import="ru.javaops.webapp.model.Period" %>
<html>
<head>
    <%--@elvariable id="resume" type="ru.javaops.webapp.model.Resume"--%>
    <title>${resume.fullName}</title>
</head>
<body>
<a href="<c:url value="/resumes"/>">Назад</a> <br>
<form action="resumes" method="post">
    <input name="uuid" type="hidden" value="${resume.uuid}">
    <p><label>ФИО:<input name="fullName" type="text" size="40" value="${resume.fullName}" required></label></p>
    <table>
        <c:forEach var="cType" items="${ContactType.values()}">
            <%--@elvariable id="cType" type="ru.javaops.webapp.model.ContactType"--%>
            <tr>
                <td><b><label for="${cType.name()}">${cType.title}:</label></b></td>
                <td>
                    <input name="${cType.name()}" id="${cType.name()}"
                           type="text" size="40" value="${resume.getContact(cType)}">
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:forEach var="sType" items="${SectionType.values()}">
        <%--@elvariable id="sType" type="ru.javaops.webapp.model.SectionType"--%>
        <c:choose>
            <c:when test="${sType == SectionType.OBJECTIVE || sType == SectionType.PERSONAL}">
                <c:set var="textSection" value="${resume.getSection(sType)}"/>
                <%--@elvariable id="textSection" type="ru.javaops.webapp.model.TextSection"--%>
                <p>
                    <b><label for="${sType.name()}">${sType.title}</label></b><br>
                    <textarea name="${sType.name()}" id="${sType.name()}" rows="4"
                              cols="80">${textSection.text}</textarea>
                </p>
            </c:when>
            <c:when test="${sType == SectionType.ACHIEVEMENT || sType == SectionType.QUALIFICATION}">
                <c:set var="listTextSection" value="${resume.getSection(sType)}"/>
                <%--@elvariable id="listTextSection" type="ru.javaops.webapp.model.ListTextSection"--%>
                <p>
                    <b><label for="${sType.name()}">${sType.title}</label></b><br>
                    <textarea name="${sType.name()}" id="${sType.name()}" rows="8" cols="80"
                    ><c:forEach var="text" items="${listTextSection.textList}"
                    >${text}&#13;&#10;</c:forEach></textarea>
                </p>
            </c:when>
            <c:when test="${sType == SectionType.EXPERIENCE || sType == SectionType.EDUCATION}">
                <h4>${sType.title}</h4>
                <c:set var="orgSection" value="${resume.getSection(sType)}"/>
                <%--@elvariable id="orgSection" type="ru.javaops.webapp.model.OrganizationSection"--%>
                <c:forEach var="organization" items="${orgSection.content}">
                    <table>
                        <tr>
                            <td><b><label for="${sType.name()}_name">Организация</label></b></td>
                            <td>
                                <input name="${sType.name()}_name" id="${sType.name()}_name"
                                       type="text" size="50" value="${organization.name}">
                            </td>
                        </tr>
                        <tr>
                            <td><b><label for="${sType.name()}_homepage">Домашняя страница</label></b></td>
                            <td>
                                <input name="${sType.name()}_homepage" id="${sType.name()}_homepage"
                                       type="text" size="50" value="${organization.homepage}">
                            </td>
                        </tr>
                        <c:forEach var="period" items="${organization.periods}">
                            <c:set var="titleLabel" value="Должность"/>
                            <c:if test="${sType==SectionType.EDUCATION}">
                                <c:set var="titleLabel" value="Специальность"/>
                            </c:if>
                            <tr>
                                <td><b><label for="${sType.name()}_title">${titleLabel}</label></b></td>
                                <td>
                                    <input name="${sType.name()}_title" id="${sType.name()}_title"
                                           type="text" size="50" value="${period.title}">
                                </td>
                            </tr>
                            <c:if test="${sType==SectionType.EXPERIENCE}">
                                <tr>
                                    <td><b><label for="${sType.name()}_description">Обязанности</label></b></td>
                                    <td>
                                    <textarea name="${sType.name()}_description" id="${sType.name()}_description"
                                              rows="4" cols="47">${period.description}</textarea>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td><b><label for="${sType.name()}_startDate">Начало</label></b></td>
                                <td>
                                    <input name="${sType.name()}_startDate" id="${sType.name()}_startDate"
                                           placeholder="yyyy-mm-dd" type="text" size="50" value="${period.startDate}">
                                </td>
                            </tr>
                            <tr>
                                <td><b><label for="${sType.name()}_endDate">Начало</label></b></td>
                                <td>
                                    <c:set var="endDate" value="${period.endDate}"/>
                                    <c:if test="${endDate.equals(Period.NOW)}">
                                        <c:set var="endDate" value=""/>
                                    </c:if>
                                    <input name="${sType.name()}_endDate" id="${sType.name()}_endDate"
                                           placeholder="yyyy-mm-dd" type="text" size="50" value="${endDate}">
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:forEach>
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