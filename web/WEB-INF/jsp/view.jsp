<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="ru.javaops.webapp.model.SectionType" %>
<%@ page import="ru.javaops.webapp.model.Period" %>
<%@ page import="ru.javaops.webapp.web.Hyperlink" %>
<%--@elvariable id="resume" type="ru.javaops.webapp.model.Resume"--%>
<t:page title="${resume.fullName}">
    <div class="column is-8">
        <section class="hero is-primary">
            <div class="hero-body">
                <p class="title">
                        ${resume.fullName}
                    <a class="button is-primary" href="resumes?uuid=${resume.uuid}&action=edit">
                        <span class="icon"><i class="fa fa-pen-to-square"></i></span></a>
                </p>
            </div>
        </section>
        <br>
        <!--- ---CONTACTS--- --->
        <c:forEach var="contact" items="${resume.contacts}">
            <c:if test="${contact.value!=null}">
                <p>
                    <a class="button is-white" href="${Hyperlink.of(contact.key, contact.value)}">
                <span class="icon">
                    <i class="${Hyperlink.iconOf(contact.key)}"></i>
                </span>
                        <span>${contact.value}</span>
                    </a>
                </p>
            </c:if>
        </c:forEach>
        <br>
        <!-- ---SECTIONS--- -->
        <c:forEach var="section" items="${resume.sections}">
            <c:set var="type" value="${section.key}"/>
            <c:choose>
                <c:when test="${type == SectionType.OBJECTIVE || type == SectionType.PERSONAL}">
                    <c:set var="textSection" value="${section.value}"/>
                    <%--@elvariable id="textSection" type="ru.javaops.webapp.model.TextSection"--%>
                    <article class="message is-primary">
                        <div class="message-header">
                            <p>${type.title}</p>
                        </div>
                        <div class="message-body">${textSection.text}</div>
                    </article>
                </c:when>
                <c:when test="${type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATION}">
                    <c:set var="listTextSection" value="${section.value}"/>
                    <%--@elvariable id="listTextSection" type="ru.javaops.webapp.model.ListTextSection"--%>
                    <article class="message is-primary">
                        <div class="message-header">
                            <p>${type.title}</p>
                        </div>
                        <div class="message-body">
                            <div class="content">
                                <ul>
                                    <c:forEach var="line" items="${listTextSection.textList}">
                                        <li>${line}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </article>
                </c:when>
                <c:when test="${type == SectionType.EXPERIENCE || type == SectionType.EDUCATION}">
                    <c:set var="organizationSection" value="${section.value}"/>
                    <%--@elvariable id="organizationSection" type="ru.javaops.webapp.model.OrganizationSection"--%>
                    <article class="message is-primary">
                        <div class="message-header">
                            <p>${type.title}</p>
                        </div>
                        <div class="message-body">
                            <c:forEach var="organization" items="${organizationSection.content}">
                                <article class="panel is-primary">
                                    <p class="panel-heading">
                                        <a class="button is-text title is-6" href="${organization.homepage}">
                                            ${organization.name}
                                        </a>
                                    </p>
                                    <c:forEach var="period" items="${organization.periods}">
                                        <fmt:parseDate value="${period.startDate}" pattern="yyyy-MM-dd"
                                                       var="startDate"/>
                                        <fmt:formatDate value="${startDate}" var="start" pattern="MM.yyyy"/>
                                        <fmt:parseDate value="${period.endDate}" pattern="yyyy-MM-dd"
                                                       var="endDate"/>
                                        <fmt:formatDate value="${endDate}" var="end" pattern="MM.yyyy"/>
                                        <c:if test="${period.endDate.equals(Period.NOW)}">
                                            <c:set var="end" value="Сейчас"/>
                                        </c:if>
                                        <div class="panel-block">
                                            <div class="column is-3">
                                                    ${start} - ${end}
                                            </div>
                                            <div class="column">
                                                <p class="title is-6">${period.title}</p>
                                                <c:if test="${period.description.length()!=0}">
                                                    ${period.description}
                                                </c:if>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </article>
                            </c:forEach>
                        </div>
                    </article>
                </c:when>
            </c:choose>
        </c:forEach>
    </div>
</t:page>
