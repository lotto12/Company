<%-- 
    Document   : UserConf
    Created on : 2017/5/17, 上午 11:51:22
    Author     : JimmyYang
--%>

<%@page import="WebPage.MemberConf"%>
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

            //會員資料定義檔
            MemberConf memberConf = new MemberConf(user_acount);
            String member_table = memberConf.getMemberConf();
            String table_801 = memberConf.getTable1("801");
            String table_802 = memberConf.getTable1("802");
            String table_803 = memberConf.getTable1("803");
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript" src="/Lotto/layer/layer.js"></script>
        <SCRIPT LANGUAGE = "JavaScript" SRC = "/Lotto/js/MemberConf/MemberConf.js" ></SCRIPT>
        <title>會員資料</title>
    </head>
    <body>
        <h1>個人資料 我在../WebPage/MemberConf.jsp</h1>

        <!--        個人資料表格-->
        <%=member_table%>
        <br>
        <!--        六合彩-->
        <%=table_801%>
        <br>
        <!--        大樂透-->
        <%=table_802%>
        <br>
        <!--        539-->
        <%=table_803%>
    </body>
</html>
