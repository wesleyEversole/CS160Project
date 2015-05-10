<%-- 
    Document   : Each threads that users already made
    Created on : May 10, 2015, 3:18:03 AM
    Author     : Jason
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> <p>${title}</p></title>
    </head>
    <body>
        <div id="menu">
            <ul>
                <a href="homepage.html">Home</a> • <a href="http://www.google.com">My Account</a> • <a href="http://www.google.com">Create Forum</a> • <a href="http://www.google.com">Contact Us</a>
            </ul>            
     </div>
        
        <div>                
         <p>${title1}</p>
         <p>${author}</p>
         <p>${message}</p>         
        </div>
        
        
        
        <form>
                Reply:
                <br>
                <textarea name="content" rows="10" cols="50">Type your content here.</textarea> 
                <br>
                <input type ="submit" class="login login-submit" value="Reply">
        </form>
    </body>
</html>
