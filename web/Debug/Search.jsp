<%-- 
    Document   : index
    Created on : 2017/2/12, 上午 11:07:31
    Author     : JimmyYang
--%>

<%@page import="model.DB"%>
<%@page import="model.DataTable"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        CSS載入start-->
        <link href="../css/Main.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->

        <!--        JS載入start-->
        <script type="text/javascript" src="../js/Main.js"></script>
        <!--        JS載入end-->
        <title>SQL Page</title>
    </head>
    <body align="center" style='font-family: 微軟正黑體'>
        <%
            //取得SQL session
            String sql_select = (String) session.getAttribute("sql_select");
            if (sql_select == null) {
                sql_select = "select * from warges_main";
            }
        %>

        <!--        輸入SQL_start-->
        <form name="from" action="../Main" method="post">
            <h1>查詢介面</h1>
            <input type=hidden name="function" value="1"/>  
            <textarea name="sql_select" rows="3" style="width:95%;height:50px;"><%out.print(sql_select);%></textarea>
            <input class="button button2" type="submit" value="執行"></input>  
        </form>
        <hr>
        <!--        輸入SQL_end-->

        <!--        表格start-->
        <%
            try {
                //資料來源
                DataTable data = model.DB.getDataTable(sql_select);
                //目前頁數
                String page_num = request.getParameter("page");
                if (page_num != null) {
                    //有頁數參數
                    out.print(new view.Table_Display().show_Table(data, Integer.parseInt(page_num)));
                } else {
                    //無頁數參數初始值1
                    page_num = "1";
                    out.print(new view.Table_Display().show_Table(data, Integer.parseInt(page_num)));
                }
        %>
        <br>
        <%      //產生分頁按鈕
                out.print(new view.Table_Display().create_page_link("Search.jsp", data, Integer.parseInt(page_num)));
            } catch (Exception ex) {
                out.print("<h1>查無資料</h1>");
            }
        %>
        <!--        表格end-->
    </body>
</html>
