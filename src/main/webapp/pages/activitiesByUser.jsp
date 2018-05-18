<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java"%>
<%@ page import="com.epam.timetracking.utils.ConstantsImpl" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="user" uri="mytaglib"%>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="bundle" />
<html>
<title>Time Tracker</title>
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
<h2 align="center"><fmt:message key="activities.actualByUser"/> ${SelectedUser.firstName}</h2>
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
    <c:if test="${activity.completed != true}">
        <tr>
            <form action="timetracking" method="post">
                <td>${activity.id}</td>
                <td>${activity.name}</td>
                <td><input type="text" name="description" required value="${activity.description}"></td>
                <td><fmt:formatDate value="${activity.creationDate}" pattern="MM/dd/yyyy"/></td>
                <td><input type="date" name="deadLine"  value=${activity.deadLine}></td>
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
                    <input type="hidden" name="removed" value="false">
                    <input type="hidden" name="select" value=${ConstantsImpl.ACTIVITIES_BY_USER}>
                    <input type="hidden" name="command" value="updateActivity" />
                    <input type="submit" value="<fmt:message key="userIndex.save"/>" />
                </td>
            </form>
            <form id="${formId}" action="timetracking" method="post">
                <input type="hidden" name="id" value=${activity.id} >
                <input type="hidden" name="name" value="${activity.name}">
                <input type="hidden" name="select" value=${ConstantsImpl.ACTIVITIES_BY_USER}>
                <input type="hidden" name="command" value="deleteActivity">
            </form>
                <td>
                    <input type="submit" value="<fmt:message key="activities.button.delete"/>"
                           onclick="deleteLine(${formId})" />
                </td>
        </tr>
    </c:if>
    </c:forEach>
</table>
<A HREF="${ConstantsImpl.ADMIN_INDEX}" class="topcorner"><fmt:message key="activities.homePage"/></A>
</body>
</html>