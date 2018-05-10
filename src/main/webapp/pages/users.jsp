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
        <h2>Users</h2>
		<table>
		<th>Id</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>E-mail</th>
		<th>Role</th>
		<c:set var="formId" value="0" scope="page"/>
		<c:forEach var="user" items="${Users}">
		<c:set var="formId" value="${formId+1}" scope="page"/>
        <tr>
            <td>${user.id}</td>
			<td>${user.firstName}</td>
			<td>${user.lastName}</td>
			<td>${user.email}</td>
			<td>${user.role}</td>
			<form action="timetracking" method="post">
			<td>
			    <input type="hidden" name="userId" value=${user.id}>
			    <input type="hidden" name="firstName" value=${user.firstName}>
			    <input type="hidden" name="lastName" value=${user.lastName}>
				<input type="hidden" name="select" value="/pages/activitiesByUser.jsp">
			    <input type="hidden" name="command" value="usersActivities">
				<input type="submit" value="View user's activities" />
			</td>
			</form>
			<form id="${formId}" action="timetracking" method="post">
			<td>
				<input type="hidden" name="id" value=${user.id}>
				<input type="hidden" name="firstName" value=${user.firstName}>
				<input type="hidden" name="command" value="deleteUser" />
                <input type="submit" value="deleteUser" onclick="deleteLine(${formId})"/>
			</td>
			</form>
		</tr>
        </c:forEach>
		</table>
		<A HREF="/pages/adminIndex.jsp" style="text-decoration:none" class="A">Go Home</A>
    </body>
</html>