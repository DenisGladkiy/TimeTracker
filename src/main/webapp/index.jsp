<%@ page language="java" contentType="text/html;UTF-8" pageEncoding="UTF-8" %>
<html>
<title>Time Tracker</title>
    <body>
        <h2>Time Tracking</h2>
        <FORM action="timetracking">
            <INPUT type="submit" value="Execute">
        </FORM>
		<table border="1">
      <form method="POST" action="timetracking">
		<b>Name</b>
        <input type="text" name="name" />
		<b>Description</b>
		<input type="text" name="description" />
		<b>Deadline</b>
		<input type="text" name="deadline" />
		<b>User</b>
		<input type="text" name="user" />
		<input type="submit" />
      </form>
    </table>
    </body>
</html>
