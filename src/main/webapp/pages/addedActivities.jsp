<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script language="javascript">
    function deleteLine(formId){
        var okay=confirm('Do you want to delete the record?');
        if(okay){
            document.getElementById(formId).submit();
        }
    }
</script>
    <body>
        <h2>Requests to add activities</h2>
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
         <form action="timetracking" method="post">
            <td>${activity.id}</td>
			<td>${activity.name}</td>
			<td><input type="text" name="description"  value="${activity.description}"></td>
			<td>${activity.creationDate}</td>
			<td><input type="text" name="deadLine"  value=${activity.deadLine}></td>
			<td><input type="text" name="userId"  value=${activity.userId}></td>
			<td>
			    <input type="hidden" name="id" value=${activity.id}>
			    <input type="hidden" name="name" value="${activity.name}">
				<input type="hidden" name="removed" value="false">
				<input type="hidden" name="complete" value="false">
			    <input type="hidden" name="select" value="addedActivities.jsp">
				<input type="hidden" name="command" value="updateActivity">
				<input type="submit" value="acceptActivity" />
			</td>
			</form>
			<form id="${formId}" action="timetracking" method="post">
			<td>
				<input type="hidden" name="id" value=${activity.id} >
				<input type="hidden" name="name" value="${activity.name}" >
				<input type="hidden" name="select" value="addedActivities.jsp">
				<input type="hidden" name="command" value="deleteActivity">
				<input type="submit" value="deleteActivity" onclick="deleteLine(${formId})" />
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