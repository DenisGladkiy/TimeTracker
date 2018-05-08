<%@ page language="java" contentType="text/html;UTF-8" pageEncoding="UTF-8" %>
<html>
<title>Time Tracker</title>
    <body>
    <table width="100%">
        <tr>
            <td>
                <a HREF="/pages/adminIndex.jsp"><h3>Admin Page</h3></a>
            </td>
            <td>
                <form name="form" method="POST" action="/pages/timetracking">
                <input type="hidden" name="command" value="usersActivities">
                <input type="hidden" name="select" value="userIndex.jsp">
                <input type="hidden" name="userId" value=1>
                <A HREF="javascript:document.form.submit()"><h3>User Page</h3></A>
                </form>
            </td>
            <td>
                <a HREF="/pages/login.jsp"><h3>LogIn</h3></a>
            </td>
            <td>
                <a HREF="/pages/logout.jsp"><h3>LogOut</h3></a>
            </td>
        </tr>
    </table>
    </body>
</html>