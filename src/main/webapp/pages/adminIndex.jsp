<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java"%>
<%@ page import="com.epam.timetracking.utils.ConstantsImpl" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="bundle" />
<html>
<title>Time Tracker</title>
<body>
<h2 align="center"><fmt:message key="adminIndex.tracker"/> </h2>
<table width="100%">
    <tr>
        <td>
            <form id="actual" method="POST" action="timetracking">
                <input type="hidden" name="command" value="selectActivity"/>
                <input type="hidden" name="select" value=${ConstantsImpl.ACTIVITIES}>
                <a href="#" onclick="document.getElementById('actual').submit()">
                    <h3><fmt:message key="adminIndex.actualActivities"/></h3></a>
            </form>
        </td>
        <td>
            <form id="added" method="POST" action="timetracking">
                <input type="hidden" name="command" value="selectActivity"/>
                <input type="hidden" name="select" value=${ConstantsImpl.ADDED_ACTIVITIES}>
                <a href="#" onclick="document.getElementById('added').submit()">
                    <h3><fmt:message key="adminIndex.addActivity"/></h3></a>
            </form>
        </td>
        <td>
            <form id="removed" method="POST" action="timetracking">
                <input type="hidden" name="command" value="selectActivity"/>
                <input type="hidden" name="select" value=${ConstantsImpl.REMOVED_ACTIVITIES}>
                <a href="#" onclick="document.getElementById('removed').submit()">
                    <h3><fmt:message key="adminIndex.removeActivity"/></h3></a>
            </form>
        </td>
        <td>
            <form id="completed" method="POST" action="timetracking">
                <input type="hidden" name="command" value="selectActivity"/>
                <input type="hidden" name="select" value=${ConstantsImpl.COMPLETED_ACTIVITIES}>
                <a href="#" onclick="document.getElementById('completed').submit()">
                    <h3><fmt:message key="adminIndex.completedActivities"/></h3></a>
            </form>
        </td>
        <td>
            <form id="users" method="POST" action="timetracking">
                <input type="hidden" name="select" value=${ConstantsImpl.USERS}>
                <input type="hidden" name="command" value="selectUser"/>
                <a href="#" onclick="document.getElementById('users').submit()">
                    <h3><fmt:message key="adminIndex.usersList"/></h3></a>
            </form>
        </td>
    </tr>
</table>
<table style="float: left">
    <caption><h4><fmt:message key="adminIndex.createActivity"/></h4></caption>
    <form method="POST" action="timetracking">
        <tr><td><fmt:message key="userIndex.name"/></td><td><input type="text" name="name" required /></td></tr>
        <tr><td><fmt:message key="userIndex.description"/></td><td>
        <textarea rows="3" cols=${language == "ru" ? "19" : "22"} name="description" required></textarea></td></tr>
        <tr><td><fmt:message key="userIndex.deadLine"/></td><td><input type="date" name="deadLine" /></td></tr>
        <tr><td><fmt:message key="adminIndex.userId"/></td><td><input type="text" name="userId" /></td></tr>
        <input type="hidden" name="select" value=${ConstantsImpl.ADMIN_INDEX}>
        <input type="hidden" name="command" value="insertActivity"/>
        <tr><td></td><td><input type="submit" value="<fmt:message key="adminIndex.button.create"/>"/></td></tr>
    </form>
</table>
<table style="float: right">
    <caption><h4><fmt:message key="adminIndex.createUser"/></h4></caption>
    <form method="POST" action="timetracking">
        <tr><td><fmt:message key="adminIndex.firstName"/></td><td><input type="text" name="firstName" required /></td></tr>
        <tr><td><fmt:message key="adminIndex.lastName"/></td><td><input type="text" name="lastName" required/></td></tr>
        <tr><td>E-mail</td><td><input type="text" name="email" required/></td></tr>
        <tr><td><fmt:message key="index.password"/></td><td><input type="text" name="pass" required/></td></tr>
        <tr><td><fmt:message key="adminIndex.role"/></td><td><input type="text" name="role" required/></td></tr>
        <input type="hidden" name="command" value="insertUser"/>
        <tr><td></td><td><input type="submit" value="<fmt:message key="adminIndex.button.create"/>" /></td></tr>
    </form>
</table>
<style type="text/css">
    textarea {
        resize: none;
    }
</style>
</body>
</html>

