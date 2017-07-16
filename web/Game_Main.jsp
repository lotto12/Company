<%-- 
    Document   : Game_Main
    Created on : 2017/5/5, 下午 02:02:04
    Author     : JimmyYang
--%>

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

            //遊戲目錄
            //GamePage/801/game1/game1.jsp
            String url = "GamePage/" + gtype + "/game" + game_type + "/game" + game_type + ".jsp";
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript" src="/Lotto/layer/layer.js"></script>
        <SCRIPT LANGUAGE = "JavaScript" SRC = "/Lotto/js/GameMain/GameMain.js" ></SCRIPT>
        <link href="css/index.css" rel="stylesheet" type="text/css">
        <title>遊戲主頁</title>
    </head>
    <!--    遊戲主頁面-->
    <!--    遊戲在GamePage資料夾底下-->
    <body>
        <!-- <h1>我是遊戲首頁，我在WebPage/index.jsp</h1> -->
        <div class="wrap" id='loading' style="display:none">
            <section style="width:1366px;height:500px;margin:0 auto;float:left;margin-top:15px;">
                <!--                <input type="button" onclick="show_price();" value="價目表浮動視窗(測試用)"></input>-->



                <!--                以下為下注單，需要用Div包住並靠左-start-->
                <!--                <div style="background: rgb(76,76,76); width: 190px;height:500px; border:1px solid #DA8644;float:left;color:#fff;">-->
                <iframe src="Odd/Odds.jsp" name="Odd" marginwidth="0" marginheight="0" scrolling="No" frameborder="0" id="Odd"  style="background: rgb(76,76,76); width: 190px;height:500px; border:1px solid #DA8644;float:left;color:#fff;">
                </iframe>
                <!--                </div>-->
                <!--                下注單-END-->



                <!--             ->




                <!             以下為遊戲內容頁，需要用Div包住並靠右-start-->
                <!--                ※命名規則
                                801、802、803遊戲種類(801:六和、802:大樂、803:539)
                                game1~8各遊戲玩法(game1:特別號、game2:正特碼、game3:全車...)
                                Ex. GamePage/801/game1/game1.jsp ==> 六合彩_特別號
                -->
                <!--                <div style="background: rgb(76,76,76);width:1100px;border:1px solid #DA8644;height:500px;float:left;color:#fff;">-->

                <iframe src="<%=url%>" name="Game" marginwidth="0" marginheight="0" scrolling="No" frameborder="0" id="Game" style="background: rgb(76,76,76);width:1150px;border:1px solid #DA8644;height:500px;float:left;color:#fff;">
                </iframe> 

                <!--                </div>-->
                <!--                遊戲內容頁---END-->
            </section>
        </div>
    </body>
    <script>
        $(window).load(function () {
            $('#loading').fadeIn('fast');
        });
    </script>
</html>

