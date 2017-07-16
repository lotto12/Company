/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//傳送資料專用_web socket_edit by Jimmy 20170428

var wsocket;
var isOnline = false;
var check_key = "error";

//連線
function connect() {
    //建立連線
    document.getElementById("status").style.color = "#CC0000";
    document.getElementById("status").innerHTML = "連線中...";
    wsocket = new WebSocket("ws://jimmyyang.ddns.net:8084/Lotto/OrderServer");
    wsocket.onmessage = onMessage;
}

//接收訊息
function onMessage(evt) {
    var system_msg = evt.data;
    var json_data = JSON.parse(system_msg);
    switch (json_data.type) {
        case "check_user":
            //完成連線
            isOnline = true;
            document.getElementById("status").style.color = "#66DD00";
            document.getElementById("status").innerHTML = "已連線";
            check_key = json_data.key;
            break;
        case "check_online":
            //已建立連線
            var client_pwd = document.getElementById("client_pwd").value;
            var check_msg = new Object();
            check_msg.function = "check_user";
            check_msg.client_pwd = client_pwd;
            doSend(JSON.stringify(check_msg));
            break;
        case "check_order":
            //已確認下單
            layer.closeAll();
            if (json_data.result === "true") {
                layer.msg('下注成功', {
                    time: 0, //20s後關閉
                    btn: [' 確認 '],
                    yes: function () {
                        initView();
                    }
                });
            } else {
                layer.msg('下注失敗');
            }
            break;
    }
}

//畫面重整
function initView() {
    location.href = 'Odds.jsp?reset=1';
}


//傳送訊息
function doSend(message) {
    //document.getElementById('text').value = "";
    wsocket.send(message);
}

window.addEventListener("load", connect, false);


//以下傳送注單
//傳送下注單
function order_send() {
    layer.load(1, {shade: false});
    var send_data = new Object();

    //注單基本資料START
    send_data.function = "order"; //功能種類
    send_data.check_key = check_key; //檢查碼
    send_data.game_id = "GS04"; //賽事ID
    send_data.account = account; //帳號
    //注單基本資料END

    //注單資料-各玩法
    send_data.order_data = order_data; //注單資料

    //傳送
    doSend(JSON.stringify(send_data));
}

//注單使用JS檔案

//下注確認
function orderCheck() {
    layer.msg('確認下注?', {
        time: 20000, //20s後關閉
        btn: ['確認', '取消'],
        yes: function () {
            if (isOnline && check_key !== "error") {
                //下注
                order_send();
            }
        },
        btn2: function () {
            layer.msg('已取消');
        }
    });
}


