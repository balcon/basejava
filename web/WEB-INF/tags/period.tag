<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="ru.javaops.webapp.model.Period" %>
<%@ tag import="ru.javaops.webapp.model.SectionType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="period" type="ru.javaops.webapp.model.Period" %>
<%@ attribute name="sectionType" type="ru.javaops.webapp.model.SectionType" required="true" %>
<%@ attribute name="periodPrefix" required="true" %>
<%@ attribute name="titleLabel" required="true" %>
<div class="field is-horizontal">
    <div class="field-label is-normal">
        <label class="label" for="${periodPrefix}_title">${titleLabel}</label>
    </div>
    <div class="field-body">
        <div class="field">
            <div class="control">
                <input class="input" name="${periodPrefix}_title" id="${periodPrefix}_title"
                       type="text" value="${period.title}">
            </div>
        </div>
    </div>
</div>
<c:if test="${sectionType==SectionType.EXPERIENCE}">
    <div class="field is-horizontal">
        <div class="field-label is-normal">
            <label class="label" for="${periodPrefix}_description">Обязанности</label>
        </div>
        <div class="field-body">
            <div class="field">
                <div class="control">
                    <textarea class="textarea has-fixed-size" name="${periodPrefix}_description" rows=3
                              id="${periodPrefix}_description">${period.description}</textarea>
                </div>
            </div>
        </div>
    </div>
</c:if>
<div class="field is-horizontal">
    <div class="field-label is-normal">
        <label class="label" for="${periodPrefix}_startDate">Период</label>
    </div>
    <div class="field-body">
        <div class="field ">
            <div class="control">
                <input class="input" name="${periodPrefix}_startDate" id="${periodPrefix}_startDate"
                       placeholder="yyyy-mm-dd" type="date" value="${period.startDate}">
            </div>
        </div>
<%--    </div>--%>
<%--    <div class="field-label is-normal">--%>
<%--        <label class="label" for="${periodPrefix}_endDate">Конец</label>--%>
<%--    </div>--%>
<%--    <div class="field-body">--%>
        <div class="field">
            <div class="control">
                <c:set var="endDate" value="${period.endDate}"/>
                <c:if test="${endDate.equals(Period.NOW)}">
                    <c:set var="endDate" value=""/>
                </c:if>
                <input class="input" name="${periodPrefix}_endDate" id="${periodPrefix}_endDate"
                       placeholder="yyyy-mm-dd" type="date" value="${endDate}">
            </div>
        </div>
    </div>
</div>
<%--<tr>--%>
<%--    <td><b><label for="${periodPrefix}_title">${titleLabel}</label></b></td>--%>
<%--    <td>--%>
<%--        <input name="${periodPrefix}_title" id="${periodPrefix}_title"--%>
<%--               type="text" size="50" value="${period.title}">--%>
<%--        <button type="button" onclick="clearPeriod('${periodPrefix}')">X</button>--%>
<%--    </td>--%>
<%--</tr>--%>
<%--<c:if test="${sectionType==SectionType.EXPERIENCE}">--%>
<%--    <tr>--%>
<%--        <td><b><label for="${periodPrefix}_description">Обязанности</label></b></td>--%>
<%--        <td>--%>
<%--                                    <textarea name="${periodPrefix}_description" id="${periodPrefix}_description"--%>
<%--                                              rows="4" cols="47">${period.description}</textarea>--%>
<%--        </td>--%>
<%--    </tr>--%>
<%--</c:if>--%>
<%--<tr>--%>
<%--    <td><b><label for="${periodPrefix}_startDate">Начало</label></b></td>--%>
<%--    <td>--%>
<%--        <input name="${periodPrefix}_startDate" id="${periodPrefix}_startDate"--%>
<%--               placeholder="yyyy-mm-dd" type="date" size="50" value="${period.startDate}">--%>
<%--    </td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--    <td><b><label for="${periodPrefix}_endDate">Конец</label></b></td>--%>
<%--    <td>--%>
<%--        <c:set var="endDate" value="${period.endDate}"/>--%>
<%--        <c:if test="${endDate.equals(Period.NOW)}">--%>
<%--            <c:set var="endDate" value=""/>--%>
<%--        </c:if>--%>
<%--        <input name="${periodPrefix}_endDate" id="${periodPrefix}_endDate"--%>
<%--               placeholder="yyyy-mm-dd" type="date" size="50" value="${endDate}">--%>
<%--    </td>--%>
<%--</tr>--%>