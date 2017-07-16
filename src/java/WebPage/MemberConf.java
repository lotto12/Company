/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebPage;

import Class.*;
import Function.*;
import model.DB;
import model.DataTable;

/**
 *
 * @author JimmyYang 個人資料使用
 */
public class MemberConf {

    //會員資料
    private String user_account;

    //遊戲類型
    private String[] type;

    public MemberConf(String user_account) {
        //初始化會員資料
        this.user_account = user_account;

        //初始化遊戲類型(1~10)
        type = new String[10];
        for (int i = 1; i <= 10; i++) {
            type[i - 1] = String.valueOf(i);
        }
    }

    //取得個人資料表格
    public String getMemberConf() {
        StringBuilder result = new StringBuilder();
        GameMember game_member = new GameMember();
        Member member_data = game_member.getMemberData(user_account); //取得所有會員資料

        //table_start
        result.append("<table border=\"1\">");

        //show_data_標頭
        result.append("<tr>");
        result.append("<th colspan=\"2\">個人資料</th>");
        result.append("</tr>");

        //show_data_代理商
        result.append("<tr>");
        result.append("<td>代理商</td>");
        result.append("<td>上層代理商(尚未完成)</td>");
        result.append("</tr>");

        //show_data_會員帳號
        result.append("<tr>");
        result.append("<td>會員帳號</td>");
        result.append("<td>").append(member_data.getAccount()).append("<a href=\"javascript:show_changePwd();\">修改密碼</></td>");
        result.append("</tr>");

        //show_data_會員名稱
        result.append("<tr>");
        result.append("<td>會員名稱</td>");
        result.append("<td>").append(member_data.getName()).append("</td>");
        result.append("</tr>");

        //show_data_會員總額度
        result.append("<tr>");
        result.append("<td>會員名稱</td>");
        result.append("<td>").append(member_data.getMoney()).append("</td>");
        result.append("</tr>");

        //table_end
        result.append("</table>");

        return result.toString();
    }

    //取得會員設定表格
    public String getTable(String gtype) {
        StringBuilder result = new StringBuilder();
        String sql_select = "select * from game_member_conf where master_id = '" + user_account + "' and gtype = '" + gtype + "' order by type";

        String gtype_name = new GameMethod().getGame_GType(gtype);
        DataTable data_table = DB.getDataTable(sql_select);

        //table_start
        result.append("<table border=\"1\">");

        //show_data_標頭
        result.append("<tr>");
        result.append("<th colspan=\"11\">").append(gtype_name).append("</th>");
        result.append("</tr>");

        //show_data_標題
        result.append("<tr>");
        result.append("<td></td>");
        result.append("<td>正碼</td>");
        result.append("<td>特碼</td>");
        result.append("<td>正特碼雙面</td>");
        result.append("<td>台號</td>");
        result.append("<td>特尾三</td>");
        result.append("<td>二星</td>");
        result.append("<td>三星</td>");
        result.append("<td>四星</td>");
        result.append("<td>天碰二</td>");
        result.append("<td>天碰三</td>");
        result.append("</tr>");

        //show_data_賠率
        result.append("<tr>");
        result.append("<td>賠率</td>");
        for (int i = 0; i < data_table.getRow(); i++) {
            result.append("<td>").append(data_table.getColume(i, "odds")).append("</td>");
        }
        result.append("</tr>");

        //show_data_退水
        result.append("<tr>");
        result.append("<td>退水</td>");
        for (int i = 0; i < data_table.getRow(); i++) {
            result.append("<td>").append(data_table.getColume(i, "re_cost")).append("</td>");
        }
        result.append("</tr>");

        //show_data_單筆上限
        result.append("<tr>");
        result.append("<td>單筆上限</td>");
        for (int i = 0; i < data_table.getRow(); i++) {
            result.append("<td>").append(data_table.getColume(i, "one_limit")).append("</td>");
        }
        result.append("</tr>");

        //table_end
        result.append("</table>");
        return result.toString();
    }

    //取得會員設定表格
    public String getTable1(String gtype) {
        StringBuilder result = new StringBuilder();
        String sql_select = "select * from game_member_conf where master_id = '" + user_account + "' and gtype = '" + gtype + "' order by type";

        String gtype_name = new GameMethod().getGame_GType(gtype);
        DataTable data_table = DB.getDataTable(sql_select);

        //table_start
        result.append("<table border=\"1\">");

        //show_data_賽事標題
        result.append("<tr>");
        result.append("<th colspan=\"11\">").append(gtype_name).append("</th>");
        result.append("</tr>");

        //show_data_表格標題
        result.append("<tr>");
        result.append("<td>玩法</td>");
        result.append("<td>退水(散單)</td>");
        result.append("<td>退水</td>");
        result.append("<td>賠率</td>");
        result.append("<td>單筆上限</td>");
        result.append("<td>單號上限</td>");
        result.append("</tr>");

        //show_data_內容
        for (int i = 0; i < data_table.getRow(); i++) {
            result.append("<tr>");
            switch (data_table.getColume(i, "type")) {
                case "1":
                    result.append("<td>特別號</td>");
                    break;
                case "2":
                    result.append("<td>正特碼雙面</td>");
                    break;
                case "3":
                    result.append("<td>全車</td>");
                    break;
                case "4":
                    result.append("<td>台號</td>");
                    break;
                case "5":
                    result.append("<td>特尾三</td>");
                    break;
                case "6":
                    result.append("<td>二星</td>");
                    break;
                case "7":
                    result.append("<td>三星</td>");
                    break;
                case "8":
                    result.append("<td>四星</td>");
                    break;
                case "9":
                    result.append("<td>天二</td>");
                    break;
                case "10":
                    result.append("<td>天三</td>");
                    break;
            }
            result.append("<td>").append(data_table.getColume(i, "re_cost_dis")).append("</td>");
            result.append("<td>").append(data_table.getColume(i, "re_cost")).append("</td>");
            result.append("<td>").append(data_table.getColume(i, "odds")).append("</td>");
            result.append("<td>").append(data_table.getColume(i, "one_limit")).append("</td>");
            result.append("<td>").append(data_table.getColume(i, "num_limit")).append("</td>");
            result.append("</tr>");
        }

        //table_end
        result.append("</table>");
        return result.toString();
    }

}
