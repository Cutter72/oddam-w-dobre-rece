<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pl-PL">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin</title>
</head>
<body>
<h1>Adminpage</h1>
<jsp:include page="../components/header.jsp"/>
<a href="/logout">
    <button>Wyloguj</button>
</a>
<jsp:include page="../components/footer.jsp"/>
</body>
</html>