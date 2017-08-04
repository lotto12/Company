/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var order_step = 1; //目前步驟
var type = -1; //玩法種類
var stype = 1; //單碰柱碰種類
var gtype; //遊戲種類--------->須從前端匯入

var order = []; //注單
var odd;
var back_gold;

var send_order = false; //是否要下單

//------------------------柱單------------------------
//注單格式(多筆)
var Order_array = new Array();
var Order_result = new Object();
Order_result.order_id = "S046-533";
Order_result.data = Order_array;

var Order = new Object();

//------------------------柱單------------------------

//初始
function init(set_gtype) {
    document.getElementById('step1').focus();
    gtype = set_gtype;
    Order_result.gtype = gtype;
}

//取得使用者焦點
function setFocus(step) {
    order_step = step;
    console.log('now_step:' + order_step);
}

//下一個步驟
function nextStep() {
    //判斷種類
    switch (order_step) {
        case 1:
            //玩法確認
            console.log('type_set');

            //使用者輸入
            var user_type = document.getElementById('step1').value;
            console.log('user_type:' + user_type);
            switch (user_type) {

//-----------------------------------------二星
                case '21': //二星
                    type = 6;
                    stype = 1;
                    break;
                case '22': //二星連碰
                    type = 6;
                    stype = 2;
                    break;
                case '23': //二星柱碰
                    type = 6;
                    stype = 3;
                    break;
//-----------------------------------------二星

//-----------------------------------------三星
                case '31': //三星
                    type = 7;
                    stype = 1;
                    break;
                case '32': //三星連碰
                    type = 7;
                    stype = 2;
                    break;
                case '33': //三星柱碰
                    type = 7;
                    stype = 3;
                    break;
//-----------------------------------------二星

//-----------------------------------------四星
                case '41': //三星
                    type = 7;
                    stype = 0;
                    break;
                case '42': //三星連碰
                    type = 7;
                    stype = 2;
                    break;
                case '43': //三星柱碰
                    type = 7;
                    stype = 3;
                    break;
//-----------------------------------------四星

//-----------------------------------------特別號
                case '51': //特別號
                    type = 1;
                    stype = 1;
                    break;
//-----------------------------------------特別號

//-----------------------------------------正特碼雙面
                case '61': //正特碼雙面
                    type = 2;
                    stype = 1;
                    break;
//-----------------------------------------正特碼雙面

//-----------------------------------------全車
                case '71': //全車
                    type = 3;
                    stype = 1;
                    break;
//-----------------------------------------全車

//-----------------------------------------台號
                case '81': //台號
                    type = 4;
                    stype = 1;
                    break;
//-----------------------------------------台號

//-----------------------------------------特尾三
                case '91': //特尾三
                    type = 5;
                    stype = 1;
                    break;
//-----------------------------------------特尾三

//-----------------------------------------天碰二-連碰
                case '28': //天碰二-連碰
                    type = 9;
                    stype = 2;
                    break;
//-----------------------------------------天碰二-連碰

//-----------------------------------------天碰二-柱碰
                case '29': //天碰二-柱碰
                    type = 9;
                    stype = 3;
                    break;
//-----------------------------------------天碰二-柱碰

//-----------------------------------------天碰三-連碰
                case '38': //天碰二-連碰
                    type = 10;
                    stype = 2;
                    break;
//-----------------------------------------天碰三-連碰

//-----------------------------------------天碰三-柱碰
                case '39': //天碰二-柱碰
                    type = 10;
                    stype = 3;
                    break;
//-----------------------------------------天碰三-柱碰

//-----------------------------------------錯誤指令
                default:
                    type = -1;
                    stype = 1;
//-----------------------------------------錯誤指令
            }
            break;
        case 2:
            //下注號碼確認
            console.log('num_set');
            var result = getBall_result();
            var order_str = "";
            order.push(result);
            for (var i = 0; i < order.length; i++) {
                order_str += '第' + (i + 1) + '柱:';
                order_str += order[i];
                order_str += '<br>';
            }
            document.getElementById('order').innerHTML = order_str;
            result = "";
            document.getElementById('step2').value = result;

            //設定完成
            document.getElementById('step2').disabled = true;
            document.getElementById('num_txv').style.color = 'blue';
            document.getElementById('num_txv').innerHTML = ' [ 完成 ] ';
            break;
    }


    order_step++;
    document.getElementById('step' + order_step).focus();

    type_set();
}

