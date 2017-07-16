/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$("#table").on("mouseenter", "tr", function () {
    $(this).addClass("enter"); // 觸發 mouseenter 事件 新增 css 
}).on("mouseout", "tr", function () {
    $(this).removeClass("enter"); // 觸發 mouseout 事件 移除 css 
});
