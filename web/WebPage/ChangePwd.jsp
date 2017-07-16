<%-- 
    Document   : ChagePwd
    Created on : 2017/4/28, 下午 09:57:25
    Author     : JimmyYang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            //判斷有無登入
            String user_acount = (String) session.getAttribute("user_acount");
            if (user_acount == null) {
                out.print("<script>document.location.href=\"index.html\";</script>");
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        表格變色-->
        <script src='../js/jquery-3.2.1.min.js'></script>
        <link href="../css/table_css.css" rel="stylesheet" type="text/css">
        <!--表格變色END-->
        <title>更改密碼</title>
    </head>
    <body>
        <!--                密碼修改_edit by jimmy-->  
        <form name="from" action="../Main" method="post">
            <input type=hidden name="function" value="change_pwd"/>  
            <table border="1"  id="table">
                <tbody>
　<tr>
　<td colspan="2">修改密碼</td>
　</tr>
　<tr>
　<td>舊密碼</td>
　<td><input name="old_pwd" type="password">＊請輸入現在的密碼</td>
　</tr>
                    <tr>
　<td>新密碼</td>
　<td><input name="mew_pwd" type="password">＊英數混合搭配，至少四字元</td>
　</tr>
                    <tr>
　<td>確認新密碼</td>
　<td><input name="new_pwd_chk" type="password">＊請再次輸入新密碼</td>
　</tr>
                    <tr>
　<td colspan="2"><input type="submit" value="確認修改"></td>
　</tr>
                </tbody>
            </table>
            <script src='../js/table.js'></script>
        </form>
        <!--                密碼修改_edit by jimmy--> 
    </body>
</html>
