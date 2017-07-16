<%-- 
    Document   : SystemMsg
    Created on : 2017/4/29, 上午 12:17:14
    Author     : JimmyYang
--%>

<%@page import="model.DB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        表格變色-->
        <script src='../js/jquery-3.2.1.min.js'></script>
        <link href="../css/table_css.css" rel="stylesheet" type="text/css">
        <!--表格變色END-->
        <title>歷史公告</title>
    </head>
    <body>
        <h1>歷史公告 我在../WebPage/SystemMsg.jsp</h1>
        <table border="1" id="table">
            <tr>
　<th colspan="3">跑馬燈-歷史公告</th>
　</tr>
                <%
                    //系統公告
                    String sql_select = "select build_date,msg from system_msg order by id desc limit 10";
                    String[][] system_msg = DB.getDataArray(sql_select);

                    //產生表格
                    if (sql_select.length() > 0) {
                        for (int i = 0; i < system_msg.length; i++) {
                            out.print("<tr>");
                            out.print("<td>" + (i + 1) + "</td>");
                            out.print("<td>" + system_msg[i][0] + "</td>");
                            out.print("<td>" + system_msg[i][1] + "</td>");
                            out.print("</tr>");
                        }
                    } else {
                        out.print("<tr>");
                        out.print("<td><th colspan=\"3\">暫無資料</th></td>");
                        out.print("</tr>");
                    }
                %>
            <tr>
　<th colspan="3">更新日誌</th>
　</tr>
                <%
                    //系統公告
                    String sql_select_up = "select build_date,msg from system_update_msg order by id desc limit 10";
                    String[][] system_msg_up = DB.getDataArray(sql_select_up);

                    //產生表格
                    if (sql_select.length() > 0) {
                        for (int i = 0; i < system_msg.length; i++) {
                            out.print("<tr>");
                            out.print("<td>" + (i + 1) + "</td>");
                            out.print("<td>" + system_msg_up[i][0] + "</td>");
                            out.print("<td>" + system_msg_up[i][1] + "</td>");
                            out.print("</tr>");
                        }
                    } else {
                        out.print("<tr>");
                        out.print("<td><th colspan=\"3\">暫無資料</th></td>");
                        out.print("</tr>");
                    }
                %>
        </table> 
        <script src='../js/table.js'></script>
    </body>
</html>
