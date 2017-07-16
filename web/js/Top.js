/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

//變更選單顏色
function clickBtn(obj) {
    init();
    obj.style.color = "#FF3333";
}

//色彩還原
function init() {
    for (var i = 1; i <= 9; i++) {
        document.getElementById("b" + i).style.color = "";
    }
}

//時間
function ShowTime() {
    var time = new Date().Format("yyyy-MM-dd hh:mm:ss");
    document.getElementById('show_time').innerHTML = time;
    setTimeout('ShowTime()', 1000);
}

//傳送控制項目_GTYPE
function setGtype(Gtype) {
    var theForm = document.forms['gtype'];
    addHidden(theForm, 'Gtype', Gtype);
    theForm.submit();
}

//傳送控制項目_TYPE
function set_type(type) {
    var theForm = document.forms['type'];
    document.getElementById('type').value = type;
    theForm.submit();
}

//增加隱藏項目
function addHidden(theForm, key, value) {
    // Create a hidden input element, and append it to the form:
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = key;
    'name-as-seen-at-the-server';
    input.value = value;
    theForm.appendChild(input);
}