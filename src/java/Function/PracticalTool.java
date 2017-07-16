/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author JimmyYang 常用方法類別
 */
public class PracticalTool {

    //取得時間yyyy/MM/dd hh:mm:ss
    public String getDateTime() {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date = new Date();
        String strDate = sdFormat.format(date);
        return strDate;
    }
}
