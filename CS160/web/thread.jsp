<%-- 
    Document   : Each threads that users already made
    Author     : Jason Ng
-------------------
thread message | author
-------------------
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> ${title}</title>
        <link rel="stylesheet" type="text/css" media="screen" href="css/thread.css">
    </head>
    <body>
        <div id="header">
            <h1><font size = "9">SJSU Forum</font></h1>
        </div>
        <div id="menu">
            <table align="center">
                <tr><th><a href="homepage.html">Home</a> • <a href="account.html">My Account</a> • <a href="http://www.google.com">Contact Us</a></th></tr>
            </table>            
        </div>
        
        <h3 align="center"><font size = "6">Title: ${title1}</font></h3>
        <table align="center">

            <tr>
                <th> <div id= author> Author: ${author} </div></th>                
            </tr>
            <tr>
                <td> <div id="message">${message}</div> </td>
            </tr>
            <tr>
                <th> <div id= author>Author: ${author} </div></th>                
            </tr>
            <tr>
                <td> <div id="message"> ${message} </div> </td>
            </tr>    
        </table>
            <br>
        <form>   
            <h1>
                Reply:
            </h1>
                <textarea name="content" rows="8" cols="80">Type your content here.</textarea> 
                <br>
                <input type ="submit" class="login login-submit" value="Reply">
         </form>
    </body>
</html>
