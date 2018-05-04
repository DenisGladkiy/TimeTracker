<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <body>
    <table>
        <h2>Completed activities</h2>
		<th>Id</th>
		<th>Name</th>
		<th>Description</th>
		<th>Creation Date</th>
		<th>DeadLine</th>
		<th>Working Time</th>
		<th>User</th>
		<th>Completed</th>
        <c:forEach var="activity" items="${Activities}">
        <tr>
        <form action="timetracking" method="post">
            <td>${activity.id}</td>
			<td>${activity.name}</td>
			<td>${activity.description}</td>
			<td>${activity.creationDate}</td>
			<td>${activity.deadLine}</td>
			<td>${activity.time}</td>
			<td>${activity.userId}</td>
			<td>
				<c:choose>
					<c:when test="${activity.completed==true}">
						<input type="checkbox" name="completed" checked/>
					</c:when>
					<c:otherwise>
						<input type="checkbox" name="completed"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td>
			    <input type="hidden" name="id" value=${activity.id}>
            	<input type="hidden" name="name" value=${activity.name}>
            	<input type="hidden" name="description" value=${activity.description}>
            	<input type="hidden" name="creationDate" value=${activity.creationDate}>
            	<input type="hidden" name="deadLine" value=${activity.deadLine}>
            	<input type="hidden" name="time" value=${activity.time}>
            	<input type="hidden" name="userId" value=${activity.userId}>
			    <input type="hidden" name="accept" value=${activity.addRequest}>
			    <input type="hidden" name="remove" value=${activity.removeRequest}>
			    <input type="hidden" name="select" value="selectCompleted">
				<input type="submit" name="command" value="updateActivity" />
			</td>
			</form>
			<form action="timetracking" method="post">
			<td>
				    <input type="hidden" name="id" value=${activity.id} >
				    <input type="hidden" name="name" value=${activity.name} >
					<input type="submit" name="command" value="deleteActivity" />
			</td>
			</form>
		</tr>
        </c:forEach>
		</table>
    </body>
</html>