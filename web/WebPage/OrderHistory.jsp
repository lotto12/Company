<%-- 
    Document   : Orderhistory
    Created on : 2017/5/25, 下午 08:15:42
    Author     : wayne
--%>

<%@page import="Lottery.Lottery"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            //檢查帳號有無登入，未登入則返回登入畫面
            String user_acount = (String) session.getAttribute("user_acount");
            if (user_acount == null) {
                out.print("<script>parent.location.href='/Lotto/index.html';</script>");
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">   
        <title>歷史總帳</title>
    </head>
    <body>
        <h1>歷史總帳 我在 ../WebPage/OrderHistory</h1>
        <form name="from" action="../test" method="post">
            <input type="submit" value="send">
        </form>
    </body>
</html>