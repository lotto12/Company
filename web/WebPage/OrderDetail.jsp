<%-- 
    Document   : OrderDetail
    Created on : 2017/5/14, 上午 09:47:06
    Author     : JimmyYang
--%>

<%@page import="WebPage.OrderDetail"%>
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

            //下注明細後端定義檔案
            OrderDetail od = new OrderDetail();

            //歷史紀錄
            String orderTable = od.showDetialTable(user_acount);
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>下注明細</title>
    </head>
    <body>
        <h1>下注明細 我在 ../WebPage/OrderDetail.jsp</h1>
        <!--        測試用Start-->
        <%=orderTable%>
        <!--        測試用End-->
    </body>
</html>
