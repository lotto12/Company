<%-- 
    Document   : game1
    Created on : 2017/5/5, 下午 02:11:50
    Author     : JimmyYang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>六合彩-特別號</title>
        <!--        <SCRIPT LANGUAGE = "JavaScript" SRC = "/Lotto/js/WebSocket.js" ></SCRIPT>-->

        <!--        STEP1 JS載入START-->
        <%
            //檢查帳號有無登入，未登入則返回登入畫面
            String user_acount = (String) session.getAttribute("user_acount");
            if (user_acount == null) {
                out.print("<script>top.window.location.replace(\"/Lotto/index.html\");</script>");
            }
        %>
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <SCRIPT LANGUAGE = "JavaScript" SRC = "/Lotto/js/801/game1.js" ></SCRIPT>
        <script type="text/javascript" src="/Lotto/layer/layer.js"></script>
        <!--        STEP1 JS載入END-->

        <!--        P.S 目錄使用方式 /Lotto/css/...-->
        <link rel="stylesheet" type="text/css" href="/Lotto/css/801/game1.css">
    </head>
    <body>
        <!--        測試用START
        <form name="add_order" action="/Lotto/Order" method="post" target="Odd">
            <input type=hidden id="function" name="function" value="add_order"/>
            <input type=hidden id="gtype" name="gtype" value="0"/>  
            <input type=hidden id="type" name="type" value="0"/>  
            <input type=hidden id="data" name="data" value="0"/> 
            <input type=hidden id="acount" name="acount" value="<%=user_acount%>"/> 
            <input type="button" onclick="addOrder();" value="增加注單(測試用)"></input>
        </form>-->
        <!--        測試用END-->



        <!--左二-->
        <div class="bet_price">
            <table class="table3" border="1"  bordercolor="#C8722D" cellspacing="0" cellpadding="0">
                <tr><td><button>超快速下注</button></td></tr>
                <tr><td class="header">支數累計</td></tr>
                <tr><td>號碼<input type="text" size="3">支數<input type="text" size="5"></td></tr>
                <tr><td class="header">特碼累計</td></tr>
                <tr><td>號碼<input type="text" size="3">全額<input type="text" size="5"></td></tr>

                <tr><td class="header">快速選號</td></tr>
                <tr>
                    <td>
                        <p>
                            <a href="javascript:;" id="red">紅波</a>
                            <a href="javascript:;" id="blue">藍波</a>
                            <a href="javascript:;" id="green">綠波</a>
                        </p>
                    </td></tr>
                <tr><td>
                        <p>
                            <a href="javascript:;" id="odd">單</a>
                            <a href="javascript:;" id="even">雙</a>
                            <a href="javascript:;" id="big">大</a>
                            <a href="javascript:;" id="small">小</a>
                            <a href="javascript:;" id="all">全</a>
                        </p>
                    </td></tr>
                <tr><td class="header">十位數（頭數）</td></tr>
                <tr><td>
                        <p>
                            <a href="javascript:;" id="tens0">0</a>
                            <a href="javascript:;" id="tens1">1</a>
                            <a href="javascript:;" id="tens2">2</a>
                            <a href="javascript:;" id="tens3">3</a>
                            <a href="javascript:;" id="tens4">4</a>
                        </p>
                    </td></tr>
                <tr><td class="header">個位數（頭數）</td></tr>
                <tr><td>
                        <p style="display:block; height:10px;">
                            <a href="javascript:;" id="digits0">0</a>
                            <a href="javascript:;" id="digits1">1</a>
                            <a href="javascript:;" id="digits2">2</a>
                            <a href="javascript:;" id="digits3">3</a>
                            <a href="javascript:;" id="digits4">4</a>
                        </p>
                        <p>
                            <a href="javascript:;" id="digits5">5</a>
                            <a href="javascript:;" id="digits6">6</a>
                            <a href="javascript:;" id="digits7">7</a>
                            <a href="javascript:;" id="digits8">8</a>
                            <a href="javascript:;" id="digits9">9</a>
                        </p>
                    </td></tr>
                <tr><td class="header">十位數與個位數相加</td></tr>
                <tr><td>
                        <p>
                            <a href="javascript:;" id="totalodd">總和單</a>
                            <a href="javascript:;" id="totaleven">總和雙</a>
                        </p>
                        <p>
                            <a href="javascript:;" id="totalbig">總和大</a>
                            <a href="javascript:;" id="totalsmall">總和小</a>
                        </p>
                    </td></tr>
                <tr><td class="header">加注金額</td></tr>
                <tr><td>
                        （選取右方號碼累加）<br>
                        累加<input type="text" size="8">元
                    </td></tr>
                <tr><td><button>加注金額</button></td></tr>
            </table>
        </div>
        <!--左三-->
        <div class="number">
            <table class="table4"><!--最外面的恇-->
                <tr><td colspan="5" class="header">六合-特碼（一般下注）</td></tr>
                <!--1-->
                <table class="table5" style="float:left;" border="0" cellpadding="0" cellspacing="0">
                    <tr class="header" bgcolor="#D9671C;">
                        <td>特碼</td>
                        <td>本金</td>
                        <td>下注金額</td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballred">01</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballred">02</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballblue">03</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballblue">04</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballgreen">05</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballgreen">06</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>   
                    <tr>
                        <td rowspan="2"><span class="ballred">07</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballred">08</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballblue">09</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>

                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballblue">10</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>

                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                </table>

                <!--2-->
                <table class="table5" style="float:left;" border="0" cellpadding="0" cellspacing="0">
                    <tr class="header">
                        <td>特碼</td>
                        <td>本金</td>
                        <td>下注金額</td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballgreen">11</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>

                    <tr>
                        <td rowspan="2"><span class="ballred">12</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballred">13</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballblue">14</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>

                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballblue">15</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>

                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballgreen">16</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>   
                    <tr>
                        <td rowspan="2"><span class="ballgreen">17</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballred">18</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballred">19</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballblue">20</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                </table>
                <!--3-->
                <table class="table5" style="float:left;" border="0" cellpadding="0" cellspacing="0">
                    <tr class="header">
                        <td>特碼</td>
                        <td>本金</td>
                        <td>下注金額</td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballgreen">21</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6" style="height:10px;"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballgreen">22</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballred">23</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballred">24</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballblue">25</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballblue">26</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>   
                    <tr>
                        <td rowspan="2"><span class="ballgreen">27</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballgreen">28</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballred">29</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballred">30</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                </table>
                <!--4-->
                <table class="table5" style="float:left;" border="0" cellpadding="0" cellspacing="0">
                    <tr class="header">
                        <td>特碼</td>
                        <td>本金</td>
                        <td>下注金額</td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballblue">31</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6" style="height:10px;"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballgreen">32</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballgreen">33</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballred">34</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballred">35</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballblue">36</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>   
                    <tr>
                        <td rowspan="2"><span class="ballblue">37</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballgreen">38</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballgreen">39</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballred">40</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                </table>
                <!--5-->
                <table class="table5" style="float:left;" border="0" cellpadding="0" cellspacing="0">
                    <tr class="header">
                        <td>特碼</td>
                        <td>本金</td>
                        <td>下注金額</td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballblue">41</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6" style="height:10px;"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballblue">42</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballgreen">43</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballgreen">44</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballred">45</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballred">46</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>   
                    <tr>
                        <td rowspan="2"><span class="ballblue">47</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballblue">48</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><span class="ballgreen">49</span></td>
                        <td rowspan="2"><span class="basicprice">77</span></td>
                        <td><span class="betprice">60000</span></td>
                    </tr>
                    <tr>
                        <td><input type="text" size="6"></td>
                    </tr>
                    <tr bgcolor="none;">
                        <td colspan="3">
                            <button style="height:30px; width:40px; margin-top:11px;margin-bottom:5px;">清除</button>
                            <button style="height:30px; width:70px;">加入注單</button>
                        </td>
                    </tr>
                </table>

            </table>
        </div>
        <!--右一-->
        <div class="list_info">
            <table class="table6" border="1" cellpadding="0" cellspacing="0">
                <tr><td class="header">組數倍數</td></tr>
                <tr><td>
                        <p>單筆上限</p>
                        <p><span class="red">1.5</span> 倍</p>
                        <p>（<span class="red">30000</span>）</p>
                    </td></tr>
                <tr><td class="header">雙面包牌</td></tr>
                <tr>
                    <td colspan="2">項目</td>
                    <td>本金</td>
                </tr>
                <tr>
                    <td><input type="checkbox"></td>
                    <td>單</td>
                    <td>73.7</td>
                </tr>
                <tr>
                    <td><input type="checkbox"></td>
                    <td>雙</td>
                    <td>73.7</td>
                </tr>
                <tr>
                    <td><input type="checkbox"></td>
                    <td>大</td>
                    <td>73.7</td>
                </tr>
                <tr>
                    <td><input type="checkbox"></td>
                    <td>小</td>
                    <td>73.7</td>
                </tr>
                <tr>
                    <td><p>總下注金額：</p><p class="red">0</p><p><input type="text" size="12"></p><p><button>送出注單</button></p></td>
                </tr>
            </table>
            <table class="table7" border="1">
                <tr><td class="header">配比包牌<button style="width:50px;">說明</button></td>
                </tr>
                <tr>
                    <td>配比</td>
                    <td>本金</td>
                </tr>
                <tr>
                    <td>1:40</td>
                    <td>73.6</td>
                </tr>
                <tr>
                    <td>1:30</td>
                    <td>73.7</td>
                </tr>
                <tr>
                    <td>1:24</td>
                    <td>73.7</td>
                </tr>
                <tr>
                    <td>1:20</td>
                    <td>73.7</td>
                </tr>

                <tr>
                    <td><button>送出注單</button></td>
                </tr>
            </table>
        </div>

        <!--        <h3>六合彩_特別號 我在 ../GamePage/801/game1/game1.jsp</h3>-->
    </body>
</html>