//快速鍵
function key_set(set_type) {
    stype = 1;
    type = set_type;
    order_step = 2;
    document.getElementById('step2').focus();

    type_set();
}

//玩法確認
function type_set() {
    var types = ["特別碼", "正特碼", "全車", "台號", "特尾三", "二星", "三星", "四星", "天碰二", "天碰三"];
    var stypes = ["單碰", "連碰", "柱碰", "連柱碰"];
    if (type !== -1) {
        var type_str = types[type - 1];
        if (stype !== 0) {
            type_str += '-' + stypes[stype - 1];
        }
        document.getElementById('type').style.color = 'blue';
        document.getElementById('type').innerHTML = ' [ ' + type_str + ' ] ';
    } else {
        order_step = 1;
        document.getElementById('step1').value = '';
        document.getElementById('step' + order_step).focus();
        document.getElementById('type').innerHTML = '<font style="color: red"> [ ' + '輸入錯誤' + ' ] </font>';
    }
}

//偵測使用者按下的動作
document.onkeyup = function () {
    var keycode = event.which || event.keyCode;
    console.log('keycode:' + keycode);
    if (order_step === 2) {
        if (keycode !== 8) {
            var result = getBall_result();
            if (keycode === 107) {
                //增加一柱
                //order.push(order_data);
                order.push(result);
                var order_str = "";
                for (var i = 0; i < order.length; i++) {
                    order_str += '第' + (i + 1) + '柱:';
                    order_str += order[i];
                    order_str += '<br>';
                }
                document.getElementById('order').innerHTML = order_str;
                result = "";
            }
            document.getElementById('step2').value = result;
        } else {
            //刪除
            var num_str = getBall_result();
            num_str = num_str.substring(0, num_str.lastIndexOf(','));
            document.getElementById('step2').value = num_str;
        }
    }

    switch (keycode) {
        case 13: //ENTER
            if (order_step < 3) {
                //下一個步驟
                nextStep();
            } else {
                if (!send_order) {
                    getData(getSend_BallNum());
                } else {
                    console.log('order_save');
                    send_order = false;

                    //注單結果
                    var order_data_send = JSON.stringify(Order_result);
                    console.log('Final:' + order_data_send);
                    alert(order_data_send);

                    //傳送
                    send_Order(order_data_send);

                    //重設柱單
                    reset();

                }
            }
            break;
        case 81: //Qq 二星
            document.getElementById('step1').value = 21;
            //下一個步驟
            key_set(6);
            break;
        case 87: //Ww 三星
            document.getElementById('step1').value = 31;
            //下一個步驟
            key_set(7);
            break;
        case 69: //Ee 四星
            document.getElementById('step1').value = 41;
            //下一個步驟
            key_set(8);
            break;
        case 82: //Rr 特別號
            document.getElementById('step1').value = 51;
            //下一個步驟
            key_set(1);
            break;
        case 84: //Tt 雙面
            document.getElementById('step1').value = 61;
            //下一個步驟
            key_set(2);
            break;
        case 89: //Yy 全車
            document.getElementById('step1').value = 71;
            //下一個步驟
            key_set(3);
            break;
        case 65: //Aa 全車
            document.getElementById('step1').value = 81;
            //下一個步驟
            key_set(3);
            break;
        case 83: //Ss 特尾三
            document.getElementById('step1').value = 91;
            //下一個步驟
            key_set(5);
            break;
        case 27: //重設
            reset();
            break;
        case 80: //P 增加一著
            order.push(result);
            var order_str = "";
            for (var i = 0; i < order.length; i++) {
                order_str += '第' + (i + 1) + '柱:';
                order_str += order[i];
                order_str += '<br>';
            }
            document.getElementById('order').innerHTML = order_str;
            result = "";

            document.getElementById('step2').value = result;
            break;
    }
};

