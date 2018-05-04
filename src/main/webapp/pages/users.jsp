<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <body>
        <h2>Users</h2>
		<table>
		<th>Id</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>E-mail</th>
        <c:forEach var="user" items="${Users}">
        <tr>
            <td>${user.id}</td>
			<td>${user.firstName}</td>
			<td>${user.lastName}</td>
			<td>${user.email}</td>
			<form action="timetracking" method="post">
			<td>
			    <input type="hidden" name="id" value=${user.id}>
			    <input type="hidden" name="firstName" value=${user.firstName}>
			    <input type="hidden" name="lastName" value=${user.lastName}>
			    <input type="hidden" name="command" value="usersActivities">
				<input type="submit" value="View user's activities" />
			</td>
			</form>
			<form action="timetracking" method="post">
			<td>
				<input type="hidden" name="id" value=${user.id}>
                <input type="hidden" name="firstName" value=${user.firstName}>
                <input type="submit" name="command" value="deleteUser" />
			</td>
			</form>
		</tr>
        </c:forEach>
		</table>
    </body>
</html>