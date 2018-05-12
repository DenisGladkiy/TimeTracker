<html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script language="javascript">
    function deleteLine(formId){
        var okay=confirm('Do you want to delete the activity?');
        if(okay){
            document.getElementById(formId).submit();
        }
    }
</script>
<style type="text/css">
	td{text-align: center}
</style>
    <body>
        <h2>Requests to remove activities</h2>
		<table width="100%">
		<th>Id</th>
		<th>Name</th>
		<th>Description</th>
		<th>Creation Date</th>
		<th>DeadLine</th>
		<th>User</th>
		<c:set var="formId" value="0" scope="page"/>
		<c:forEach var="activity" items="${Activities}">
		<c:set var="formId" value="${formId+1}" scope="page"/>
        <tr>
            <td>${activity.id}</td>
			<td>${activity.name}</td>
			<td>${activity.description}</td>
			<td>${activity.creationDate}</td>
			<td>${activity.deadLine}</td>
			<td>${activity.userId}</td>
			<form id="${formId}" action="timetracking" method="post">
				<input type="hidden" name="id" value=${activity.id}>
				<input type="hidden" name="name" value="${activity.name}">
				<input type="hidden" name="select" value="removedActivities.jsp">
				<input type="hidden" name="command" value="deleteActivity">
			</form>
			<td>
				<input type="submit" value="Delete" onclick="deleteLine(${formId})"/>
			</td>
			<form action="timetracking" method="post">
			<td>
				<input type="hidden" name="id" value=${activity.id} >
				<input type="hidden" name="name" value="${activity.name}">
				<input type="hidden" name="description" value="${activity.description}">
				<input type="hidden" name="creationDate" value=${activity.creationDate}>
				<input type="hidden" name="deadLine" value=${activity.deadLine}>
				<%--<input type="hidden" name="time" value=${activity.time}>--%>
				<input type="hidden" name="userId" value=${activity.userId}>
				<input type="hidden" name="removed" value="false">
				<input type="hidden" name="completed" value="false">
				<input type="hidden" name="select" value="removedActivities.jsp">
				<input type="hidden" name="command" value="updateActivity">
				<input type="submit" value="Reject Removal" />
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