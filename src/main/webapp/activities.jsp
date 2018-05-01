<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <body>
        <h2>Time Tracking</h2>
		<table style="border: 1px solid;">
		<style type="text/css">
		table, th, td {
			border: 1px solid black;
			border-collapse: collapse;
		}
		</style>
		<th>Id</th>
		<th>Name</th>
		<th>Description</th>
		<th>Creation Date</th>
		<th>DeadLine</th>
		<th>Working Time</th>
		<th>User</th>
		<th>Accepted</th>
		<th>Remove</th>
        <c:forEach var="activity" items="${Activities}">
        <tr>
        <form action="timetracking" method="post">
            <td>${activity.id}</td>
			<td>${activity.name}</td>
			<td><input type="text" name="description"  value=${activity.description}></td>
			<td>${activity.creationDate}</td>
			<td><input type="text" name="deadline"  value=${activity.deadLine}></td>
			<td><input type="text" name="time"  value=${activity.time}></td>
			<td><input type="text" name="user"  value=${activity.userId}></td>
			<td>
				<c:choose>
					<c:when test="${activity.addRequest==false}">
						<input type="checkbox" name="accept" checked/>
					</c:when>
					<c:otherwise>
						<input type="checkbox" name="accept"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<c:choose>
					<c:when test="${activity.removeRequest==true}">
						<input type="checkbox" name="accept" checked/>
					</c:when>
					<c:otherwise>
						<input type="checkbox" name="accept"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<input type="submit" name="command" value="update" />
			</td>
			</form>
			<td>
				<form action="timetracking" method="post">
					<input type="submit" name="command" value="delete" />
					<input type="hidden" name="name" value=${activity.name} />
				</form>
			</td>
		</tr>
        </c:forEach>
		</table>
    </body>
</html>