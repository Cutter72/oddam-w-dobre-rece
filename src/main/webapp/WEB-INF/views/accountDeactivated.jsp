<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Error</title>
    <link rel="stylesheet" href="/css/style.css"/>
</head>
<body>
<header class="header--form-page">
    <nav class="container container--70">
        <ul>
            <li><a href="/" class="btn btn--without-border active">Start</a></li>
            <li><a href="<jsp:include page="../links/hrefWhat.jsp"/>" class="btn btn--without-border">O co
                chodzi?</a>
            </li>
            <li><a href="<jsp:include page="../links/hrefAbout.jsp"/>" class="btn btn--without-border">O nas</a></li>
            <li>
                <a href="<jsp:include page="../links/hrefOrganizations.jsp"/>" class="btn btn--without-border"
                >Fundacje i organizacje</a
                >
            </li>
            <li><a href="<jsp:include page="../links/hrefContact.jsp"/>" class="btn btn--without-border">Kontakt</a>
            </li>
        </ul>
    </nav>
    <section class="login-page">
        <h2 class="color-red" style="color: red;">
            Nie masz dostępu do zasobów lub zostałeś zbanowany!<br/>
            Skontaktuj się z nami poprzez formularz kontaktowy.
        </h2>
        <h1>Przekierowanie na stronę główną za: <span id="timer">5</span> s</h1>
        <form action="/logout" method="post" hidden="hidden" id="logoutForm">
            <input class="btn--small" type="submit" value="Wyloguj">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </section>
</header>

<script src="/js/timeRedirectHome.js"></script>
</body>
</html>
