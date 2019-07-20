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
    <title>reCaptcha error</title>
    <link rel="stylesheet" href="/css/style.css"/>
</head>
<body>
<header class="header--form-page">
    <nav class="container container--70">
        <ul class="nav--actions">
            <li class="logged-user">
                Witaj ${user.firstName}
                <ul class="dropdown">
                    <li><a href="<jsp:include page="../links/hrefUserProfile.jsp"/>">Profil</a></li>
                    <li><a href="<jsp:include page="../links/hrefUserSettings.jsp"/>">Ustawienia</a></li>
                    <li><a href="<jsp:include page="../links/hrefUserGifts.jsp"/>">Moje dary</a></li>
                    <li><a href="<jsp:include page="../links/hrefUserCollections.jsp"/>">Moje zbiórki</a></li>
                    <li>
                        <form action="/logout" method="post">
                            <input class="btn--small" type="submit" value="Wyloguj">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                    </li>
                </ul>
            </li>
        </ul>

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
    <section class="form--steps">

        <div class="form--steps-container">
            <div class="form--steps-counter"></div>
            <div data-step="7" class="active">
                <h2>
                    Nie zaznaczono reCAPTCHA lub jesteś robotem.
                    <br/>WIADOMOŚĆ NIE ZOSTAŁA WYSŁANA. SPRÓBUJ PONOWNIE.
                </h2>
                <label>Przekierowanie na stronę główną użytkownika za: <label id="timer">5</label> s</label></div>
        </div>
        </div>
    </section>
</header>

<script src="/js/timeRedirectHome.js"></script>
</body>
</html>
