<html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="user" uri="mytaglib" %>
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
    <table width="100%">
        <h2>Completed activities</h2>
		<th>Id</th>
		<th>Name</th>
		<th>Description</th>
		<th>Creation Date</th>
		<th>DeadLine</th>
		<th>Working<br> Time(h)</th>
		<th>User Name</th>
		<th>User ID</th>
		<c:set var="formId" value="0" scope="page"/>
		<c:forEach var="activity" items="${Activities}">
		<c:set var="formId" value="${formId+1}" scope="page"/>
        <tr>
        <form action="timetracking" method="post">
            <td>${activity.id}</td>
			<td>${activity.name}</td>
			<td>${activity.description}</td>
			<td>${activity.creationDate}</td>
			<td>${activity.deadLine}</td>
			<td><user:getHours activity="${activity}"/></td>
			<td><user:getName userId="${activity.userId}"/></td>
			<td>${activity.userId}</td>
			<td>
			    <input type="hidden" name="id" value=${activity.id}>
            	<input type="hidden" name="name" value="${activity.name}">
            	<input type="hidden" name="description" value="${activity.description}">
            	<input type="hidden" name="creationDate" value=${activity.creationDate}>
            	<input type="hidden" name="deadLine" value=${activity.deadLine}>
            	<%--<input type="hidden" name="time" value=${activity.time}>--%>
            	<input type="hidden" name="userId" value=${activity.userId}>
			    <input type="hidden" name="removed" value="false">
			    <input type="hidden" name="select" value="completedActivities.jsp">
				<input type="hidden" name="command" value="updateActivity">
				<input type="submit" value="Continue work" />
			</td>
			</form>
			<form id="${formId}" action="timetracking" method="post">
				<input type="hidden" name="id" value=${activity.id} >
				<input type="hidden" name="name" value=${activity.name} >
				<input type="hidden" name="select" value="completedActivities.jsp">
				<input type="hidden" name="command" value="deleteActivity">
			</form>
			<td>
				<input type="submit" value="Delete" onclick="deleteLine(${formId})"/>
			</td>
		</tr>
        </c:forEach>
		</table>
	<form action="/pages/adminIndex.jsp" method="post">
		<input type="submit" name="home" value="Go Home Page" />
	</form>
    </body>
</html>