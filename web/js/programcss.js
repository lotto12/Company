/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//選擇功能
function click(id) {
    initList();
    $("#" + id).show(200);
}

//初始化下拉選單
function initList() {
    var list = ["basketball_ul", "basketball_j_ul", "baseball_ul",
        "baseball_j_ul", "baseball_k_ul", "baseball_t_ul",
        "iceball_ul", "football_a_ul", "football_ul"];
    for (var i = 0; i < list.length; i++) {
        //消失
        $("#" + list[i]).hide(200);
    }
}

//Loading
function loading() {
    $('#loading_txv').show(200);
}

