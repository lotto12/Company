/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebPage;

/**
 *
 * @author JimmyYang ../System/Top.jsp 使用
 */
public class Top {

    //上方控制列顯示使用
    public String setGame_Button(String gtype) {
        String front_str = "<li><a href=\"javascript:";
        String front_str_b = ";init();\"\">";
        String back_str = "</a></li>";
        String[] data_array = {
            "特&nbsp;別&nbsp;號", "正特馬雙面",
            "全&nbsp;&nbsp;&nbsp;&nbsp;車", "台&nbsp;&nbsp;&nbsp;&nbsp;號",
            "特&nbsp;尾&nbsp;三", "二三四星",
            "天&nbsp;&nbsp;&nbsp;&nbsp;碰",
            "特殊包牌", "快速打單"};
        StringBuilder str = new StringBuilder();
        int[] set_btn = null;
        switch (gtype) {
            case "801":
                //六合彩
                set_btn = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
                break;
            case "802":
                //大樂透
                set_btn = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
                break;
            case "803":
                //539
                set_btn = new int[]{2, 3, 5, 7, 8};
                break;
        }
        //組合Button
        for (int i = 0; i < set_btn.length; i++) {
            String js_onclick = "set_type('" + String.valueOf(set_btn[i] + 1) + "')";

            String data
                    = front_str
                    + js_onclick //type
                    + front_str_b
                    + data_array[set_btn[i]] //遊戲按鈕
                    + back_str;
            str.append(data);
        }
        return str.toString();
    }
}
