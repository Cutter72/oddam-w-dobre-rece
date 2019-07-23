<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pl-PL">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Login</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>

<jsp:include page="../components/header.jsp"/>

<section class="login-page">
    <h2>Zaloguj się</h2>
    <h1 style="color: #b92c28;">${errorLogin}</h1>
    <h1 style="color: #5cb85c;">${registerSuccess}</h1>
    <form action="/login" method="post">
        <div class="form-group">
            <label>E-mail:</label><br/>
            <input name="username" placeholder="Email" type="email" required />
        </div>
        <div class="form-group">
            <label>Hasło:</label><br/>
            <input type="password" name="password" placeholder="Hasło" autocomplete="current-password" required />
            <a href="/password/reset" class="btn btn--small btn--without-border reset-password">Zapomniałem hasła</a>
        </div>

        <div class="form-group form-group--buttons">
            <a href="<jsp:include page="../links/hrefRegister.jsp"/>" class="btn btn--without-border">Załóż konto</a>
            <button class="btn" type="submit">Zaloguj się</button>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</section>

<jsp:include page="../components/footer.jsp"/>
</body>
</html>
