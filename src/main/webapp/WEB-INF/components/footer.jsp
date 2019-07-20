<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<footer>
    <div class="contact">
        <h2>Skontaktuj się z nami</h2>
        <h3>Formularz kontaktowy</h3>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <form class="form--contact" method="post" action="/contact/send">
            <div class="form-group form-group--50"><input type="text" name="name" placeholder="Imię i Nazwisko" required/></div>
            <div class="form-group form-group--50"><input type="text" name="email" placeholder="E-mail" required/></div>
            <div class="form-group"><textarea name="text" placeholder="Wiadomość" rows="5" required></textarea></div>
            <div class="g-recaptcha form-group" data-sitekey="6LeWZa4UAAAAAB6oRn5dNo86whatF2wBqS9nuOmo"></div>
            <br/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn" type="submit">Wyślij</button>
        </form>
    </div>
    <div class="bottom-line">
        <a href="https://github.com/Cutter72" target="_blank"><span class="bottom-line--copy">Copyright &copy; 2019, Paweł Drelich</span></a>
        <div class="bottom-line--icons">
            <a href="#" class="btn btn--small"><img src="<c:url value="../../images/icon-facebook.svg"/>"/></a> <a href="#" class="btn btn--small"><img src="<c:url value="../../images/icon-instagram.svg"/>"/></a>
        </div>
    </div>
</footer>