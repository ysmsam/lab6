<%-- 
    Document   : users
    Created on : 2022-3-11, 13:28:58
    Author     : Sheng Ming Yan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    
    </head>
    <body>
        <h1>Sheng's User Management System</h1>
        
        <div class="container">
            <div class="row">
                <div class="col">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>E-mail</th>
                                <th>First name</th>
                                <th>Last name</th>
                                <th>Role</th>
                                <th>Active</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <td name="email">${user.email}</td>
                                    <td name="firstName">${user.firstName}</td>
                                    <td name="lastName">${user.lastName}</td>
                                    <td name="role">${user.roleID}</td>
                                    <td name="active">${user.active ? "Y" : "N"}</td>
                                    <td>
                                        <a href="user?action=update&amp;email=${user.email}">Edit</a>
                                        <a href="user?action=delete&amp;email=${user.email}">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <%--<c:if test="${user ne null}">--%>
                                <form action="user" method="post">
                                    <table>
                                        <tr>
                                            <td><input type="text" name="email">Email</td>
                                            <td><input type="text" name="firstName">Fisrt name</td>
                                            <td><input type="text" name="lastName">Last name</td>
                                            <td><input type="text" name="roleID">Role</td>
                                            <td><input type="text" name="active">Active</td>
                                            <td>
                                                <a href="user" name="action" value="create">Create</a>
                                                <input type="hidden" name="action" value="create">
                                                <input type="submit" value="Add">
                                                
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            <%--</c:if>--%>
                        </tbody>
                    </table>
                    
            <c:if test="${userEdit ne null}">
            <!--<h2>Edit Note</h2>-->
            <form action="user" method="post">
                <span>${userEdit.email}</span>
                <input type="text" name="firstName" value="${userEdit.firstName}">
                <input type="text" name="lastName" value="${userEdit.lastName}">
                <input type="text" name="roleID" value="${userEdit.roleID}">
                <input type="text" name="active" value="${userEdit.active}">

                <input type="hidden" name="action" value="update">
                <input type="submit" value="Save">
            </form>
        </c:if>
                </div>
            </div>
        </div>

    </body>
</html>
