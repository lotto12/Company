<%-- 
    Document   : Odds
    Created on : 2017/5/5, 下午 02:07:06
    Author     : JimmyYang
--%>

<%@page import="Function.EncryptionCode"%>
<%@page import="Function.GameMethod"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            //檢查帳號有無登入，未登入則返回登入畫面
            String user_acount = (String) session.getAttribute("user_acount");
            if (user_acount == null) {
                out.print("<script>top.window.location.replace(\"/Lotto/index.html\");</script>");
            }

            //遊戲種類
            String gtype = (String) session.getAttribute("Gtype");

            //遊戲玩法
            String game_type = (String) session.getAttribute("game_type");

            //預設玩法(六合彩_特別號)
            if (gtype == null || game_type == null) {
                gtype = "801";
                game_type = "1";
            }

            //種類名稱
            String game_method = new GameMethod().getGame_Type(game_type);

            //清理快取
            String reset = request.getParameter("reset");
            if (reset != null && reset.equals("1")) {
                session.removeAttribute("order_data");
            }

            //下注單
            String order_data = (String) session.getAttribute("order_data");

            //產生安全碼
            String client_pwd = new EncryptionCode().getCheckPwd_Client();
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        需載入JS-->
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script>
            var order_data = '<%=order_data%>';
            var account = '<%=user_acount%>';
        </script>
        <script type="text/javascript" src="/Lotto/layer/layer.js"></script>
        <SCRIPT LANGUAGE = "JavaScript" SRC = "/Lotto/js/Order/OrderSocket.js" ></SCRIPT>
        <!--        需載入JS-->


        <title>下注單</title>
        <link rel="stylesheet" type="text/css" href="/Lotto/css/odd.css">
    </head>
    <body>

        <!--        測試用，需要建立下注單(如需隱藏請先註解)  -->       
        <div id='loading' style="display:none">
            <input type="hidden" id="client_pwd" value="<%=client_pwd%>"/>
            <input type="button" onclick="orderCheck();" value="下注(測試用)"></input>
            <h3>我是下注單我在../Odd/Odds.jsp</h3>
            <p>目前遊戲種類：<%=gtype%> 目前玩法：<%=game_method%> -------連線狀態：<font id="status"></font></p>
            <p><%=user_acount%>下注單：<%=order_data%></p>
        </div>
        <!--        以上為測試用，需要建立下注單        -->


        <table  class="table1">
            <tr><td colspan="2" bgcolor="#DA8644" class="header">帳 戶 資 訊</td></tr>
            <tr><td colspan="2"  class="header">wx227</td></tr>
            <tr><td colspan="2"  class="header">(ting)</td></tr>
            <tr>
                <td>下注金額</td>
                <td><span class="infoprice">0</span></td>
            </tr>
            <tr>
                <td>總和額度</td>
                <td><span class="infoprice">1000000</span></td>
            </tr>
            <tr>
                <td>剩餘額度</td>
                <td><span class="infoprice">1000000</span></td>
            </tr>
            <tr>
                <td>單筆上限</td>
                <td><span class="infoprice">20000</span></td>
            </tr>
            <tr>
                <td>單筆下限</td>
                <td><span class="infoprice">100</span></td>
            </tr>
        </table>

        <button class="nextbutton">未開盤<br>下一個</button>

        <table class="table2" border="0"  bordercolor="#C8722D" cellspacing="0" cellpadding="0">
            <tr class="header">
                <td>號碼</td>
                <td style="text-align:center;">下注金額</td>
                <td>刪單</td>
            </tr>
            <tr bgcolor="#D0D0D0">
                <td>01</td>
                <td style="text-align:center;">110000000</td>
                <td><a href="javascript:;"><img src="/Lotto/images/del.png"></a></td>
            </tr>
            <tr bgcolor="#D0D0D0">
                <td>08</td>
                <td style="text-align:center;">800</td>
                <td><a href="javascript:;"><img src="/Lotto/images/del.png"></a></td>
            </tr>
        </table>
        <p>&nbsp;</p>
        <p>請按下「確定下注」送出注單，即可完成下注程序</p>
        <button>清除暫存</button><button>送出注單</button>




    </body>
    <script>
        $(window).load(function () {
            $('#loading').fadeIn('fast');
        });
    </script>
</html>
