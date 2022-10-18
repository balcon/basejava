<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="organization" type="ru.javaops.webapp.model.Organization" %>
<%@ attribute name="orgPrefix" required="true" %>
<div class="field">
    <label class="label" for="${orgPrefix}_name">Организация</label>
    <div class="control">
        <input class="input" type="text" name="${orgPrefix}_name"
               id="${orgPrefix}_name" value="${organization.name}">
    </div>
</div>
<div class="field">
    <label class="label" for="${orgPrefix}_homepage">Домашняя страница</label>
    <div class="control">
        <input class="input" type="text" name="${orgPrefix}_homepage"
               id="${orgPrefix}_homepage" value="${organization.homepage}">
    </div>
</div>