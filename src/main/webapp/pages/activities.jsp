<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="user" uri="mytaglib"%>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="bundle" />
<html>
<title>Time Tracker</title>
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
        <h2 align="center"><fmt:message key="adminIndex.actualActivities"/> </h2>
		<table width="100%">
		<th><fmt:message key="userIndex.id"/></th>
		<th><fmt:message key="userIndex.name"/></th>
		<th><fmt:message key="userIndex.description"/></th>
		<th><fmt:message key="userIndex.creationDate"/></th>
		<th><fmt:message key="userIndex.deadLine"/></th>
		<th><fmt:message key="userIndex.workingTime"/></th>
		<th><fmt:message key="activities.userName"/></th>
		<th><fmt:message key="adminIndex.userId"/></th>
		<th><fmt:message key="userIndex.complete"/></th>
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
			<td><user:getHours activity="${activity}"/></td>
			<td><user:getName userId="${activity.userId}"/></td>
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
				<input type="submit" value="<fmt:message key="userIndex.save"/>" />
			</td>
			</form>
			<form id="${formId}" action="timetracking" method="post">
				<input type="hidden" name="id" value=${activity.id} >
				<input type="hidden" name="name" value="${activity.name}">
				<input type="hidden" name="select" value="activities.jsp">
				<input type="hidden" name="command" value="deleteActivity">
			</form>
			<td>
				<input type="submit" value="<fmt:message key="activities.button.delete"/>" onclick="deleteLine(${formId})" />
			</td>
		</tr>
        </c:forEach>
		</table>
		<A HREF="/pages/adminIndex.jsp" ><fmt:message key="activities.homePage"/></A>
		<%--<form action="/pages/adminIndex.jsp" method="post">
			<input type="submit" name="home" value="<fmt:message key="activities.homePage"/>" />
		</form>--%>
		<%--<form action="timetracking" method="post">
			<jsp:useBean id="list" scope="session" class="com.epam.timetracking.mvc.controller.command.executors.utils.ActivitySort">
			<jsp:setProperty name="list" property="activities" value="${Activities}"/>
			</jsp:useBean>
			<input type="hidden" name="command" value="selectActivity" />
			<input type="hidden" name="select" value="activities.jsp" />
			<input type="submit"  value="Test bean" />
		</form>--%>
    </body>
</html>