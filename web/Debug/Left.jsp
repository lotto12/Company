<%-- 
    Document   : Left
    Created on : 2017/2/13, 下午 10:58:19
    Author     : JimmyYang
--%>

<%@page import="model.DB_Config"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        CSS載入start-->
        <link href="../css/Main.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <title>Left</title>
    </head>
    <body align="center"  style='font-family: 微軟正黑體'>
        <form target="Mid_2" name="from" action="../Main" method="post">
            <input type=hidden name="function" value="2"/>  
            <h1>資料表</h1>
            <%
                //取得所有資料表
                String db_name = new DB_Config().getDB();
                String[][] db_Table = model.DB.getDataArray("SHOW TABLES FROM " + db_name);
                for (int i = 0; i < db_Table.length; i++) {
                    for (int j = 0; j < db_Table[i].length; j++) {
                        out.print("<input class=\"button button2\" type=\"submit\" name=\"table\"value=\"" + db_Table[i][j] + "\"></input>");
                        out.print("<br>");
                    }
                }
            %>
        </form>
    </body>
</html>
