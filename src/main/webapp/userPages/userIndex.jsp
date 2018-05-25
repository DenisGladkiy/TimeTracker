<%@ page pageEncoding="UTF-8" contentType="text/html;UTF-8" language="java"%>
<%@ page import="com.denis.timetracking.utils.ConstantsImpl" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="user" uri="mytaglib"%>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="bundle" />
<html>
<head>
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
    <title>Time Tracker</title>
</head>
<body>
<h2><fmt:message key="activities.welcome"/> ${User.firstName}</h2>
<h2><fmt:message key="userIndex.myActivities"/></h2>
<table width="100%">
    <th><fmt:message key="userIndex.id"/></th>
    <th><fmt:message key="userIndex.name"/></th>
    <th><fmt:message key="userIndex.description"/></th>
    <th><fmt:message key="userIndex.creationDate"/></th>
    <th><fmt:message key="userIndex.deadLine"/></th>
    <th style="background: lightsalmon"><fmt:message key="userIndex.workingTime"/></th>
    <th style="background: lightsalmon"><fmt:message key="userIndex.timeToday"/></th>
    <th><fmt:message key="userIndex.complete"/></th>
    <th><fmt:message key="userIndex.askDelete"/></th>
    <c:forEach var="activity" items="${Activities}">
        <c:if test="${activity.completed != true}">
        <tr>
            <form action="/pages/timetracking" method="post">
                <td>${activity.id}</td>
                <td>${activity.name}</td>
                <td>${activity.description}</td>
                <td><fmt:formatDate value="${activity.creationDate}" pattern="MM/dd/yyyy"/></td>
                <td><fmt:formatDate value="${activity.deadLine}" pattern="MM/dd/yyyy"/></td>
                <td style="background: lightsalmon"><user:getHours activity="${activity}"/></td>
                <td style="background: lightsalmon"><c:choose>
                    <c:when test="${activity.addRequest != true}">
                        <input type="number" name="time" min="0" max="24" step="0.1" value="0.0">
                    </c:when>
                    <c:otherwise>
                        0.0
                    </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <input type="checkbox" name="complete"/>
                </td>
                <td>
                    <c:choose>
                         <c:when test="${activity.removeRequest == true}">
                             <input type="checkbox" name="removed" checked/>
                         </c:when>
                         <c:otherwise>
                             <input type="checkbox" name="removed"/>
                         </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <input type="hidden" name="id" value=${activity.id}>
                    <input type="hidden" name="name" value="${activity.name}">
                    <input type="hidden" name="description" value="${activity.description}">
                    <input type="hidden" name="creationDate" value=${activity.creationDate}>
                    <input type="hidden" name="deadLine" value=${activity.deadLine}>
                    <input type="hidden" name="userId" value="${activity.userId}">
                    <input type="hidden" name="added" value="${activity.addRequest}">
                    <input type="hidden" name="select" value=${ConstantsImpl.USER_INDEX}>
                    <input type="hidden" name="command" value="updateActivity">
                    <input type="submit" value="<fmt:message key="userIndex.save"/>" />
                </td>
            </form>
        </tr>
        </c:if>
    </c:forEach>
</table>
<h3><fmt:message key="userIndex.propose"/></h3>
<table>
    <th><fmt:message key="userIndex.name"/> </th>
    <th><fmt:message key="userIndex.description"/></th>
    <th><fmt:message key="userIndex.deadLine"/></th>
    <tr>
        <form method="POST" action="/pages/timetracking">
            <td><input type="text" name="name" required/></td>
            <td><input type="text" name="description" required/></td>
            <td><input type="date" name="deadLine" /></td>
            <input type="hidden" name="userId" value=${User.id} />
            <input type="hidden" name="added" value="on" />
            <input type="hidden" name="select" value=${ConstantsImpl.USER_INDEX}>
            <input type="hidden" name="command" value="insertActivity"/>
            <td><input type="submit" value="<fmt:message key="userIndex.send"/>" /></td>
        </form>
    </tr>
</table>
</body>
</html>
