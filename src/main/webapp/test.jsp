<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <body>
        <h2>Time Tracking</h2>
        <c:forEach var="activity" items="${Activities}">
			<c:out value="${activity}"/></br>
        </c:forEach>
    </body>
</html>