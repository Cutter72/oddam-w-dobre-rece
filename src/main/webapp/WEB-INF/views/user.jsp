<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pl-PL">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/style.css" />
    <title>User</title>
</head>
<body>

<jsp:include page="../components/header.jsp"/>

<h1>USER page</h1>

<form action="/logout" method="post">
    <input class="btn btn--without-border active" type="submit" value="Wyloguj">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<jsp:include page="../components/footer.jsp"/>
<script src="js/app.js"></script>
</body>
</html>