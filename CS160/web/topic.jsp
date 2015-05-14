<%-- 
    Document   : The 5 topics
    Created on : May 10, 2015, 3:55:39 AM
    Author     : Jason
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${title}</title>
        <link rel="stylesheet" type="text/css" media="screen" href="css/forumtables.css">
    </head>
    <body>
        <h1> <p>SJSU Forum</p> </h1>
        <br>
        <div id="menu">
            <table align="center">
                <tr><th><a href="homepage.html">Home</a> • <a href="account.html">My Account</a> • <a href="http://www.google.com">Contact Us</a></th></tr>
            </table>            
        </div>
        <br>

        <form action="posting.html">
            <input type="submit" value="Create a thread">
        </form>
        <br>
        <h3> <p>${title1}</p> </h3>
        <table align="center">
            <tr>
                <th> Title: </th>
                <th> Created By:</th>
                <th> Date:</th>
            </tr>
            <!pull data from server here>
            <tr>
                <td> <a href="Threads?id=1">Thread1</a> </td>
                <td> 0 topics<br>0 replies </td>
                <td> <a href="http://www.google.com">Post Title</a><br>By <a href="http://www.google.com">Author</a><br>Some time ago </td>
            </tr>

            <c:forEach items="${rows}" var="row">
                <tr>
                <td>
                <c:out value="${row.getTitle()}" />
                </td>
                <td>
                author
                </td>
                <td>
                sometime
                </td>                
                </tr>
            </c:forEach>

        </table>
    </body>
</html>
