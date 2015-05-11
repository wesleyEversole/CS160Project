<%-- 
    Document   : Each threads that users already made
    Author     : Jason Ng
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
        <div id="menu">
            <ul>
                <a href="homepage.html">Home</a> • <a href="http://www.google.com">My Account</a> • <a href="http://www.google.com">Create Forum</a> • <a href="http://www.google.com">Contact Us</a>
            </ul>            
     </div>
        
        <div>                
         <p>${title1}</p>
         <br>
         <p>${author}</p>
         <br>
         <p>${message}</p>
         <br>
        </div>
        
        <div>                
         Title
         author
         Message        
        </div>        
        <form>
            <h1>
                Reply:
                <br>
                <textarea name="content" rows="10" cols="50">Type your content here.</textarea> 
                <br>
                <input type ="submit" class="login login-submit" value="Reply">
            </h1>
        </form>
    </body>
</html>
