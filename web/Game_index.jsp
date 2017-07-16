<%-- 
   Document   : Game_index
   Created on : 2017/4/27, 下午 09:23:04
   Author     : JimmyYang
--%>

<%@page import="model.DB"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>主畫面</title>
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <SCRIPT LANGUAGE = "JavaScript" SRC = "js/WebSocket.js" ></SCRIPT>
        <script type="text/javascript">
            //調整中間顯示範圍
            var obj1_h;
            var bd_h;
            function resizeIframe(obj) {
                obj.style.height = 0;
                var hight = obj.contentWindow.document.body.scrollHeight;
                if (hight < 50) {
                    hight = 100;
                }
                obj1_h = hight;
                bd_h = $(window).height() - 10;
                document.getElementById('Mid').style.height = bd_h - obj1_h + 'px';
                obj.style.height = hight + 'px';
            }
            //重新調整視窗
            $(document).ready(function () {
                $(window).resize(function () {
                    hight = $(window).height() - 10;
                    bd_h = hight;
                    document.getElementById('Mid').style.height = bd_h - obj1_h + 'px';
                });
            });
        </script>
        <style>
            html,body, div, iframe{             
                margin: 0; padding: 0;
            }
        </style>
    <body bgcolor="gray">
        <%
            String user_acount = (String) session.getAttribute("user_acount");
            String ip = request.getRemoteAddr();
            JSONObject obj = new JSONObject();
            Map m = new HashMap();
            m.put("ip", ip);
            m.put("acount", user_acount);
            obj.put("login", m);

            if (user_acount == null) {
                //登入失敗
                out.print("<h1 style=\"font-family: '微軟正黑體'\">登入失敗</h1>");
                out.print("<a style=\"font-family: '微軟正黑體'\" href=\"index.html\" target=\"_parent\">返回登入頁面</a>");
            } else {
                //登入成功
                out.print("<iframe src=\"Layout/Top.jsp\" style=\" border:none\" name=\"Top\" width=\"100%\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"No\" frameborder=\"0\" id=\"Top\" onload=\"resizeIframe(this)\"></iframe>");
                out.print("<iframe src=\"Game_Main.jsp\" style=\"height: 600px; border:none\" name=\"Mid\" width=\"100%\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"Yes\" frameborder=\"0\" id=\"Mid\"></iframe>");
            }
        %>
    </body>
    <script type="text/javascript">
        window.setTimeout(function () {
            doSend('<%=obj.toString()%>');
        }, 5000);
    </script>
</head>
</html>
