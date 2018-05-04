<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <body>
        <h2>Actual Activities</h2>
		<table>
		<th>Id</th>
		<th>Name</th>
		<th>Description</th>
		<th>Creation Date</th>
		<th>DeadLine</th>
		<th>Working Time</th>
		<th>User</th>
        <c:forEach var="activity" items="${Activities}">
        <tr>
         <form action="timetracking" method="post">
            <td>${activity.id}</td>
			<td>${activity.name}</td>
			<td><input type="text" name="description"  value="${activity.description}"></td>
			<td>${activity.creationDate}</td>
			<td><input type="text" name="deadline"  value=${activity.deadLine}></td>
			<td><input type="text" name="time"  value=${activity.time}></td>
			<td><input type="text" name="userId"  value=${activity.userId}></td>
			<td>
			    <input type="hidden" name="id" value=${activity.id}>
			    <input type="hidden" name="name" value=${activity.name}>
			    <input type="hidden" name="creationDate" value=${activity.creationDate}>
			    <input type="hidden" name="select" value="selectActual">
				<input type="submit" name="command" value="updateActivity" />
			</td>
			</form>
			<form action="timetracking" method="post">
			<td>
				    <input type="hidden" name="id" value=${activity.id} >
				    <input type="hidden" name="name" value=${activity.name} >
				    <input type="hidden" name="select" value="selectActual">
					<input type="submit" name="command" value="deleteActivity" />
			</td>
			</form>
		</tr>
        </c:forEach>
		</table>
    </body>
</html>