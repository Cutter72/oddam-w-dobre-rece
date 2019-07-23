<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Mój profil</title>
    <link rel="stylesheet" href="<c:url value="../../../css/style.css"/>"/>
</head>
<body>
<header class="header--form-page">
    <nav class="container container--70">
        <ul class="nav--actions">
            <li class="logged-user">
                Witaj ${user.firstName}
                <ul class="dropdown">
                    <li><a href="<jsp:include page="../../links/hrefUserProfile.jsp"/>">Profil</a></li>
                    <li><a href="<jsp:include page="../../links/hrefUserSettings.jsp"/>">Ustawienia</a></li>
                    <li><a href="<jsp:include page="../../links/hrefUserGifts.jsp"/>">Moje dary</a></li>
                    <li><a href="<jsp:include page="../../links/hrefUserCollections.jsp"/>">Moje zbiórki</a></li>
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
            <li><a href="<jsp:include page="../../links/hrefWhat.jsp"/>" class="btn btn--without-border">O co chodzi?</a>
            </li>
            <li><a href="<jsp:include page="../../links/hrefAbout.jsp"/>" class="btn btn--without-border">O nas</a></li>
            <li>
                <a href="<jsp:include page="../../links/hrefOrganizations.jsp"/>" class="btn btn--without-border"
                >Fundacje i organizacje</a
                >
            </li>
            <li><a href="<jsp:include page="../../links/hrefContact.jsp"/>" class="btn btn--without-border">Kontakt</a>
            </li>
        </ul>
    </nav>

    <div class="slogan container container--90">
        <div class="slogan--item" style="text-align: center">
            <h2>
                <span class="uppercase">twój profil</span>
            </h2>
            <div class="slogan--steps-title">Imię: <b>${user.firstName}</b></div>
            <div class="slogan--steps-title">Nazwisko: <b>${user.lastName}</b></div>
            <div class="slogan--steps-title">E-mail: <b>${user.email}</b></div>
            <div class="btn btn--without-border"><a href="<jsp:include page="../../links/hrefEditPersonalData.jsp"/>">Edytuj dane osobowe</a></div><br/>
            <div class="btn btn--without-border"><a href="<jsp:include page="../../links/hrefPasswordChange.jsp"/>">Zmień hasło</a></div>
        </div>
    </div>
</header>

<jsp:include page="../../components/footer.jsp"/>

</body>
</html>
