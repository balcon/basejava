<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="sType" %>
<%--@elvariable id="period" type="ru.javaops.webapp.model.Period"--%>
<%@ attribute name="period" %>
<%@ attribute name="periodIndex" %>

<c:if test="${sType.equals(\"EXPERIENCE\")}">
    <c:set var="titleLabel" value="Должность"/>
</c:if>
<c:if test="${sType.equals(\"EDUCATION\")}">
    <c:set var="titleLabel" value="Специальность"/>
</c:if>
<tr>
    <td><b><label for="${periodIndex}_title">${titleLabel}</label></b></td>
    <td>
        <input name="${periodIndex}_title" id="${periodIndex}_title"
               type="text" size="50" value="${period.title}">
    </td>
</tr>
<c:if test="${sType.equals(\"EXPERIENCE\")}">
    <tr>
        <td><b><label for="${periodIndex}_description">Обязанности</label></b></td>
        <td>
            <textarea name="${periodIndex}_description" id="${periodIndex}_description"
                      rows="4" cols="47">${period.description}</textarea>
        </td>
    </tr>
</c:if>
<tr>
    <td><b><label for="${periodIndex}_startDate">Начало</label></b></td>
    <td>
        <input name="${periodIndex}_startDate" id="${periodIndex}_startDate"
               placeholder="yyyy-mm-dd" type="date" size="50" value="${period.startDate}">
    </td>
</tr>
<tr>
    <td><b><label for="${periodIndex}_endDate">Конец</label></b></td>
    <td>
        <c:set var="endDate" value="${period.endDate}"/>
        <%--@elvariable id="Period" type="ru.javaops.webapp.model.Period"--%>
        <c:if test="${endDate.equals(Period.NOW)}">
            <c:set var="endDate" value=""/>
        </c:if>
        <input name="${periodIndex}_endDate" id="${periodIndex}_endDate"
               placeholder="yyyy-mm-dd" type="text" size="50" value="${endDate}">
    </td>
</tr>