//重設柱單
function reset() {

    odd = 0;
    back_gold = 0;

    //隱藏柱單
    document.getElementById('order_result').style.display = "none";

    //清理
    order = [];
    Order_array = new Array();
    document.getElementById('order').innerHTML = '';

    //清空金額
    document.getElementById('step3').value = '';

    //清空下注球號
    document.getElementById('step2').value = '';

    //球-狀態修改
    document.getElementById('num_txv').style.color = 'red';
    document.getElementById('num_txv').innerHTML = ' [ 尚未設定 ] ';

    //錢-狀態修改
    document.getElementById('pay_txv').style.color = 'red';
    document.getElementById('pay_txv').innerHTML = ' [ 尚未設定 ] ';

    //重新開始下單
    document.getElementById('step2').disabled = false;
    document.getElementById('step3').disabled = false;

    //回到下注金額
    order_step = 2;
    document.getElementById('step' + order_step).focus();

}

//重組球號格式
function getSend_BallNum() {
    var num = new Object();
    var result_json;
    if (type <= 5) {
        //回傳單數
        var data = order[0].split(',');
        result_json = data[0];
    } else {
        var Order_array = new Array();
        for (var i = 0; i < order.length; i++) {
            var order_row = new Array();
            var data = order[i].split(',');
            for (var j = 0; j < data.length; j++) {
                order_row.push(parseInt(data[j]));
            }
            Order_array.push(order_row);
        }
        num.result = Order_array;
        result_json = JSON.stringify(num);
    }
    return result_json;
}

//回傳球號
function getBall_STR() {
    var order_str = "";
    for (var i = 0; i < order.length; i++) {
        order_str += '第' + (i + 1) + '柱:';
        order_str += order[i];
        order_str += '<br>';
    }
    return order_str;
}

//組合球號
function getBall_result() {
    var result = ""; //結果

    var order_data = document.getElementById('step2').value;
    var order_spilt = order_data.split("+");
    console.log("order_spilt.length:" + order_spilt.length);
    for (var i = 0; i < order_spilt.length; i++) {
        order_data = order_spilt[i].replace(/,/g, "");
        console.log('order_data:' + order_data);
        var num_data = order_data.split("");
        var count = 0;
        for (var i = 0; i < num_data.length; i++) {
            result += num_data[i];
            if (num_data[i] !== ',' && i !== num_data.length - 1) {
                count++;
                if (count === 2) {
                    result += ",";
                    count = 0;
                    //判斷重複球號
                    //isCheck_Num(result);
                }
            }
        }
    }
    return result;
}

//回傳賠率、價錢
function getData(ball) {
    var url = "/Lotto/GetSetting?gtype=" + gtype + "&type=" + type + "&ball=" + ball;
    console.log(url);
    $.get(url, function (data, status) {
        var obj = JSON.parse(data);
        var status = obj.status;
        if (status === true) {
            var odd_s = obj.odd;
            var back_gold_s = obj.back_gold;
            odd = odd_s;
            back_gold = back_gold_s;

            var pay = document.getElementById('step3').value;
            //下注顯示----------------------

            //柱單內容
            Order.num = getSend_BallNum();
            Order.price = back_gold;
            Order.pay = pay * getGroup_num();
            Order.odds = odd;
            Order.group_num = getGroup_num();
            Order.stype = stype;
            Order.type = type;

            //增加一筆
            Order_array.push(Order);

            document.getElementById('set_gtype').innerHTML = Order_result.gtype;
            document.getElementById('set_remarks').innerHTML = Order_result.order_id;
            document.getElementById('set_type').innerHTML = getType(Order.type);
            document.getElementById('set_stype').innerHTML = getStype(Order.stype);
            document.getElementById('set_num').innerHTML = getBall_STR();
            document.getElementById('set_price').innerHTML = Order.price;
            document.getElementById('set_odds').innerHTML = Order.odds;
            document.getElementById('set_group_num').innerHTML = Order.group_num;
            document.getElementById('set_pay').innerHTML = Order.pay;

            //下注顯示---------------------- 20170713

            //設定完成
            document.getElementById('pay_txv').style.color = 'blue';
            document.getElementById('pay_txv').innerHTML = ' [ 完成 ] ';

            //顯示柱單
            document.getElementById('order_result').style.display = "";

            //清空金額
            document.getElementById('step3').value = '';
            document.getElementById('step3').disabled = true;

            send_order = true;
        } else {
            alert("連線逾時!");
            top.window.location.replace("/Lotto/index.html");
        }
    });
}

