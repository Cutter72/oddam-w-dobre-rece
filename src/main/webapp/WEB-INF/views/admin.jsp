<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Admin</title>
    <link rel="stylesheet" href="css/bootstrap/bootstrap.css"/>
    <link rel="stylesheet" href="css/style.css"/>
    <%--<link rel="stylesheet" href="css/bootstrap/bootstrap-theme.css"/>--%>
    <%--<link rel="stylesheet" href="css/bootstrap/bootstrap-table.css"/>--%>
    <%--<link rel="stylesheet" href="css/bootstrap/styles.css"/>--%>
</head>
<body>
<header class="header--form-page" style="background-image: none">
    <nav class="container container--70" style="text-align: center">
        <ul class="nav--actions" style="text-align: center">
            <li class="logged-user">
                Witaj ADMINIE ${user.firstName}
                <ul class="dropdown">
                    <li><a href="<jsp:include page="../links/hrefUserProfile.jsp"/>">Profil</a></li>
                    <li><a href="<jsp:include page="../links/hrefUserSettings.jsp"/>">Ustawienia</a></li>
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
            <li><a href="#" class="btn btn--without-border active">Zarządzanie Administratorami</a></li>
            <li><a href="#" class="btn btn--without-border">Zarządzanie Użytkownikami</a>
            </li>
            <li>
                <a href="#" class="btn btn--without-border">Zarządzanie Instytucjami</a>
            </li>
        </ul>
    </nav>

    <div class="slogan container container--90">
        <div class="slogan--item">
            <h1>
                Lista administratorów
            </h1>
            <div class="tab-content">
                <div class="tab-pane fade in active">
                    <table class="table">
                        <tr>
                            <th>Email</th>
                            <th>Imię</th>
                            <th>Nazwisko</th>
                            <th>Zarządzaj</th>
                        </tr>
                        <tr>
                            <td>email 1</td>
                            <td>Imie 1</td>
                            <td>Nazwisko 1</td>
                            <td>Edytuj Usuń</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</header>


<script src="js/app.js"></script>
</body>
</html>
