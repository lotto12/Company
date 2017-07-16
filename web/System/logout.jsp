<%-- 
    Document   : logout
    Created on : 2017/4/28, 下午 10:33:05
    Author     : JimmyYang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%
            //清出所有快取
            session.invalidate();
        %>
        <script>
            //重新導向
            window.location.href = '../index.html';
        </script>
    </body>
</html>
