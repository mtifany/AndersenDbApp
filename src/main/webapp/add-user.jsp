<jsp:useBean id="user" scope="request" type="com.example.andersendbapp.model.User"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.example.andersendbapp.model.User" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Add user</title>
</head>
<body>
<div>
    <form method="post" action="user">
        <h2>${param.action == 'create' ? 'Add new User' : 'Update user'}</h2>

        <input type="hidden" name="id" value="${user.id}">
        <br>
        <label>First name</label>
        <br>
        <input type="text" name="firstname" value="${user.firstName}"required>
        <br>
        <label>Last Name</label>
        <br>
        <input type="text" name="lastname" value="${user.lastName}"required>
        <br>
        <label>Age</label>
        <br>
        <input type="number" name="age" min="0" max="100" value="${user.age}" required>
        <br>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</div>
</body>
</html>



</body>
</html>
