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
    </head>
    <body>
       <h1> <p>${title1}</p> </h1>
       <br>
       <div id="menu">
            <ul>
                <a href="homepage.html">Home</a> • <a href="http://www.google.com">My Account</a> • <a href="http://www.google.com">Create Forum</a> • <a href="http://www.google.com">Contact Us</a>
            </ul>
       </div>
       <br>
       
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
                           
        </table>
    </body>
</html>
