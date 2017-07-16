<%-- 
    Document   : game7
    Created on : 2017/5/17, 下午 03:32:15
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>大樂透-天碰</title>
        <!--        <SCRIPT LANGUAGE = "JavaScript" SRC = "/Lotto/js/WebSocket.js" ></SCRIPT>-->

        <!--        STEP1 JS載入START-->
        <%
            //檢查帳號有無登入，未登入則返回登入畫面
            String user_acount = (String) session.getAttribute("user_acount");
            if (user_acount == null) {
                out.print("<script>top.window.location.replace(\"/Lotto/index.html\");</script>");
            }
        %>
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <SCRIPT LANGUAGE = "JavaScript" SRC = "/Lotto/js/802/game7.js" ></SCRIPT>
        <script type="text/javascript" src="/Lotto/layer/layer.js"></script>
        <!--        STEP1 JS載入END-->

        <!--        P.S 目錄使用方式 /Lotto/css/...-->
    </head>
    <body>
        <!--        測試用START-->
        <form name="add_order" action="/Lotto/Order" method="post" target="Odd">
            <input type=hidden id="function" name="function" value="add_order"/>
            <input type=hidden id="gtype" name="gtype" value="0"/>  
            <input type=hidden id="type" name="type" value="0"/>  
            <input type=hidden id="data" name="data" value="0"/> 
            <input type=hidden id="data" name="group_num" value="0"/> 
            <input type=hidden id="data" name="stype" value="0"/> 
            <input type=hidden id="acount" name="acount" value="<%=user_acount%>"/> 
            <input type="button" onclick="addOrder();" value="增加注單(測試用)"></input>
        </form>
        <!--        測試用END-->

        <h3>大樂透_天碰 我在 ../GamePage/802/game7/game7.jsp</h3>
    </body>
</html>

