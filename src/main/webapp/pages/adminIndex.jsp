<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java"%>
<%@ page import="com.epam.timetracking.utils.ConstantsImpl" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="bundle" />
<html>
<title>Time Tracker</title>
<body>
<h2 align="center"><fmt:message key="adminIndex.tracker"/> </h2>
<table style="float: left" width="70%" class="table1">
    <tr>
        <th>
            <form id="actual" method="POST" action="timetracking">
                <input type="hidden" name="command" value="selectActivity"/>
                <input type="hidden" name="select" value=${ConstantsImpl.ACTIVITIES}>
                <a href="#" onclick="document.getElementById('actual').submit()">
                    <h3><fmt:message key="adminIndex.actualActivities"/></h3></a>
            </form>
        </th>
        <th>
            <form id="added" method="POST" action="timetracking">
                <input type="hidden" name="command" value="selectActivity"/>
                <input type="hidden" name="select" value=${ConstantsImpl.ADDED_ACTIVITIES}>
                <a href="#" onclick="document.getElementById('added').submit()">
                    <h3><fmt:message key="adminIndex.addActivity"/></h3></a>
            </form>
        </th>
        <th>
            <form id="removed" method="POST" action="timetracking">
                <input type="hidden" name="command" value="selectActivity"/>
                <input type="hidden" name="select" value=${ConstantsImpl.REMOVED_ACTIVITIES}>
                <a href="#" onclick="document.getElementById('removed').submit()">
                    <h3><fmt:message key="adminIndex.removeActivity"/></h3></a>
            </form>
        </th>
        <th>
            <form id="completed" method="POST" action="timetracking">
                <input type="hidden" name="command" value="selectActivity"/>
                <input type="hidden" name="select" value=${ConstantsImpl.COMPLETED_ACTIVITIES}>
                <a href="#" onclick="document.getElementById('completed').submit()">
                    <h3><fmt:message key="adminIndex.completedActivities"/></h3></a>
            </form>
        </th>
    </tr>
    <tr><td colspan="2" align="center"><h4><fmt:message key="adminIndex.createActivity"/></h4></td></tr>
    <form method="POST" action="timetracking">
        <tr><td><fmt:message key="userIndex.name"/></td>
            <td><input type="text" name="name" required style="width:170px"/></td><td colspan="2"></td></tr>
        <tr><td><fmt:message key="userIndex.description"/></td>
            <td><textarea maxlength="200" name="description" required></textarea>
            <td colspan="2"></td></tr>
        <tr><td><fmt:message key="userIndex.deadLine"/></td>
            <td><input type="date" name="deadLine" style="width:170px"/></td><td colspan="2"></td></tr>
        <tr><td><fmt:message key="adminIndex.userId"/></td>
            <td>
                <select name="userId" style="width:170px">
                    <c:forEach var="user" items="${Users}">
                        <option value="${user.id}">${user.id}</option>
                    </c:forEach>
                </select>
            </td>
        <input type="hidden" name="select" value=${ConstantsImpl.ADMIN_INDEX}>
        <input type="hidden" name="command" value="insertActivity"/>
        <tr><td></td><td><input type="submit" value="<fmt:message key="adminIndex.button.create"/>"/></td>
            <td colspan="2"></td></tr>
    </form>
</table>
<table style="float: right" width="30%" class="table2">
    <tr>
        <th colspan="2" align="center">
            <form id="users" method="POST" action="timetracking">
                <input type="hidden" name="select" value=${ConstantsImpl.USERS}>
                <input type="hidden" name="command" value="selectUser"/>
                <a href="#" onclick="document.getElementById('users').submit()">
                    <h3><fmt:message key="adminIndex.usersList"/></h3></a>
            </form>
        </th>
    </tr>
    <tr><td colspan="2" align="center"><h4><fmt:message key="adminIndex.createUser"/></h4></td></tr>
    <form id="submitUser" method="POST" action="timetracking">
        <tr><td><fmt:message key="adminIndex.firstName"/></td><td><input type="text" name="firstName" required style="width:170px" /></td></tr>
        <tr><td><fmt:message key="adminIndex.lastName"/></td><td><input type="text" name="lastName" required style="width:170px"/></td></tr>
        <tr><td>E-mail</td><td><input type="text" name="email" id="email" required style="width:170px"/></td></tr>
        <tr><td><fmt:message key="index.password"/></td><td><input type="password" name="pass" id="pass1" required style="width:170px"/></td></tr>
        <tr><td><fmt:message key="index.chkPassword"/></td><td><input type="password" name="chkpass" id="pass2" required style="width:170px"/></td></tr>
        <tr><td><fmt:message key="adminIndex.role"/></td>
            <td>
                <select name="role" style="width:170px">
                    <option value="USER">USER</option>
                    <option value="ADMIN">ADMIN</option>
                </select>
            </td>
        </tr>
        <input type="hidden" name="command" value="insertUser"/>
        <tr><td></td><td><input type="button" value="<fmt:message key="adminIndex.button.create"/>" onclick="checkPassword()" /></td></tr>
    </form>
</table>
<style type="text/css">
    textarea {
        resize: none;
        width: 170px;
        height: 70px;
    }
    .table1 {
        background: #F0F8FF;
        color: dimgray;
        border-collapse: collapse;
        height: 300px;
    }
    .table2{
        background: #F0FFF0;
        color: dimgray;
        border-collapse: collapse;
        height: 300px;
    }
    th{
        background: #F5F5F5;
    }
    * { font-family: Arial; }
    body{
        background: #F5F5F5;
    }
</style>
<script language="javascript">
    function checkPassword(){
        var pass = document.getElementById("pass1").value;
        var chkpass = document.getElementById("pass2").value;
        var form = document.getElementById("submitUser");
        var email = document.getElementById("email").value;
        if(pass == chkpass && form.checkValidity() && emailIsValid(email)){
            form.submit();
        }else if(!form.checkValidity()){
            alert("<fmt:message key='index.formError'/>");
        }else if(!emailIsValid(email)){
            alert("Incorrect Email format");
        }else{
            alert("<fmt:message key='index.wrongPass'/>");
        }
    }
    function emailIsValid(email) {
        var regex = /\S+@\S+\.\S+/;
        return regex.test(email);
    }
</script>
</body>
</html>

