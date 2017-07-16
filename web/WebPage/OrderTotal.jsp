<%-- 
    Document   : OrderTotal
    Created on : 2017/5/18, 下午 08:36:24
    Author     : JimmyYang
--%>

<%@page import="Lottery.Lottery"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String[] num_801 = {"07", "02", "03", "04", "05", "06", "01"};
                    String[] num_802 = {"07", "02", "03", "04", "05", "06", "01"};
                    String[] num_803 = {"01", "02", "03", "04", "05"};
                    String game_id = "GS04";
                    Lottery lottery = new Lottery(num_801, num_802, num_803, game_id);
                    //執行
                    lottery.update_result();
                    lottery.update_result1();
                }
            }).start();
        %>
        <title>總帳</title>
    </head>
    <body>
        <h1>總帳 我在 ../WebPage/OrderTotal</h1>
        <h1>測試結算，請看下注明細</h1>
    </body>
</html>