//回傳組數
function getGroup_num() {
    var result;
    switch (type) {
        case 1: //特別號
            result = 1;
            break;
        case 2: //正特碼
            result = 1;
            break;
        case 3: //全車
            result = 1;
            break;
        case 4: //台號
            result = 1;
            break;
        case 5: //特尾三
            result = 1;
            break;
        case 6: //二星
            if (order.length > 1) {
                //立柱
                stype = 3;
                result = groupSplit(2);
            } else {
                //連碰
                stype = 2;
                var data = order[0].split(",");
                var length = data.length;
                result = (length * (length - 1)) / 2;
            }
            break;
        case 7: //三星
            if (order.length > 1) {
                //立柱
                stype = 3;
                result = groupSplit(3);
            } else {
                //連碰
                stype = 2;
                var data = order[0].split(",");
                var length = data.length;
                result = (length * (length - 1) * (length - 2)) / 6;
            }
            break;
        case 8: //四星
            if (order.length > 1) {
                //立柱
                stype = 3;
                result = groupSplit(4);
            } else {
                //連碰
                stype = 2;
                var data = order[0].split(",");
                var length = data.length;
                result = (length * (length - 1) * (length - 2) * (length - 3)) / 24;
            }
            break;
        case 9: //天碰二
            break;
        case 10: //天碰三
            break;
        default:
            result = 0;
            break;
    }
    return result;
}

//立柱
function groupSplit(size) {
    var array = [];
    for (var i = 0; i < order.length; i++) {
        array.push(order[i].split(",").length);
    }

    //計算開始
    var r = []; //result

    function _(t, a, n) { //tempArr, arr, num
        if (n === 0) {
            r[r.length] = t;
            return;
        }
        for (var i = 0, l = a.length - n; i <= l; i++) {
            var b = t.slice();
            b.push(a[i]);
            _(b, a.slice(i + 1), n - 1);
        }
    }
    _([], array, size);
    console.log(r);

    var total = 0;
    for (var i = 0; i < r.length; i++) {
        var d = r[i];
        var total_m = 1;
        for (var j = 0; j < d.length; j++) {
            total_m *= d[j];
        }
        total += total_m;
    }
    return total;
}

function getType(chose) {
    var types = ["特別碼", "正特碼", "全車", "台號", "特尾三", "二星", "三星", "四星", "天碰二", "天碰三"];
    return types[chose - 1];
}

function getStype(chose) {
    var stypes = ["單碰", "連碰", "柱碰", "連柱碰"];
    return stypes[chose - 1];
}

function send_Order(order) {
    var url = "/Lotto/AddOrder?&order=" + order;
    console.log(url);
    $.get(url, function (data, status) {
        var obj = JSON.parse(data);
        var status = obj.status;
        if (status === true) {
            var spc_status = obj.spc_status;
            var fix_status = obj.fix_status;

            if (spc_status || fix_status) {
                alert('下注成功,符合特殊牌型或固定賠牌型');
            } else {
                alert('下注成功');
            }
        }
    });
}

//確認是否有重複球號
function isCheck_Num(str) {
    console.log('str:' + str);
    var check_data = str.split(',');
}
