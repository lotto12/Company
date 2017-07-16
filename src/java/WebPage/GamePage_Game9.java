/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebPage;

import java.util.ArrayList;
import model.DB;
import model.DataTable;

/**
 *
 * @author JimmyYang 快速打單
 */
public class GamePage_Game9 {

    //取得玩家各玩法上限值
    public ArrayList<String[]> getAccount_Conf_Data(String gtype, String account) {
        ArrayList<String[]> result = new ArrayList<>();
        String sql_select = "select conf.one_limit , conf.num_limit,conf.type "
                + "from game_member_conf conf join game_member mem on conf.master_id  = mem.id "
                + "where conf.gtype=" + gtype + " and mem.acount = '" + account + "' "
                + "order by conf.type asc";
        DataTable dt = DB.getDataTable(sql_select);
        if (dt.getRow() > 0) {
            for (int i = 0; i < dt.getRow(); i++) {
                String[] data = new String[3];
                data[0] = dt.getColume(i, "one_limit");
                data[1] = dt.getColume(i, "num_limit");
                data[2] = dt.getColume(i, "type");
                result.add(data);
            }
        }
        return result;
    }

    public String getTable(String gtype, String account) {
        String[] type_name = {
            "特別碼", "正特碼", "全車",
            "台號", "特尾三", "二星",
            "三星", "四星", "天碰二",
            "天碰三"};
        StringBuilder table = new StringBuilder();
        ArrayList<String[]> data_array = getAccount_Conf_Data(gtype, account);
        for (String[] data : data_array) {
            int type = Integer.parseInt(data[2]);

            String one_limit = data[0];
            String num_limit = data[1];
            String game_type = type_name[type - 1];

            table.append("<tr>");
            table.append("<td>").append(game_type).append("</td>"); //玩法
            table.append("<td>").append(num_limit).append("</td>"); //單碰上限
            table.append("<td>").append("0").append("</td>"); //單碰下陷
            table.append("<td>").append(one_limit).append("</td>"); //單組上限
            table.append("</tr>");
        }
        return table.toString();
    }
}
