<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<header>
    <nav class="container container--70">
        <ul class="nav--actions">
            <li><a href="<jsp:include page="../links/hrefLogin.jsp"/>" class="btn btn--small btn--without-border">Zaloguj</a></li>
            <li><a href="<jsp:include page="../links/hrefRegister.jsp"/>" class="btn btn--small btn--highlighted">Załóż konto</a></li>
            <li><a href="<jsp:include page="../links/hrefUserCheck.jsp"/>" class="btn btn--small btn--without-border">Moje konto</a></li>
        </ul>

        <ul>
            <li><a href="/" class="btn btn--without-border active">Start</a></li>
            <li><a href="<jsp:include page="../links/hrefWhat.jsp"/>" class="btn btn--without-border">O co chodzi?</a></li>
            <li><a href="<jsp:include page="../links/hrefAbout.jsp"/>" class="btn btn--without-border">O nas</a></li>
            <li><a href="<jsp:include page="../links/hrefOrganizations.jsp"/>" class="btn btn--without-border">Fundacje i organizacje</a></li>
            <li><a href="<jsp:include page="../links/hrefContact.jsp"/>" class="btn btn--without-border">Kontakt</a></li>
        </ul>
    </nav>
</header>