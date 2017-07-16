<%-- 
    Document   : Result
    Created on : 2017/4/19, 上午 02:14:04
    Author     : JimmyYang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>結果</title>
    </head>
    <body style="font-family: 微軟正黑體">
        <h1>立柱算法</h1><br>
        <form action="index_backup.jsp" method="post">
            1. 請輸入排:<input type="text" name="d1" size="50"><div>Ex. 1,1,1 -------(3排 , 每排各1組中間用逗號分隔)</div><br>
            2. 請輸入星數:<input type="text" name="d2" size="2"><br><div>Ex. 2 -------(2星)</div><br>
            <input type="submit" value="計算">
        </form>
        <br>
        <hr>
        <%
            //資料接收型態UTF-8
            request.setCharacterEncoding("UTF-8");

            //資料名稱
            String d1 = request.getParameter("d1");
            String d2 = request.getParameter("d2");

            if (d1 != null && d2 != null) {
                try {
                    String[] data = d1.split(",");
                    int num = Integer.parseInt(d2);
                    out.print("排-->" + d1 + "<br>");
                    out.print("星數-->" + d2 + "星<br>");
                    out.print("<h1>總共有: " + new Lotto.Lotto().getGameNum(data, num) + "組</h1>");
                } catch (Exception ex) {
                    out.print("輸入錯誤!" + ex.toString());
                }
            }

        %>
    </body>
</html>
