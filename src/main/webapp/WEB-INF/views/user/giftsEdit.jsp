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
    <title>Moje zbiórki</title>
    <link rel="stylesheet" href="<c:url value="../../../css/bootstrap/bootstrap.css"/>"/>
    <link rel="stylesheet" href="<c:url value="../../../css/style.css"/>"/>
</head>
<body>
<header class="header--form-page" style="background-image: none">
    <nav class="container container--70" style="text-align: center">
        <ul class="nav--actions" style="text-align: center">
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
            <li><a href="<jsp:include page="../../links/hrefAdmin.jsp"/>" class="btn btn--without-border">Zarządzanie Administratorami</a></li>
            <li><a href="<jsp:include page="../../links/hrefAdminUser.jsp"/>" class="btn btn--without-border">Zarządzanie Użytkownikami</a>
            </li>
            <li>
                <a href="<jsp:include page="../../links/hrefAdminOrganizations.jsp"/>" class="btn btn--without-border active">Zarządzanie Organizacjami</a>
            </li>
        </ul>
    </nav>

    <div class="container">
        <div class="slogan--item" style="width: 100%">
            <h2>
                Moje zbiórki
            </h2>
        </div>
        <div class="tab-content">
            <div class="tab-pane fade in active">

                <section class="container">
                    <h3 class="slogan--steps-title">
                        Formularz edycji organizacji
                    </h3>
                    <%-- Edit collections form here--%>
                </section>

                <section class="container">
                    <h3 class="slogan--steps-title">
                        Lista moich darów
                    </h3>
                    <table class="table">
                        <tr>
                            <th>Typ</th>
                            <th>Nazwa</th>
                            <th>Misja</th>
                            <th>Miasto</th>
                            <th>Potrzeby</th>
                            <th>Grupa docelowa</th>
                            <th>Zarządzaj</th>
                        </tr>
                        <c:forEach items="${giftList}" var="gift">
                            <tr>
                                <td>${gift.type.name}</td>
                                <td>${gift.name}</td>
                                <td>${gift.mission}</td>
                                <td>${gift.city.name}</td>
                                <td>
                                    <c:forEach items="${gift.need}" var="need">
                                        <div>${need.name}</div>
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:forEach items="${gift.target}" var="target">
                                        <div>${target.name}</div>
                                    </c:forEach>
                                </td>
                                <td>
                                    <a href="<jsp:include page="../../links/hrefAdminOrganizationEdit.jsp"/>/${gift.id}" class="btn btn--small">Edytuj</a>
                                    <a href="<jsp:include page="../../links/hrefAdminOrganizationDelete.jsp"/>/${gift.id}" class="btn btn--small deleteBtn">Usuń</a>
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
<script src="<c:url value="../../../js/confirm.js"/>" type="text/javascript"></script>
</body>
</html>
