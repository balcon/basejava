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
        <div class="column is-7">
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
                <div class="field">
                    <div class="control has-icons-left">
                        <input class="input " type="text" name="${cType.name()}" size="5"
                               placeholder="${cType.title}" value="${resume.getContact(cType)}">
                        <span class="icon is-left">
                            <i class="${Hyperlink.iconOf(cType)}"></i>
                        </span>
                    </div>
                </div>
            </c:forEach>

            <!-- --- SECTIONS --- -->
            <c:forEach var="sType" items="${SectionType.values()}">
                <%--@elvariable id="sType" type="ru.javaops.webapp.model.SectionType"--%>
                <c:choose>
                    <c:when test="${sType == SectionType.OBJECTIVE||sType == SectionType.PERSONAL}">
                        <!-- --- TEXT SECTIONS --- -->
                        <%--@elvariable id="textSection" type="ru.javaops.webapp.model.TextSection"--%>
                        <c:set var="textSection" value="${resume.getSection(sType)}"/>
                        <div class="field">
                            <label class="label" for="${sType.name()}">${sType.title}</label>
                            <c:if test="${sType == SectionType.OBJECTIVE}">
                                <div class="control">
                                    <input class="input is-primary" type="text" id="${sType.name()}"
                                           name="${sType.name()}" value="${textSection.text}" required>
                                </div>
                                <p class="help">Обязательно для заполнения</p>
                            </c:if>
                            <c:if test="${sType == SectionType.PERSONAL}">
                                <div class="control">
                                    <textarea class="textarea has-fixed-size" id="${sType.name()}"
                                              name="${sType.name()}">${textSection.text}</textarea>
                                </div>
                            </c:if>
                        </div>
                    </c:when>
                    <c:when test="${sType == SectionType.ACHIEVEMENT || sType == SectionType.QUALIFICATION}">
                        <!-- --- LIST TEXT SECTIONS --- -->
                        <%--@elvariable id="listTextSection" type="ru.javaops.webapp.model.ListTextSection"--%>
                        <c:set var="listTextSection" value="${resume.getSection(sType)}"/>
                        <div class="field">
                            <label class="label" for="${sType.name()}">${sType.title}</label>
                            <div class="control">
                                <textarea class="textarea" id="${sType.name()}" name="${sType.name()}" rows="8"
                                ><c:forEach var="text" items="${listTextSection.textList}"
                                >${text}&#13;&#10;</c:forEach></textarea>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${sType == SectionType.EXPERIENCE || sType == SectionType.EDUCATION}">
                        <!-- --- ORGANIZATIONS --- -->
                        <c:if test="${sType==SectionType.EXPERIENCE}">
                            <c:set var="titleLabel" value="Должность"/>
                        </c:if>
                        <c:if test="${sType==SectionType.EDUCATION}">
                            <c:set var="titleLabel" value="Курс обучения"/>
                        </c:if>
                        <div class="field">
                            <label class="label">${sType.title}</label>
                        </div>
                        <!-- --- ADD NEW ORGANIZATION --- -->
                        <button class="button is-small is-primary" type="button"
                                id="${sType.name()}_new_btn" onclick="showBlock('${sType.name()}_new')">
                            <span class="icon"><i class="fa fa-plus"></i></span>
                            <span>Добавить ${sType.title.toLowerCase()}</span>
                        </button>
                        <div id="${sType.name()}_new" hidden>
                            <div class="box">
                                <c:set var="prefixNew" value="${sType.name()}_new"/>
                                <t:organization orgPrefix="${prefixNew}"/>
                                <tr style="height: 10px"></tr>
                                <t:period sectionType="${sType}" periodPrefix="${prefixNew}_new"
                                          titleLabel="${titleLabel}"/>
                            </div>
                        </div>
                        <br>
                        <%--@elvariable id="orgSection" type="ru.javaops.webapp.model.OrganizationSection"--%>
                        <c:set var="orgSection" value="${resume.getSection(sType)}"/>
                        <input name="${sType.name()}_count" type="hidden" value="${orgSection.content.size()}">
                        <c:forEach var="organization" varStatus="index" items="${orgSection.content}">
                            <c:set var="orgPrefix" value="${sType.name()}_${index.index}"/>
                            <div id="${orgPrefix}_box">
                                <div class="box">
                                    <button class="delete is-pulled-right" type="button"
                                            onclick="clearOrg('${orgPrefix}')"></button>
                                    <t:organization organization="${organization}" orgPrefix="${orgPrefix}"/>
                                    <!-- --- PERIODS --- -->
                                    <input name="${orgPrefix}_periodsCount" type="hidden"
                                           value="${organization.periods.size()}">
                                    <c:forEach var="period" varStatus="index" items="${organization.periods}">
                                        <div id="${orgPrefix}_${index.index}_box">
                                            <div class="box">
                                                <button class="delete is-pulled-right" type="button"
                                                        onclick="clearPeriod('${orgPrefix}_${index.index}')"></button>
                                                <br>
                                                <t:period period="${period}" periodPrefix="${orgPrefix}_${index.index}"
                                                          sectionType="${sType}" titleLabel="${titleLabel}"/>
                                            </div>
                                            <p></p>
                                        </div>
                                    </c:forEach>

                                    <!-- --- NEW PERIOD --- -->
                                    <button class="button is-small is-primary" type="button"
                                            id="${orgPrefix}_new_btn" onclick="showBlock('${orgPrefix}_new')">
                                        <span class="icon"><i class="fa fa-plus"></i></span>
                                        <span>Добавить ${titleLabel.toLowerCase()} в ${organization.name}</span>
                                    </button>
                                    <div id="${orgPrefix}_new" hidden>
                                        <div class="box">
                                            <t:period periodPrefix="${orgPrefix}_new" sectionType="${sType}"
                                                      titleLabel="${titleLabel}"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>
            <br>
            <button class="button is-primary is-hovered" type="submit">
                <span class="icon"><i class="fa fa-floppy-disk"></i></span>
                <span>Сохранить</span>
            </button>
            <button class="button is-primary is-hovered" type="button" onclick="history.back()">
                <span class="icon"><i class="fa fa-ban"></i></span>
                <span>Отмена</span>
            </button>
        </div>
    </form>
    <script>
        function clearPeriod(prefix) {
            document.getElementById(prefix + "_box").hidden = true;
            document.getElementById(prefix + "_title").value = "";
        }

        function clearOrg(prefix) {
            document.getElementById(prefix + "_box").hidden = true;
            document.getElementById(prefix + "_name").value = "";
        }

        function showBlock(blockId) {
            document.getElementById(blockId + "_btn").disabled = true;
            document.getElementById(blockId).hidden = false;
        }
    </script>
</t:page>