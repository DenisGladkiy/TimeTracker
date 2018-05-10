<%@ page language="java" contentType="text/html;UTF-8" pageEncoding="UTF-8" %>
<html>
<title>Time Tracker</title>
    <body>
    <h1 align="center">Welcome to Time Tracking system</h1>
    <table align="center">
        </tr>
            <form name="loginForm" method="POST" action="/login">
                <tr><td>Login</td></tr>
                <tr><td><input type="text" name="login"></td></tr>
                <tr><td>Password</td></tr>
                <tr><td><input type="text" name="password"></td></tr>
                <tr><td><input type="hidden" name="command" value="loginData"></td></tr>
                <tr><td><input type="submit" value="Log In"></td></tr>
            </form>
    </table>
    </body>
</html>