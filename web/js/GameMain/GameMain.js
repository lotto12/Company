/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function show_price() {
    layer.open({
        type: 2 
        , title: '價目表浮動視窗'
        , area: ['390px', '330px']
        , shade: 0
        , offset: [
            100, 100
        ]
        , maxmin: true
        , content: 'Game_Price.jsp'
        , btn: ['關閉'] 
        , btn2: function () {
            layer.closeAll();
        }

        , zIndex: layer.zIndex 
        , success: function (layero) {
            layer.setTop(layero);
        }
    });
}
