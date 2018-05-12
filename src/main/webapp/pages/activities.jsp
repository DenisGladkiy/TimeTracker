<html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="time" uri="timetablib"%>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /></head>
<script language="javascript">
    function deleteLine(formId){
        var okay=confirm('Do you want to delete the activity?');
        if(okay){
            document.getElementById(formId).submit();
        }
    }
</script>
<style type="text/css">
	td{text-align: center;
		vertical-align: middle;}
</style>
    <body>
        <h2>Actual Activities</h2>
		<table width="100%">
		<th>Id</th>
		<th>Name</th>
		<th>Description</th>
		<th>Creation Date</th>
		<th>DeadLine</th>
		<th>Working Time(h)</th>
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
			<td><time:getHours activity="${activity}"/></td>
			<td><input type="text" name="userId"  value=${activity.userId}></td>
			<td>
                <input type="checkbox" name="complete"/>
            </td>
			<td>
			    <input type="hidden" name="id" value=${activity.id}>
			    <input type="hidden" name="name" value="${activity.name}">
			    <input type="hidden" name="creationDate" value=${activity.creationDate}>
				<%--<input type="hidden" name="time" value=${activity.time}>--%>
				<input type="hidden" name="removed" value="false">
			    <input type="hidden" name="select" value="activities.jsp">
				<input type="hidden" name="command" value="updateActivity" />
				<input type="submit" value="Save" />
			</td>
			</form>
			<form id="${formId}" action="timetracking" method="post">
				<input type="hidden" name="id" value=${activity.id} >
				<input type="hidden" name="name" value="${activity.name}">
				<input type="hidden" name="select" value="activities.jsp">
				<input type="hidden" name="command" value="deleteActivity">
			</form>
			<td>
				<input type="submit" value="Delete" onclick="deleteLine(${formId})" />
			</td>
		</tr>
        </c:forEach>
		</table>
		<form action="/pages/adminIndex.jsp" method="post">
			<input type="submit" name="home" value="Go Home Page" />
		</form>
		<form action="timetracking" method="post">
			<jsp:useBean id="list" scope="session" class="com.epam.timetracking.mvc.controller.command.executors.utils.ActivitySort">
			<jsp:setProperty name="list" property="activities" value="${Activities}"/>
			</jsp:useBean>
			<input type="hidden" name="command" value="selectActivity" />
			<input type="hidden" name="select" value="activities.jsp" />
			<input type="submit"  value="Test bean" />
		</form>
    </body>
</html>