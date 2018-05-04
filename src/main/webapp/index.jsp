<%@ page language="java" contentType="text/html;UTF-8" pageEncoding="UTF-8" %>
<html>
<title>Time Tracker</title>
    <body>
        <h2>Time Tracking</h2>
        <table>
        <tr>
            <form method="POST" action="timetracking">
            <td>
                <input type="hidden" name="command" value="selectActivity"/>
                <input type="hidden" name="select" value="selectActual"/>
                <input type="submit" value="View actual activities"/>
            </td>
            </form>
            <form method="POST" action="timetracking">
            <td>
                <input type="hidden" name="command" value="selectActivity"/>
                <input type="hidden" name="select" value="selectAdded"/>
                <input type="submit" value="View add requests"/>
            </td>
            </form>
            <form method="POST" action="timetracking">
            <td>
                 <input type="hidden" name="command" value="selectActivity"/>
                 <input type="hidden" name="select" value="selectToRemove"/>
                 <input type="submit" value="View remove requests"/>
            </td>
            </form>
            <form method="POST" action="timetracking">
            <td>
                 <input type="hidden" name="command" value="selectActivity"/>
                 <input type="hidden" name="select" value="selectCompleted"/>
                 <input type="submit" value="View completed"/>
            </td>
            </form>
        </tr>
        <tr>
        <form method="POST" action="timetracking">
        <td>
            <input type="hidden" name="command" value="selectUser"/>
            <input type="submit" value="View users"/>
        </td>
        </form>
        </tr>
        </table>
		<table>
		<tr>
		    <td><h2>Create Activity</h2></td>
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
            <input type="hidden" name="command" value="insertActivity"/>
            <td><input type="submit" /></td>
        </form>
        </tr>
        <tr>
            <td><h2>Create User</h2></td>
        </tr>
        <tr>
            <td>First Name</td>
            <td>Last Name</td>
            <td>E-mail</td>
            <td>Password</td>
        </tr>
        <tr>
        <form method="POST" action="timetracking">
            <td><input type="text" name="firstName" /></td>
            <td><input type="text" name="lastName" /></td>
            <td><input type="text" name="email" /></td>
            <td><input type="text" name="pass" /></td>
            <input type="hidden" name="command" value="insertUser"/>
            <td><input type="submit" /></td>
            </form>
        </tr>
        </table>
    </body>
</html>