<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<footer>
    <div class="contact">
        <h2>Skontaktuj się z nami</h2>
        <h3>Formularz kontaktowy</h3>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <form class="form--contact" method="post" action="/contact/send">
            <div class="form-group form-group--50"><input type="text" name="name" placeholder="Imię i Nazwisko" maxlength="31" value="${name}"
                                                          required/></div>
            <div class="form-group form-group--50"><input type="email" name="email" placeholder="E-mail" maxlength="31" value="${email}" required/></div>
            <div class="form-group"><textarea name="text" placeholder="Wiadomość" rows="5" maxlength="255" required>${text}</textarea></div>
            <h3 style="color: #b92c28;">${captchaNotChecked}</h3>
            <div class="g-recaptcha form-group" data-sitekey="6LeWZa4UAAAAAB6oRn5dNo86whatF2wBqS9nuOmo"></div>
            <br/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn" type="submit">Wyślij</button>
        </form>
    </div>
    <div class="bottom-line">
        <span class="bottom-line--copy" style="text-align: center">
            <a href="https://github.com/Cutter72" target="_blank" style="text-decoration: underline">
            Copyright &copy; 2019, Paweł Drelich<br/>
        </a>

            * - strona zrobiona w celu rozwijania umiejętności programistycznych autora,<br/>
            zarejestrowane tu organizacje są fikcyjne a zgłaszane formularze nie zostają nikomu przekazywane a jedynie zapisywane do bazy<br/>

        </span>
        <div class="bottom-line--icons">
            <a href="#" class="btn btn--small"><img src="<c:url value="../../images/icon-facebook.svg"/>"/></a> <a
                href="#" class="btn btn--small"><img src="<c:url value="../../images/icon-instagram.svg"/>"/></a>
        </div>
    </div>
</footer>