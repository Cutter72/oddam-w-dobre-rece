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
                Witaj ADMINIE ${user.firstName}
                <ul class="dropdown">
                    <li><a href="<jsp:include page="../../links/hrefUserProfile.jsp"/>">Profil</a></li>
                    <li><a href="<jsp:include page="../../links/hrefUserSettings.jsp"/>">Ustawienia</a></li>
                    <li><a href="<jsp:include page="../../links/hrefUserGifts.jsp"/>">Moje dary</a></li>
                    <li><a href="<jsp:include page="../../links/hrefAdmin.jsp"/>">Panel Admina</a></li>
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
                <a href="<jsp:include page="../../links/hrefAdminOrganizations.jsp"/>" class="btn btn--without-border">Zarządzanie Instytucjami</a>
            </li>
            <li><a href="<jsp:include page="../../links/hrefAdminGiftsManagement.jsp"/>" class="btn btn--without-border active">Zarządzanie Darami</a></li>
        </ul>
    </nav>

    <div class="container">
        <div class="login-page">
            <h2>
                Zarządzanie Darami
            </h2>
            <h3 class="slogan--steps-title">
                Filtruj po adresie e-mail:
            </h3>
            <div>
                <form>
                    <input required type="text" name="email" placeholder="xyz@domain.com">
                    <button class="btn btn--small" type="submit">Filtruj</button>
                    <a href="<jsp:include page="../../links/hrefAdminGiftsManagement.jsp"/>?email=all" class="btn btn--small">Pokaż wszystkie</a>
                </form>
            </div>
        </div>
        <div class="tab-content">
            <div class="tab-pane fade in active">

                <section class="container">
                    <h3 class="slogan--steps-title">
                        Lista darów
                    </h3>
                    <table class="table">
                        <tr>
                            <th>Email użytkownika <a href="/admin/gifts?sortBy=email&sortOrder=asc" class="btn--small btn--without-border">ASC</a>/<a href="/admin/gifts?sortBy=email&sortOrder=des" class="btn--small btn--without-border">DES</a></th>
                            <th>Organizacja</th>
                            <th>Miasto organizacji</th>
                            <th>Worki</th>
                            <th>Data utworzenia <a href="/admin/gifts?sortBy=created&sortOrder=asc" class="btn--small btn--without-border">ASC</a>/<a href="/admin/gifts?sortBy=created&sortOrder=des" class="btn--small btn--without-border">DES</a></th>
                            <th>Preferowana data odebrania</th>
                            <th>Odebrane? <a href="/admin/gifts?sortBy=dateCollected&sortOrder=asc" class="btn--small btn--without-border">ASC</a>/<a href="/admin/gifts?sortBy=dateCollected&sortOrder=des" class="btn--small btn--without-border">DES</a></th>
                            <th>Szczegóły</th>
                        </tr>
                        <c:forEach items="${giftList}" var="gift">
                            <tr>
                                <td>${gift.user.email}</td>
                                <td>${gift.organization.name}</td>
                                <td>${gift.organization.city.name}</td>
                                <td>${gift.bags}</td>
                                <td>${gift.created}</td>
                                <td>${gift.preferredDateOfCollection} ${gift.preferredTimeOfCollection}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${gift.collected}">
                                            Tak<img width= "20px" src="https://upload.wikimedia.org/wikipedia/commons/8/80/Symbol_OK.svg"/> ${gift.dateCollected}
                                        </c:when>
                                        <c:otherwise>
                                            <span>Nie ❌️</span>
                                            <span class="makeStatusTrue btn btn--without-border">Ustaw jako odebrane</span>
                                            <div hidden>
                                            <form method="post" action="/user/gifts?destination=admin">
                                                <label>Podaj datę odebrania:</label><input type="date" name="date" class="dateInput" required /><br/>
                                                <button type="submit" class="btn--small btn--without-border">Ustaw</button>
                                                <a href="/admin/gifts" class="btn--small btn--without-border">Anuluj</a>
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                <input type="hidden" name="giftId" value="${gift.id}"/>
                                            </form>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                    </td>
                                <td class="showDetailsBtn btn btn--without-border">Pokaż</td>
                            </tr>
                            <tr style="background-color: #f0f0f0" hidden>
                                <td><b>Ulica:</b><br/>
                                    ${gift.street}</td>
                                <td><b>Miasto:</b><br/>
                                    ${gift.city}</td>
                                <td><b>Kod pocztowy:</b><br/>
                                    ${gift.postCode}</td>
                                <td><b>Telefon:</b><br/>
                                    ${gift.callNumber}</td>
                                <td><b>Notatka dla kuriera:</b><br/>
                                    ${gift.courierNote}</td>
                                <td><b>Odebrano dnia:</b><br/>
                                    ${gift.dateCollected}</td>
                                <td><b>Aktualizowano dnia:</b><br/>
                                    ${gift.updated}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </section>
            </div>
        </div>
    </div>
</header>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="<c:url value="../../../js/showDetails.js"/>" type="text/javascript"></script>
<script src="<c:url value="../../../js/makeStatusTrue.js"/>" type="text/javascript"></script>
</body>
</html>
