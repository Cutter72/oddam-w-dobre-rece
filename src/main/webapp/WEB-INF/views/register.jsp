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
    <title>Register</title>
    <link rel="stylesheet" href="css/style.css" />
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body>

<jsp:include page="../components/header.jsp"/>

<section class="login-page">
    <h2>Załóż konto</h2>
    <form:form method="post" modelAttribute="user">
        <div class="form-group">
            <h3 style="color: #b92c28;"><form:errors path="firstName" name="firstName" placeholder="Imię" /></h3>
            <label>Imię:</label><br/>
            <form:input path="firstName" name="firstName" placeholder="Imie" maxlength="15" required="required" />
        </div>
        <div class="form-group">
            <h3 style="color: #b92c28;"><form:errors path="lastName" name="lastName" placeholder="Nazwisko" /></h3>
            <label>Nazwisko:</label><br/>
            <form:input path="lastName" name="lastName" placeholder="Nazwisko" maxlength="15" required="required" />
        </div>
        <h3 style="color: #b92c28;">${duplicateEmail}</h3>
        <div class="form-group">
            <h3 style="color: #b92c28;"><form:errors path="email" type="email" name="email" placeholder="E-mail" /></h3>
            <label>E-mail:</label><br/>
            <form:input path="email" type="email" name="email" placeholder="E-mail" maxlength="31" required="required" />
        </div>
        <div class="form-group">
            <h3 style="color: #b92c28;"><form:errors path="password" type="password" name="password" placeholder="Hasło" /></h3>
            <label>Hasło:</label><br/>
            <form:input path="password" type="password" name="password" placeholder="Hasło" maxlength="255" id="password1" autocomplete="new-password" required="required" />
        </div>
        <div class="form-group">
            <label>Powtórz hasło:</label><br/>
            <input type="password" name="password2" placeholder="Powtórz hasło" id="password2" autocomplete="new-password" required maxlength="255"/>
            <span></span>
        </div>
        <h3 style="color: #b92c28;">${captchaNotChecked}️</h3>
        <div class="g-recaptcha form-group" data-sitekey="${reCaptchaKey}"></div>
        <div class="form-group form-group--buttons">
            <a href="<jsp:include page="../links/hrefLogin.jsp"/>" class="btn btn--without-border">Zaloguj się</a>
            <button class="btn" type="submit" id="registrationSubmit" disabled>Załóż konto</button>
        </div>
    </form:form>
</section>

<jsp:include page="../components/footer.jsp"/>
<script src="js/passValidation.js"></script>
</body>
</html>
