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
    <title>Resetowania hasła</title>
    <link rel="stylesheet" href="<c:url value="../../css/style.css"/>" />
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body>

<jsp:include page="../components/header.jsp"/>

<section class="login-page">
    <h2>Zresetuj hasło</h2>
    <h1 style="color: #b92c28;">${noSuchEmail}</h1>
    <form method="post" action="/password/reset">
        <div class="form-group">
            <label>Podaj email:</label><br/>
            <input type="email " name="email" placeholder="E-mail" id="email" autocomplete="email" required maxlength="255" value="${email}"/>
        </div>
        <h3 style="color: #b92c28;">${captchaNotChecked}️</h3>
        <div class="g-recaptcha form-group" data-sitekey="${reCaptchaKey}"></div>
        <div class="form-group form-group--buttons">
            <button class="btn" type="submit">Zresetuj hasło</button>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</section>

<jsp:include page="../components/footer.jsp"/>
</body>
</html>
