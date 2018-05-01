<%@ page language="java" contentType="text/html;UTF-8" pageEncoding="UTF-8" %>
<html>
<title>Time Tracker</title>
    <body>
        <h2>Time Tracking</h2>
        <form method="POST" action="timetracking">
            <input type="hidden" name="command" value="select"/>
            <input type="hidden" name="select" value="selectall"/>
            <input type="submit" value="Select all"/>
        </form>
		<table border="1">
        <form method="POST" action="timetracking">
            <b>Name</b>
            <input type="text" name="name" />
            <b>Description</b>
            <input type="text" name="description" />
            <b>Deadline</b>
            <input type="text" name="deadLine" />
            <b>User</b>
            <input type="text" name="userId" />
            <input type="hidden" name="command" value="insert"/>
            <input type="submit" />
        </form>
        </table>
    </body>
</html>
