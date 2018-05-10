<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <title>Time Tracker</title>
</head>
<body>
<h2>My Activities</h2>
<table width="100%">
    <th>Id</th>
    <th>Name</th>
    <th>Description</th>
    <th>Creation Date</th>
    <th>DeadLine</th>
    <th>Working Time</th>
    <th>Complete</th>
    <th>Ask to Delete</th>
    <c:forEach var="activity" items="${Activities}">
        <c:if test="${activity.completed != true}">
        <tr>
            <form action="/pages/timetracking" method="post">
                <td>${activity.id}</td>
                <td>${activity.name}</td>
                <td>${activity.description}</td>
                <td>${activity.creationDate}</td>
                <td>${activity.deadLine}</td>
                <td>
                    <c:choose>
                    <c:when test="${activity.addRequest != true}">
                        <input type="text" name="time"  value=${activity.time}>
                    </c:when>
                    <c:otherwise>
                        ${activity.time}
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
                    <input type="hidden" name="select" value="/userPages/userIndex.jsp">
                    <input type="hidden" name="command" value="updateActivity">
                    <input type="submit" value="Save" />
                </td>
            </form>
        </tr>
        </c:if>
    </c:forEach>
</table>
<h3>Propose new activity</h3>
<table>
    <th>Name</th>
    <th>Description</th>
    <th>DeadLine</th>
    <tr>
        <form method="POST" action="/pages/timetracking">
            <td><input type="text" name="name" /></td>
            <td><input type="text" name="description" /></td>
            <td><input type="text" name="deadLine" /></td>
            <input type="hidden" name="userId" value=${User.id} /> <!--How to pass ID of the user???????-->
            <input type="hidden" name="added" value="true" />
            <input type="hidden" name="select" value="/userPages/userIndex.jsp">
            <input type="hidden" name="command" value="insertActivity"/>
            <td><input type="submit" /></td>
        </form>
    </tr>
</table>
</body>
</html>
