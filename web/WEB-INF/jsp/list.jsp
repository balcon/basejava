<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="ru.javaops.webapp.model.ContactType" %>
<%@ page import="ru.javaops.webapp.model.SectionType" %>
<t:page title="Список резюме">
    <%--@elvariable id="resumes" type="java.util.List"--%>
    <c:if test="${resumes.size()!=0}">
        <table class="table is-striped">
                <%--@elvariable id="list" type="java.util.List"--%>
            <c:forEach var="resume" items="${resumes}" varStatus="index">
                <%--@elvariable id="resume" type="ru.javaops.webapp.model.Resume"--%>
                <tr>
                    <th style="vertical-align: middle">${index.count}</th>
                    <td><a class="button is-white" href="resumes?uuid=${resume.uuid}">${resume.fullName}</a></td>
                    <td style="vertical-align: middle">${resume.getSection(SectionType.OBJECTIVE)}</td>
                    <td>
                        <c:set var="mail" value="${resume.getContact(ContactType.MAIL)}"/>
                        <a class="button is-white" href="mailto:${mail}">
                            <span class="icon"><i class="fa fa-envelope"></i></span>
                            <span>${mail}</span></a>
                    </td>
                    <td><a class="button is-primary" href="resumes?uuid=${resume.uuid}&action=edit">
                        <span class="icon"><i class="fa fa-pen-to-square"></i></span></a>
                    </td>
                    <td><a class="button is-danger" href="resumes?uuid=${resume.uuid}&action=delete">
                        <span class="icon"><i class="fa fa-trash-alt"></i></span></a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</t:page>
