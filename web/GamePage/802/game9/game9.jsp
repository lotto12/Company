<%-- 
    Document   : game9
    Created on : 2017/7/11, 下午 01:37:49
    Author     : JimmyYang
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="WebPage.GamePage_Game9"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>快速打單</title>
        <%
            //檢查帳號有無登入，未登入則返回登入畫面
            String user_acount = (String) session.getAttribute("user_acount");
            if (user_acount == null) {
                out.print("<script>top.window.location.replace(\"/Lotto/index.html\");</script>");
            }

            //搜索條件
            String gtype = (String) session.getAttribute("Gtype"); //遊戲種類

            //抓取各玩法限額設定
            GamePage_Game9 game_page = new GamePage_Game9();
            ArrayList<String[]> member_setting = game_page.getAccount_Conf_Data(gtype, user_acount);//玩家各玩法上限

            //顯示資料
            String table_member_conf = game_page.getTable(gtype, user_acount);

            out.print("account:" + user_acount + "<br>");
            out.print("gtype:" + gtype);
        %>

        <!--        JS載入-->
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <SCRIPT LANGUAGE = "JavaScript" SRC = "/Lotto/js/SpeedOrder/Main.js" ></SCRIPT>
    </head>
    <body style="background-color: white" onload="init(<%=gtype%>);">
        <table align="center" border="1">
            <tr>
                <th colspan="4">會員額度</th>
            </tr>
            <tr>
                <th>玩法</th>
                <th>單碰(筆)上限</th>
                <th>單碰(筆)下限</th>
                <th>單組限額</th>
            </tr>
            <%=table_member_conf%>
        </table>
        <hr>
        <table align="center">
            <tr>
                <td colspan="2" align="center">
                    <font style="color: brown"> ENTER:下一步，ESC:重設 </font>
                </td>
            </tr>
            <tr>
                <td>
                    <font>玩法代號</font>
                </td>
                <td>
                    <input type="number" id="step1" OnFocus="setFocus(1);" onkeyup="value = value.replace(/[^\d]/g, '')"></input>
                    <font style="color: red" id="type">[ 尚未設定 ]</font>
                </td>
            </tr>
            <tr>
                <td>
                    <font>下注號碼</font>
                </td>
                <td>
                    <input type="text" style="width: 300px" id="step2" OnFocus="setFocus(2);" onkeyup="value = value.replace(/[^\d&+,]/g, '')"></input>
                    <font style="color: red" id="num_txv"> [ 尚未設定 ] </font>
                </td>
            </tr>
            <tr>
                <td>
                    <font>單注金額</font>
                </td>
                <td>
                    <input type="number" id="step3" OnFocus="setFocus(3);" onkeyup="value = value.replace(/[^\d]/g, '')"></input>
                    <font style="color: red" id="pay_txv"> [ 尚未設定 ] </font>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <font id="order"></font>
                </td>
            </tr>
        </table>
        <hr>
        <table border="1" align="center" style="display:none" id="order_result">
            <tr>
                <th colspan="2">注單結果</th>
            </tr>
            <tr>
                <td>賽事種類</td>
                <td><font id="set_gtype">801</font></td>
            </tr>
            <tr>
                <td>注單號碼</td>
                <td><font id="set_remarks">SG04-05</font></td>
            </tr>
            <tr>
                <th colspan="2">注單</th>
            </tr>
            <tr>
                <td>遊戲玩法</td>
                <td><font id="set_type">3</font></td>
            </tr>
            <tr>
                <td>連碰柱碰</td>
                <td><font id="set_stype">2</font></td>
            </tr>
            <tr>
                <td>下注號碼</td>
                <td><font id="set_num">2</font></td>
            </tr>
            <tr>
                <td>價格</td>
                <td><font id="set_price">63.5</font></td>
            </tr>
            <tr>
                <td>賠率</td>
                <td><font id="set_odds">570</font></td>
            </tr>
            <tr>
                <td>組數</td>
                <td><font id="set_group_num">1</font></td>
            </tr>
            <tr>
                <td>下注金額</td>
                <td><font id="set_pay">100</font></td>
            </tr>
            <tr>
                <th colspan="2">再按一次 ENTER 送出</th>
            </tr>
        </table>
    </body>
</html>
