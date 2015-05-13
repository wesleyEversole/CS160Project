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
                <th> Hits: </th>
                <th> Last Post By:</th>
            </tr>
            <!pull data from server here>
            <tr>
                <td> <a href="thread.jsp">Thread1</a> </td>
                <td> 0 topics<br>0 replies </td>
                <td> <a href="http://www.google.com">Post Title</a><br>By <a href="http://www.google.com">Author</a><br>Some time ago </td>
            </tr>

            <c:forEach begin="1" end= "${no}" step="1" varStatus="loopCounter"
                       value="${ForumPosts}" var="ForumPosts">
                <tr>
                    <td>
                <c:out value="${ForumPosts.getTitle()}" />
                </td>
                <td>
                <c:out value="${ForumPosts.getNumberOfReply()}" />
                </td>
                <td>
                <c:out value="some date" />
                </td>                
                </tr>
            </c:forEach>

        </table>
    </body>
</html>
