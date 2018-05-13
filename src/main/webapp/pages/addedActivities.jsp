<html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="user" uri="mytaglib"%>
<script language="javascript">
    function deleteLine(formId){
        var okay=confirm('Do you want to delete the activity?');
        if(okay){
            document.getElementById(formId).submit();
        }
    }
    function acceptBatch() {
        var checkArr = [];
        var check = document.getElementsByName("batch");
        for(var i = 0; i < check.length; ++i){
            var node = check[i];
            if(node.checked == true) {
                checkArr.push(check[i].value);
            }
        }
        document.getElementById("accepted").value=checkArr;
        document.getElementById("sendBatch").submit();
    }
</script>

<style type="text/css">
	td{text-align: center}
</style>
    <body>
        <h2>Requests to add activities</h2>
		<table width="100%">
		<th>Id</th>
		<th>Name</th>
		<th>Description</th>
		<th>Creation Date</th>
		<th>DeadLine</th>
		<th>User Name</th>
		<th>User ID</th>
		<th>Select</th>
		<c:set var="formId" value="0" scope="page"/>
		<c:forEach var="activity" items="${Activities}">
		<c:set var="formId" value="${formId+1}" scope="page"/>
        <tr>
        <form name="acceptForm" action="timetracking" method="post">
            <td>${activity.id}</td>
			<td>${activity.name}</td>
			<td><input type="text" name="description"  value="${activity.description}"></td>
			<td>${activity.creationDate}</td>
			<td><input type="text" name="deadLine"  value=${activity.deadLine}></td>
			<td><user:getName userId="${activity.userId}"/></td>
			<td><input type="text" name="userId"  value=${activity.userId}></td>
			<td><input type="checkbox" name="batch" value="${activity.id};${activity.name}"></td>
			<td>
			    <input type="hidden" name="id" value=${activity.id}>
			    <input type="hidden" name="name" value="${activity.name}">
				<input type="hidden" name="removed" value="false">
				<input type="hidden" name="complete" value="false">
			    <input type="hidden" name="select" value="addedActivities.jsp">
				<input type="hidden" name="command" value="updateActivity">
				<input type="submit" value="Accept" />
			</td>
		</form>
		<form id="${formId}" action="timetracking" method="post">
			<input type="hidden" name="id" value=${activity.id} >
			<input type="hidden" name="name" value="${activity.name}" >
			<input type="hidden" name="select" value="addedActivities.jsp">
			<input type="hidden" name="command" value="deleteActivity">
		</form>
			<td>
				<input type="submit" value="Delete" onclick="deleteLine(${formId})" />
			</td>
		</tr>
        </c:forEach>
			<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
				<td><input type="submit" value="Accept Selected" onclick="acceptBatch()"/></td></tr>
		</table>
		<form action="/pages/adminIndex.jsp" method="post">
			<input type="submit" name="home" value="Go Home Page" />
		</form>
	<form id="sendBatch" action="timetracking" method="post">
		<input type="hidden" name="accepted" id="accepted" >
		<input type="hidden" name="select" value="addedActivities.jsp">
		<input type="hidden" name="command"  value="acceptActivities"/>
	</form>

    </body>
</html>