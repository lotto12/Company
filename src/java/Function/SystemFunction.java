/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import model.DB;

/**
 *
 * @author JimmyYang 系統功能相關
 */
public class SystemFunction {

    //取得最新消息_跑馬燈
    public String getNews() {
        String result = null;
        String sql_select = "SELECT msg FROM system_msg order by id desc limit 1";
        if (sql_select.length() > 0) {
            result = DB.getDataArray(sql_select)[0][0];
        }
        return result;
    }
}
