<%-- 
    Document   : Top
    Created on : 2017/4/27, 下午 09:46:27
    Author     : JimmyYang
--%>

<%@page import="WebPage.Top"%>
<%@page import="Function.SystemFunction"%>
<%@page import="Class.Member"%>
<%@page import="Function.GameMember"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            //本頁面需要資料定義
            String game_id = "GS04"; //遊戲期數
            String game_account = null; //會員帳號
            String game_name = null; //會員名稱
            String game_money = null; //會員總額度
            String game_money_temp = null; //會員剩餘額度

            //會員資料_info
            String user_acount = (String) session.getAttribute("user_acount");
            if (user_acount != null) {
                Member member = new GameMember().getMemberData(user_acount);
                game_account = member.getAccount();
                game_name = member.getName();
                game_money = member.getMoney();
                game_money_temp = member.getMoney_temp();
            } else {
                out.print("<script>top.window.location.replace(\"/Lotto/index.html\");</script>");
            }

            //系統資料
            String news_data = new SystemFunction().getNews(); //跑馬燈訊息

            //取得遊戲資料
            String gtype = (String) session.getAttribute("Gtype");
            Top top = new Top();
            if (gtype == null) {
                gtype = "801"; //預設六合彩
            }
        %>        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>功能選單</title>
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <link href="../css/top.css" rel="stylesheet" type="text/css">
        <script src='../js/Top.js'></script>
    </head>
    <body onload="ShowTime()">
        <!--        <h1>我是頂部功能選單，我在Layout/Top.jsp</h1>  -->
        <div class="wrap" id='loading' style="display:none">
            <header>
                <div class="time">
                    <a>現在</a><span class="timecolor" id="show_time">2017-01-01 20:50:50</span>
                    <a>封盤</a><span class="timecolor">2017-01-01 21:28:50</span>
                    <a>開獎</a><span class="timecolor">00:00:37:09</span>
                </div>

                <a target="Mid" href="../Game_Main.jsp" onclick="init();"><div class="logo"></div></a>

                <form name="gtype" action="../Main" method="post" target="_parent">
                    <input type=hidden name="function" value="set_gtype"/>  
                    <div class="method">
                        <a href="javascript:setGtype('801');" id="lottery1">六合</a>
                        <a href="javascript:setGtype('802');" id="lottery2">大樂</a>
                        <a href="javascript:setGtype('803');" id="lottery3">539</a>
                    </div>
                </form>
                <nav class="mainnav">
                    <ul>
                        <li><a id="b1" target="Mid" href="../WebPage/SystemMsg.jsp" onclick="clickBtn(this);">歷史公告</a></li>
                        <li><a id="b2" target="Mid" href="../WebPage/Contact.jsp" onclick="clickBtn(this);">聯絡我們</a></li>
                        <li><a id="b3" target="Mid" href="javascript:;" onclick="clickBtn(this);">規則說明</a></li>
                        <li><a id="b4" target="Mid" href="../WebPage/MemberConf.jsp" onclick="clickBtn(this);">個人資料</a></li>
                        <li><a id="b5" target="Mid" href="javascript:;" onclick="clickBtn(this);">開球結果</a></li>
                        <li><a id="b6" target="Mid" href="../WebPage/OrderDetail.jsp" onclick="clickBtn(this);">下注明細</a></li>
                        <li><a id="b7" target="Mid" href="../WebPage/OrderTotal.jsp" onclick="clickBtn(this);">總&nbsp;&nbsp;&nbsp;&nbsp;帳</a></li>
                        <li><a id="b8" target="Mid" href="../WebPage/OrderHistory.jsp" onclick="clickBtn(this);">歷史總帳</a></li>
                        <li><a id="b9" target="_parent" href="../System/logout.jsp" onclick="clickBtn(this);">登&nbsp;&nbsp;&nbsp;&nbsp;出</a></li>
                    </ul>
                </nav>        

                <div class="info">
                    <a>期數</a><span><%=game_id%></span>
                    <a>帳號</a><span><%=game_account%>(<%=game_name%>)</span>
                    <a>總額度</a><span><%=game_money%>(已使用<%=game_money_temp%>)</span>
                    <a>套餐總額度</a><span>100000(已使用0)</span>
                </div>
            </header>

            <form name="type" action="../Main" method="post" target="Mid">
                <input type=hidden name="function" value="set_type"/>  
                <input id="type" type=hidden name="game_type" value="1"/>  
                <nav class="playnav">
                    <ul>
                        <!--                    <li><a href="javascript:;">特&nbsp;別&nbsp;號</a></li>
                                            <li><a href="javascript:;">正特馬雙面</a></li>
                                            <li><a href="javascript:;">全&nbsp;&nbsp;&nbsp;&nbsp;車</a></li>
                                            <li><a href="javascript:;">台&nbsp;&nbsp;&nbsp;&nbsp;號</a></li>
                                            <li><a href="javascript:;">特&nbsp;尾&nbsp;三</a></li>
                                            <li><a href="javascript:;">二三四星</a></li>
                                            <li><a href="javascript:;">天&nbsp;&nbsp;&nbsp;&nbsp;碰</a></li>
                                            <li><a href="javascript:;">特殊包牌</a></li>
                                            <li><a href="javascript:;">快速打單</a></li>-->
                        <%=top.setGame_Button(gtype)%>
                    </ul>
                </nav>
            </form>

            <marquee onMouseOver="this.stop()" onMouseOut="this.start()">
                <p><%=news_data%></marquee></p>

    </marquee>

</div>
</body>
<script>
    $(window).load(function () {
        $('#loading').fadeIn('fast');
    });
</script>
</html>
