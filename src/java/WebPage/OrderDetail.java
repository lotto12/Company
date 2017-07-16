/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebPage;

import model.DB;
import model.DataTable;

/**
 *
 * @author JimmyYang 下注明細使用
 */
public class OrderDetail {

    //測試顯示所有歷史紀錄
    public String showDetialTable(String account) {
        StringBuilder result = new StringBuilder();
        String sql_select = "select * from game_warges_main where account='" + account + "'order by id desc";
        DataTable dt = DB.getDataTable(sql_select);
        String[] title = dt.getColumn_Name();

        //產生標頭
        result.append("<table border=\"1\">");

        //欄位名稱
        result.append("<tr>");
        for (int i = 0; i < title.length; i++) {
            result.append("<th>");
            result.append(title[i]);
            result.append("</th>");
        }
        result.append("</tr>");

        //資料顯示
        for (int i = 0; i < dt.getRow(); i++) {
            result.append("<tr>");
            for (int j = 0; j < title.length; j++) {
                result.append("<td>");
                result.append(dt.getColume(i, title[j]));
                result.append("</td>");
            }
            result.append("</tr>");
        }
        
        //END
        result.append("</table>");

        return result.toString();
    }

}
