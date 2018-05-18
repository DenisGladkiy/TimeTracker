<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java"%>
<%@ page import="com.epam.timetracking.utils.ConstantsImpl" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="bundle" />
<html>
<title>Time Tracker</title>
<script language="javascript">
    function deleteLine(formId){
        var okay=confirm('Do you want to delete the user?');
        if(okay){
            document.getElementById(formId).submit();
        }
    }
</script>
<style type="text/css">
	td{text-align: center}
	.submitLink {
		background-color: transparent;
		text-decoration: underline;
		border: none;
		color: blue;
		cursor: pointer;
	}
	* { font-family: Arial; }
	body{ background: #F5F5F5; }
	tr{
		height: 40px;
		color: dimgray;
	}
	.topcorner{
		position:absolute;
		top:1%;
		left:1%;}
</style>
    <body>
        <h2 align="center"><fmt:message key="adminIndex.usersList"/> </h2>
		<table width="100%">
		<th><fmt:message key="adminIndex.userId"/></th>
		<th><fmt:message key="adminIndex.firstName"/></th>
		<th><fmt:message key="adminIndex.lastName"/></th>
		<th>E-mail</th>
		<th><fmt:message key="adminIndex.role"/></th>
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
				<input type="hidden" name="select" value=${ConstantsImpl.ACTIVITIES_BY_USER}>
			    <input type="hidden" name="command" value="usersActivities">
				<input type="submit" class="submitLink" value="<fmt:message key="activities.viewUserActivities"/> " />
			</td>
			</form>
			<form id="${formId}" action="timetracking" method="post">
				<input type="hidden" name="id" value=${user.id}>
				<input type="hidden" name="firstName" value=${user.firstName}>
				<input type="hidden" name="command" value="deleteUser" />
			</form>
			<td>
                <input type="submit" value="<fmt:message key="activities.button.delete"/>" onclick="deleteLine(${formId})"/>
			</td>
		</tr>
        </c:forEach>
		</table>
		<A HREF="${ConstantsImpl.ADMIN_INDEX}" class="topcorner"><fmt:message key="activities.homePage"/></A>
    </body>
</html>