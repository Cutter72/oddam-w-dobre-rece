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
    <title>Document</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>

<jsp:include page="../components/header.jsp"/>

<section class="login-page">
    <h2>Załóż konto</h2>
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    <form:form method="post" modelAttribute="user">
        <div class="form-group">
            <h3 style="color: #b92c28;"><form:errors path="firstName" name="firstName" placeholder="Imię" /></h3>
            <form:input path="firstName" name="firstName" placeholder="Imie" />
        </div>
        <div class="form-group">
            <h3 style="color: #b92c28;"><form:errors path="lastName" name="lastName" placeholder="Nazwisko" /></h3>
            <form:input path="lastName" name="lastName" placeholder="Nazwisko" />
        </div>
        <h3 style="color: #b92c28;">${duplicateEmail}</h3>
        <div class="form-group">
            <h3 style="color: #b92c28;"><form:errors path="email" type="email" name="email" placeholder="E-mail" /></h3>
            <form:input path="email" type="email" name="email" placeholder="E-mail" />
        </div>
        <div class="form-group">
            <h3 style="color: #b92c28;"><form:errors path="password" type="password" name="password" placeholder="Hasło" /></h3>
            <form:input path="password" type="password" name="password" placeholder="Hasło" />
        </div>
        <%--<div class="form-group">--%>
            <%--<input type="password" name="password2" placeholder="Powtórz hasło" />--%>
        <%--</div>--%>
        <h3 style="color: #b92c28;">${captchaNotChecked}</h3>
        <div class="g-recaptcha form-group" data-sitekey="6LeWZa4UAAAAAB6oRn5dNo86whatF2wBqS9nuOmo"></div>
        <div class="form-group form-group--buttons">
            <a href="<jsp:include page="../links/hrefLogin.jsp"/>" class="btn btn--without-border">Zaloguj się</a>
            <button class="btn" type="submit">Załóż konto</button>
        </div>
    </form:form>
</section>

<jsp:include page="../components/footer.jsp"/>
</body>
</html>
