<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="title" required="true" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.6.1/css/bulma.min.css">
</head>

<body>
<nav class="navbar is-primary" role="navigation" aria-label="main navigation">
    <div class="navbar-brand">
        <a class="navbar-item" href="https://github.com/balcon/basejava">
            <img src="https://upload.wikimedia.org/wikipedia/commons/5/59/Logo-Logo.svg" width="112" height="28">
        </a>
        <a class="navbar-item" href="<c:url value="/resumes"/>">
            <span class="icon"><i class="fa fa-list"></i></span>
            <span>Список резюме</span>
        </a>
        <a class="navbar-item" href="<c:url value="resumes?action=create"/>">
            <span class="icon"><i class="fa fa-plus"></i></span>
            <span>Добавить резюме</span>
        </a>
    </div>
</nav>
<div class="container" style="margin-top: 10pt">
    <jsp:doBody/>
</div>
</body>
</html>