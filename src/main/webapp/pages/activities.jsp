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
        <h2>Actual Activities</h2>
		<table width="100%">
		<th>Id</th>
		<th>Name</th>
		<th>Description</th>
		<th>Creation Date</th>
		<th>DeadLine</th>
		<th>Working Time</th>
		<th>User</th>
		<th>Complete</th>
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
			<td>${activity.time}</td>
			<td><input type="text" name="userId"  value=${activity.userId}></td>
			<td>
                <input type="checkbox" name="complete"/>
            </td>
			<td>
			    <input type="hidden" name="id" value=${activity.id}>
			    <input type="hidden" name="name" value="${activity.name}">
			    <input type="hidden" name="creationDate" value=${activity.creationDate}>
				<input type="hidden" name="removed" value="false">
			    <input type="hidden" name="select" value="activities.jsp">
				<input type="submit" name="command" value="updateActivity" />
			</td>
			</form>
			<td>
				<form id="${formId}" action="timetracking" method="post">
				    <input type="hidden" name="id" value=${activity.id} >
				    <input type="hidden" name="name" value="${activity.name}">
				    <input type="hidden" name="select" value="activities.jsp">
					<input type="hidden" name="command" value="deleteActivity">
				</form>
					<input type="submit" value="Delete activity" onclick="deleteLine(${formId})" />
			</td>
		</tr>
        </c:forEach>
		</table>
		<form action="/pages/adminIndex.jsp" method="post">
			<input type="submit" name="home" value="Go Home Page" />
		</form>
    </body>
</html>