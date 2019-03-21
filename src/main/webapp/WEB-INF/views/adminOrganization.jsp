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
    <link rel="stylesheet" href="/css/bootstrap/bootstrap.css"/>
    <link rel="stylesheet" href="/css/style.css"/>
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
            <li><a href="<jsp:include page="../links/hrefAdmin.jsp"/>" class="btn btn--without-border">Zarządzanie Administratorami</a></li>
            <li><a href="<jsp:include page="../links/hrefAdminUser.jsp"/>" class="btn btn--without-border">Zarządzanie Użytkownikami</a>
            </li>
            <li>
                <a href="<jsp:include page="../links/hrefAdminOrganizations.jsp"/>" class="btn btn--without-border active">Zarządzanie Organizacjami</a>
            </li>
        </ul>
    </nav>

    <div class="container">
        <div class="slogan--item">
            <h2>
                Zarządzanie Organizacjami
            </h2>
        </div>
        <div class="tab-content">
            <div class="tab-pane fade in active">

                <section class="container">
                    <h3 class="slogan--steps-title">
                        Formularz dodawania nowej organizacji
                    </h3>
                    <form:form method="post" modelAttribute="newOrganization">
                        <table class="table">
                            <tr>
                                <th>Dane organizacji</th>
                                <th>Potrzeby</th>
                                <th>Grupa docelowa</th>
                            </tr>
                            <tr>
                                <td>
                                    <div class="form-group">
                                        <form:select path="type">
                                            <form:options items="${organizationTypeList}" itemLabel="name" itemValue="id"/>
                                        </form:select>
                                    </div>


                                    <div class="form-group">
                                        <form:select path="city">
                                            <form:options items="${cityList}" itemLabel="name" itemValue="id"/>
                                        </form:select>
                                    </div>


                                    <div class="form-group">
                                        <form:input  path="name" name="name" placeholder="name"/><br/>
                                        ${duplicateName}
                                        <form:errors path="name" name="name" placeholder="name"/>
                                    </div>

                                    <div class="form-group">
                                        <form:textarea path="mission" name="mission" placeholder="mission"/><br/>
                                        <form:errors path="mission" name="mission" placeholder="mission"/>
                                    </div>
                                </td>
                                <td>
                                    <div class="form-group">
                                        <form:checkboxes path="need" items="${organizationNeedList}" itemLabel="name" itemValue="id" element="div"/>
                                    </div>
                                </td>
                                <td>
                                    <div class="form-group">
                                        <form:checkboxes path="target" items="${organizationTargetList}" itemLabel="name" itemValue="id" element="div"/>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <div class="form-group form-group--buttons">
                            <a href="<jsp:include page="../links/hrefAdminOrganizations.jsp"/>" class="btn btn--small" style="visibility: hidden">Anuluj</a>
                            <button class="btn btn--small" type="submit">Dodaj organizację</button>
                        </div>
                    </form:form>
                </section>

                <a name="list"></a>
                <section class="container">
                    <h3 class="slogan--steps-title" style="width: 100%">
                        Lista Organizacji
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
                        <c:forEach items="${organizationList}" var="organization">
                            <tr>
                                <td>${organization.type.name}</td>
                                <td>${organization.name}</td>
                                <td>${organization.mission}</td>
                                <td>${organization.city.name}</td>
                                <td>
                                    <c:forEach items="${organization.need}" var="need">
                                        <div>${need.name}</div>
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:forEach items="${organization.target}" var="target">
                                        <div>${target.name}</div>
                                    </c:forEach>
                                </td>
                                <td>
                                    <a href="<jsp:include page="../links/hrefAdminOrganizationEdit.jsp"/>/${organization.id}" class="btn btn--small">Edytuj</a>
                                    <a href="<jsp:include page="../links/hrefAdminOrganizationDelete.jsp"/>/${organization.id}" class="btn btn--small deleteBtn">Usuń</a>
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
