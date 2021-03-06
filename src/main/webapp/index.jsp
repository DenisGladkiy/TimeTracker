<%@ page language="java" contentType="text/html;UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language :
                                not empty language ? language :
                                pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="bundle" />
<html>
<title>Time Tracker</title>
<style type="text/css">
.topcorner{
    position:absolute;
    top:1%;
    right:1%;}
* { font-family: Arial; }
    body{
        background: #F5F5F5;
    }
</style>
    <body>
    <h1 align="center"><fmt:message key="index.welcome"/> </h1>
    <table align="center">
        <form name="loginForm" method="POST" action="/login">
            <tr><td><fmt:message key="index.login"/> </td></tr>
            <tr><td><input type="text" name="login" required></td></tr>
            <tr><td><fmt:message key="index.password"/> </td></tr>
            <tr><td><input type="password" name="password" required></td></tr>
            <tr><td><input type="hidden" name="command" value="loginData"></td></tr>
            <tr><td><input type="submit" value="<fmt:message key="index.button.login"/>" ></td></tr>
        </form>
    </table>
    <form class="topcorner" method="post">
        <select id="language" name="language" onchange="submit()">
            <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
            <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
        </select>
    </form>
    </body>
</html>