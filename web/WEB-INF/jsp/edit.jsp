<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javaops.webapp.model.ContactType" %>
<%@ page import="ru.javaops.webapp.model.SectionType" %>
<%@ page import="ru.javaops.webapp.web.Hyperlink" %>
<%--@elvariable id="resume" type="ru.javaops.webapp.model.Resume"--%>
<c:set var="title" value="Новое резюме"/>
<c:if test="${resume!=null}">
    <c:set var="title" value="${resume.fullName}"/>
</c:if>

<t:page title="${title}">
    <form action="resumes" method="post" class="box">
        <div class="column is-6">
            <input name="uuid" type="hidden" value="${resume.uuid}">
            <div class="field">
                <label class="label" for="fullName">Имя</label>
                <div class="control">
                    <input class="input is-primary" type="text" id="fullName"
                           name="fullName" value="${resume.fullName}" required>
                </div>
                <p class="help">Обязательно для заполнения</p>
            </div>

            <!-- --- CONTACTS --- -->
            <div class="field">
                <label class="label">Контакты</label>
            </div>
            <c:forEach var="cType" items="${ContactType.values()}">
                <%--@elvariable id="cType" type="ru.javaops.webapp.model.ContactType"--%>
                <div class="control has-icons-left">
                    <input class="input" type="text" name="${cType.name()}" size="5"
                           placeholder="${cType.title}" value="${resume.getContact(cType)}">
                    <span class="icon is-left">
                    <i class="${Hyperlink.iconOf(cType)}"></i>
                </span>
                </div>
            </c:forEach>

            <!-- --- SECTIONS --- -->
            <c:forEach var="sType" items="${SectionType.values()}">
                <%--@elvariable id="sType" type="ru.javaops.webapp.model.SectionType"--%>
                <c:choose>
                    <c:when test="${sType == SectionType.OBJECTIVE}">
                        <%--@elvariable id="objSection" type="ru.javaops.webapp.model.TextSection"--%>
                        <c:set var="objSection" value="${resume.getSection(sType)}"/>
                        <div class="field">
                            <label class="label" for="${sType.name()}">${sType.title}</label>
                            <div class="control">
                                <input class="input is-primary" type="text" id="${sType.name()}"
                                       name="${sType.name()}" value="${objSection.text}" required>
                            </div>
                            <p class="help">Обязательно для заполнения</p>
                        </div>
                    </c:when>
                    <c:when test="${sType == SectionType.PERSONAL}">
                        <%--@elvariable id="textSection" type="ru.javaops.webapp.model.TextSection"--%>
                        <c:set var="textSection" value="${resume.getSection(sType)}"/>
                        <p>
                            <b><label for="${sType.name()}">${sType.title}</label></b><br>
                            <textarea name="${sType.name()}" id="${sType.name()}" rows="4"
                                      cols="80">${textSection.text}</textarea>
                        </p>
                    </c:when>
                    <c:when test="${sType == SectionType.ACHIEVEMENT || sType == SectionType.QUALIFICATION}">
                        <%--@elvariable id="listTextSection" type="ru.javaops.webapp.model.ListTextSection"--%>
                        <c:set var="listTextSection" value="${resume.getSection(sType)}"/>
                        <p>
                            <b><label for="${sType.name()}">${sType.title}</label></b><br>
                            <textarea name="${sType.name()}" id="${sType.name()}" rows="8" cols="80"
                            ><c:forEach var="text" items="${listTextSection.textList}"
                            >${text}&#13;&#10;</c:forEach></textarea>
                        </p>
                    </c:when>
                    <c:when test="${sType == SectionType.EXPERIENCE || sType == SectionType.EDUCATION}">
                        <!-- --- ORGANIZATIONS --- -->
                        <c:if test="${sType==SectionType.EXPERIENCE}">
                            <c:set var="titleLabel" value="Должность"/>
                        </c:if>
                        <c:if test="${sType==SectionType.EDUCATION}">
                            <c:set var="titleLabel" value="Курс обучения"/>
                        </c:if>
                        <h4>${sType.title}</h4>
                        <!-- --- ADD NEW ORGANIZATION --- -->
                        <details>
                            <summary>Добавить ${sType.title.toLowerCase()}</summary>
                            <table>
                                <c:set var="prefixNew" value="${sType.name()}_new"/>
                                <t:organization orgPrefix="${prefixNew}"/>
                                <tr style="height: 10px"></tr>
                                <t:period sectionType="${sType}" periodPrefix="${prefixNew}_new"
                                          titleLabel="${titleLabel}"/>
                            </table>
                        </details>
                        <%--@elvariable id="orgSection" type="ru.javaops.webapp.model.OrganizationSection"--%>
                        <c:set var="orgSection" value="${resume.getSection(sType)}"/>
                        <input name="${sType.name()}_count" type="hidden" value="${orgSection.content.size()}">
                        <c:forEach var="organization" varStatus="index" items="${orgSection.content}">
                            <table>
                                <tr>
                                    <td colspan="2">
                                        <hr>
                                    </td>
                                </tr>
                                <c:set var="orgPrefix" value="${sType.name()}_${index.index}"/>
                                <t:organization organization="${organization}" orgPrefix="${orgPrefix}"/>
                                <!-- --- PERIODS --- -->
                                <input name="${orgPrefix}_periodsCount" type="hidden"
                                       value="${organization.periods.size()}">
                                <c:forEach var="period" varStatus="index" items="${organization.periods}">
                                    <tr style="height: 10px"></tr>
                                    <t:period period="${period}" periodPrefix="${orgPrefix}_${index.index}"
                                              sectionType="${sType}" titleLabel="${titleLabel}"/>
                                </c:forEach>
                            </table>
                            <!-- --- NEW PERIOD --- -->
                            <details>
                                <summary>Добавить ${titleLabel.toLowerCase()} в ${organization.name}</summary>
                                <table>
                                    <t:period periodPrefix="${orgPrefix}_new" sectionType="${sType}"
                                              titleLabel="${titleLabel}"/>
                                </table>
                            </details>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>
            <p>
                <button type="submit">Сохранить</button>
                <button type="button" onclick="history.back()">Отмена</button>
            </p>
        </div>
    </form>
    <script>
        function clearPeriod(prefix) {
            document.getElementById(prefix + "_title").value = "";
            document.getElementById(prefix + "_startDate").value = "";
            document.getElementById(prefix + "_endDate").value = "";
            document.getElementById(prefix + "_description").value = "";
        }
    </script>
</t:page>