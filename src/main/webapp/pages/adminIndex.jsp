<html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<title>Time Tracker</title>
<body>
<h2 align="center">Time Tracking</h2>
<table width="100%">
    <tr>
        <td>
            <form id="actual" method="POST" action="timetracking">
                <input type="hidden" name="command" value="selectActivity"/>
                <input type="hidden" name="select" value="activities.jsp"/>
                <a href="#" onclick="document.getElementById('actual').submit()"><h3>Actual activities</h3></a>
            </form>
        </td>
        <td>
            <form id="added" method="POST" action="timetracking">
                <input type="hidden" name="command" value="selectActivity"/>
                <input type="hidden" name="select" value="addedActivities.jsp"/>
                <a href="#" onclick="document.getElementById('added').submit()"><h3>Add activity requests</h3></a>
            </form>
        </td>
        <td>
            <form id="removed" method="POST" action="timetracking">
                <input type="hidden" name="command" value="selectActivity"/>
                <input type="hidden" name="select" value="removedActivities.jsp"/>
                <a href="#" onclick="document.getElementById('removed').submit()"><h3>Remove activity requests</h3></a>
            </form>
        </td>
        <td>
            <form id="completed" method="POST" action="timetracking">
                <input type="hidden" name="command" value="selectActivity"/>
                <input type="hidden" name="select" value="completedActivities.jsp"/>
                <a href="#" onclick="document.getElementById('completed').submit()"><h3>Completed activities</h3></a>
            </form>
        </td>
        <td>
            <form id="users" method="POST" action="timetracking">
                <input type="hidden" name="select" value="userIndex.jsp"/>
                <input type="hidden" name="command" value="selectUser"/>
                <a href="#" onclick="document.getElementById('users').submit()"><h3>Users</h3></a>
            </form>
        </td>
    </tr>
</table>
<table style="float: left">
    <tr>
        <td><big>Create Activity</big></td>
    </tr>
    <form method="POST" action="timetracking">
        <tr><td>Name</td><td><input type="text" name="name" /></td></tr>
        <tr><td>Description</td><td><textarea rows="3" cols="22" name="description"></textarea></td></tr>
        <tr><td>Deadline</td><td><input type="text" name="deadLine" /></td></tr>
        <tr><td>User</td><td><input type="text" name="userId" /></td></tr>
        <input type="hidden" name="select" value="adminIndex.jsp"/>
        <input type="hidden" name="command" value="insertActivity"/>
        <tr><td></td><td><input type="submit" value="Create"/></td></tr>
    </form>
</table>
<table style="float: right">
    <tr>
        <td><big>Create User</big></td>
    </tr>
    <form method="POST" action="timetracking">
        <tr><td>First Name</td><td><input type="text" name="firstName" /></td></tr>
        <tr><td>Last Name</td><td><input type="text" name="lastName" /></td></tr>
        <tr><td>E-mail</td><td><input type="text" name="email" /></td></tr>
        <tr><td>Password</td><td><input type="text" name="pass" /></td></tr>
        <tr><td>Role</td><td><input type="text" name="role" /></td></tr>
        <input type="hidden" name="command" value="insertUser"/>
        <tr><td></td><td><input type="submit" value="Create" /></td></tr>
    </form>
</table>
<style type="text/css">
    textarea {
        resize: none;
    }
</style>
</body>
</html>

