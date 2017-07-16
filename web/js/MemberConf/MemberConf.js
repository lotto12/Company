/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//顯示改密碼
function show_changePwd() {
    layer.open({
        type: 2
        , title: '更改密碼'
        , area: ['480px', '290px']
        , shade: 0
        , offset: [
            100, 100
        ]
        , maxmin: true
        , content: 'ChangePwd.jsp'
        , zIndex: layer.zIndex
        , success: function (layero) {
            layer.setTop(layero);
        }
    });
}

