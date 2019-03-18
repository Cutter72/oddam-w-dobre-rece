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
    <title>Admin</title>
    <link rel="stylesheet" href="css/bootstrap/bootstrap.css"/>
    <link rel="stylesheet" href="css/style.css"/>
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
            <li><a href="<jsp:include page="../links/hrefAdmin.jsp"/>" class="btn btn--without-border active">Zarządzanie Administratorami</a></li>
            <li><a href="<jsp:include page="../links/hrefAdminUser.jsp"/>" class="btn btn--without-border">Zarządzanie Użytkownikami</a>
            </li>
            <li>
                <a href="<jsp:include page="../links/hrefAdminOrganizations.jsp"/>" class="btn btn--without-border">Zarządzanie Instytucjami</a>
            </li>
        </ul>
    </nav>

    <div class="container">
        <div class="slogan--item">
            <h2>
                Zarządzanie Administratorami
            </h2>
        </div>
        <div class="tab-content">
            <div class="tab-pane fade in active">

                <section class="container">
                    <h3 class="slogan--steps-title">
                        Formularz dodawania nowego administratora
                    </h3>
                    <form:form method="post" modelAttribute="newUser">
                        <table class="table">
                            <tr>
                                <th>
                                    <div class="form-group">
                                        <form:input  path="firstName" name="firstName" placeholder="Imie"/><br/>
                                        <form:errors path="firstName" name="firstName" placeholder="Imię"/>
                                    </div>
                                </th>
                                <th>
                                    <div class="form-group">
                                        <form:input path="lastName" name="lastName" placeholder="Nazwisko"/><br/>
                                        <form:errors path="lastName" name="lastName" placeholder="Nazwisko"/>
                                    </div>
                                </th>
                                <th>
                                    <div class="form-group">
                                        <form:input path="email" type="email" name="email" placeholder="E-mail"/><br/>
                                        ${duplicateEmail}
                                        <form:errors path="email" type="email" name="email" placeholder="E-mail"/>
                                    </div>
                                </th>
                                <th>
                                    <div class="form-group">
                                        <form:input path="password" type="password" name="password"
                                                    placeholder="Hasło"/><br/>
                                        <form:errors path="password" type="password" name="password"
                                                     placeholder="Hasło"/>
                                    </div>
                                </th>
                            </tr>
                        </table>
                        <div class="form-group form-group--buttons">
                            <button class="btn btn--small" type="submit">Dodaj</button>
                        </div>
                    </form:form>
                </section>

                <section class="container">
                    <h3 class="slogan--steps-title">
                        Lista administratorów
                    </h3>
                    <table class="table">
                        <tr>
                            <th>Email</th>
                            <th>Imię</th>
                            <th>Nazwisko</th>
                            <th>Zarządzaj</th>
                        </tr>
                        <c:forEach items="${adminList}" var="admin">
                            <tr>
                                <td>${admin.email}</td>
                                <td>${admin.firstName}</td>
                                <td>${admin.lastName}</td>
                                <td>
                                    <a href="<jsp:include page="../links/hrefAdminEdit.jsp"/>/${admin.id}" class="btn btn--small">Edytuj</a>
                                    <a href="<jsp:include page="../links/hrefAdminDelete.jsp"/>/${admin.id}" class="btn btn--small deleteBtn">Usuń</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </section>

            </div>
        </div>
    </div>
</header>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="<c:url value="../../js/confirm.js"/>" type="text/javascript"></script>
</body>
</html>
