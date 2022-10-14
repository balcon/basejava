<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="organization" type="ru.javaops.webapp.model.Organization" %>
<%@ attribute name="orgPrefix" required="true" %>
<tr>
    <td><b><label for="${orgPrefix}_name">Организация</label></b></td>
    <td>
        <input name="${orgPrefix}_name" id="${orgPrefix}_name"
               type="text" size="50" value="${organization.name}">
    </td>
</tr>
<tr>
    <td><b><label for="${orgPrefix}_homepage">Домашняя страница</label></b></td>
    <td>
        <input name="${orgPrefix}_homepage" id="${orgPrefix}_homepage"
               type="text" size="50" value="${organization.homepage}">
    </td>
</tr>