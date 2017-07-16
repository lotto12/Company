/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//傳送注單資料專用_web socket_edit by Jimmy 20170428
//對應端/Lotto/OrderServer

var wsocket;

//連線
function connect() {
    wsocket = new WebSocket("ws://jimmyyang.ddns.net:8084/Lotto/Server");
    wsocket.onmessage = onMessage;
}

//接收訊息
function onMessage(evt) {
    //var Data = evt.data;
    //document.getElementById("text").innerHTML = Data;
}

//傳送訊息
function doSend(message) {
    //document.getElementById('text').value = "";
    wsocket.send(message);
}


window.addEventListener("load", connect, false);
