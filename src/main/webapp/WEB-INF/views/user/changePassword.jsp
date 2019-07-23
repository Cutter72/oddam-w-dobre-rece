<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="pl-PL">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Zmień hasło</title>
    <link rel="stylesheet" href="<c:url value="../../../css/style.css"/>" />
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
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
            <li><a href="<jsp:include page="../../links/hrefWhat.jsp"/>" class="btn btn--without-border">O co
                chodzi?</a>
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



<section class="login-page">
    <h2>Zmień hasło</h2>
    <h1 style="color: #b92c28;">${wrongPassword}</h1>
    <form method="post" action="<jsp:include page="../../links/hrefUserPasswordChange.jsp"/>">
        <div class="form-group">
            <label>Stare hasło:</label><br/>
            <input type="password" name="oldPassword" placeholder="Stare hasło" id="oldPassword" autocomplete="current-password" required maxlength="255"/>
        </div>
        <div class="form-group form-group--50">
            <label>Nowe hasło:</label><br/>
            <input type="password" name="password1" placeholder="Nowe hasło" id="password1" autocomplete="new-password" required maxlength="255"/>
        </div>
        <div class="form-group form-group--50">
            <label>Powtórz hasło:</label><br/>
            <input type="password" name="password2" placeholder="Powtórz hasło" id="password2" autocomplete="new-password" required maxlength="255"/>
            <span></span>
        </div>
        <h3 style="color: #b92c28;">${captchaNotChecked}️</h3>
        <div class="g-recaptcha form-group" data-sitekey="${reCaptchaKey}"></div>
        <div class="form-group form-group--buttons">
            <a href="<jsp:include page="../../links/hrefUserProfile.jsp"/>" class="btn btn--without-border">Anuluj</a>
            <button class="btn" type="submit" id="registrationSubmit" disabled>Zmień hasło</button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </div>
    </form>
</section>
</header>

<jsp:include page="../../components/footer.jsp"/>
<script src="<c:url value="../../../js/passValidation.js"/>"></script>
</body>
</html>
