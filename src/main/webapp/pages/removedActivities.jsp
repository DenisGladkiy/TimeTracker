<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <body>
        <h2>Requests to remove activities</h2>
		<table width="100%">
		<th>Id</th>
		<th>Name</th>
		<th>Description</th>
		<th>Creation Date</th>
		<th>DeadLine</th>
		<th>User</th>
        <c:forEach var="activity" items="${Activities}">
        <tr>
         <form action="timetracking" method="post">
            <td>${activity.id}</td>
			<td>${activity.name}</td>
			<td>${activity.description}</td>
			<td>${activity.creationDate}</td>
			<td>${activity.deadLine}</td>
			<td>${activity.userId}</td>
			<td>
			    <input type="hidden" name="id" value=${activity.id}>
			    <input type="hidden" name="name" value="${activity.name}">
			    <input type="hidden" name="select" value="removedActivities.jsp">
				<input type="submit" name="command" value="deleteActivity" />
			</td>
			</form>
			<form action="timetracking" method="post">
			<td>
				<input type="hidden" name="id" value=${activity.id} >
				<input type="hidden" name="name" value="${activity.name}">
				<input type="hidden" name="description" value="${activity.description}">
				<input type="hidden" name="creationDate" value=${activity.creationDate}>
				<input type="hidden" name="deadLine" value=${activity.deadLine}>
				<input type="hidden" name="time" value=${activity.time}>
				<input type="hidden" name="userId" value=${activity.userId}>
				<input type="hidden" name="removed" value="false">
				<input type="hidden" name="completed" value="false">
				<input type="hidden" name="select" value="removedActivities.jsp">



				<input type="hidden" name="command" value="updateActivity">
				<input type="submit" value="rejectRemoval" />
			</td>
			</form>
		</tr>
        </c:forEach>
		</table>
		<form action="/pages/adminIndex.jsp" method="post">
			<input type="submit" name="home" value="Go Home Page" />
		</form>
    </body>
</html>