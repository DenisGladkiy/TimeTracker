<%@ page language="java" contentType="text/html;UTF-8" pageEncoding="UTF-8" %>
<html>
<title>Time Tracker</title>
    <body>
    <a HREF="/pages/adminIndex.jsp" style="text-decoration:none" class="A">Admin Page</a>
    <form name="form" method="POST" action="/pages/timetracking">
        <input type="hidden" name="command" value="usersActivities">
        <input type="hidden" name="source" value="userIndex">
        <input type="hidden" name="userId" value=1>
        <A HREF="javascript:document.form.submit()">User Page</A>
    </form>
    </body>
</html>