<%-- 
    Document   : Contact
    Created on : 2017/4/29, 上午 01:02:56
    Author     : JimmyYang
--%>

<%@page import="model.DB"%>
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
        <title>聯絡我們</title>
    </head>
    <body>
        <h1>聯絡我們 我在../WebPage/Contact.jsp</h1>
        <!--        聯絡我們-->
        <form name="from" action="../Main" method="post">
            <input type=hidden name="function" value="send_msg"/>  
            <input type=hidden name="acount" value="<%=user_acount%>"/>  
            <table border="1">
　<tr>
　<td colspan="2">聯絡我們</td>
　</tr>
　<tr>
　<td>會員帳號</td>
　<td><%=user_acount%></td>
　</tr>
                <tr>
　<td>連絡電話</td>
　<td><input name="phone" type="text"></td>
　</tr>
                <tr>
　<td>電子信箱</td>
　<td><input name="email" type="text"></td>
　</tr>
                <tr>
　<td>留言內容</td>
　<td><textarea rows="4" cols="50" name="msg"></textarea></td>
　</tr>
                <tr>
　<td colspan="2"><input type="reset" value="清除重設"><input type="submit" value="確認送出"></td>
　</tr>
            </table>
        </form>
        <!-- 聯絡我們END-->

        <!-- 歷史訊息-->
        <table border="1">
　<tr>
　<td colspan="3">歷史訊息</td>
　</tr> 
            <td>項目</td>
            <td>訊息內容</td>
　<td>留言時間</td>
            <%
                //歷史訊息
                String sql_select = "select msg,build_date from game_comment where acount = '" + user_acount + "' order by id desc";
                String[][] sql_result = DB.getDataArray(sql_select);
                if (sql_result.length > 0) {
                    for (int i = 0; i < sql_result.length; i++) {
                        out.print("<tr>");
                        out.print("<td>" + (i + 1) + "</td>");
                        out.print("<td>" + sql_result[i][0] + "</td>");
                        out.print("<td>" + sql_result[i][1] + "</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan=\"3\">尚無訊息</td>");
                    out.print("</tr>");
                }
            %>
        </table>
        <!--        歷史訊息END-->
    </body>
</html>
