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
<table>
    <tr>
        <td><big>Create Activity</big></td>
    </tr>
    <tr>
        <td>Name</td>
        <td>Description</td>
        <td>Deadline</td>
        <td>User</td>
    </tr>
    <tr>
        <form method="POST" action="timetracking">
            <td><input type="text" name="name" /></td>
            <td><input type="text" name="description" /></td>
            <td><input type="text" name="deadLine" /></td>
            <td><input type="text" name="userId" /></td>
            <input type="hidden" name="select" value="adminIndex.jsp"/>
            <input type="hidden" name="command" value="insertActivity"/>
            <td><input type="submit" value="Create"/></td>
        </form>
    </tr>
    <tr><br></tr>
    <tr>
        <td><big>Create User</big></td>
    </tr>
    <tr>
        <td>First Name</td>
        <td>Last Name</td>
        <td>E-mail</td>
        <td>Password</td>
        <td>Role</td>
    </tr>
    <tr>
        <form method="POST" action="timetracking">
            <td><input type="text" name="firstName" /></td>
            <td><input type="text" name="lastName" /></td>
            <td><input type="text" name="email" /></td>
            <td><input type="text" name="pass" /></td>
            <td><input type="text" name="role" /></td>
            <input type="hidden" name="command" value="insertUser"/>
            <td><input type="submit" value="Create" /></td>
        </form>
    </tr>
</table>
</body>
</html>

