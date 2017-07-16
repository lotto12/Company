/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import model.DB;
import model.LogTool;
import model.SQLTool;

/**
 *
 * @author JimmyYang 下注進入資料庫使用
 */
public class GameOrderSQL {

//    //測試用
//    public static void main(String[] args) {
//        SQLTool.showData_byArray("select * from warges_main order by id desc limit 1");
//        SQLTool.showData_byName("select * from warges_main order by id desc limit 1");
//    }

    //下注單使用_參數(帳號、賽事ID、遊戲種類gtype、遊戲玩法type、下注金額、下注球號、下注賠率、下注組數、期數備注)
    //回傳布林值
    public boolean setInsertOrder(String account, String game_id, String gtype,
            String type, String stype, String bet_gold, String num,
            String odds, String group_num, String back_gold, String gold, String remarks) {
        String acc_date = new PracticalTool().getDateTime();
        String sql_insert = "INSERT INTO game_warges_main (game_id , gtype , type , stype , "
                + "acc_date , bet_gold , num , odds , group_num , account , back_gold , gold , jump_note , remarks)"
                + "VALUES ('" + game_id + "', '" + gtype + "', '" + type + "', '" + stype + "',"
                + "'" + acc_date + "'," + bet_gold + ",'" + num + "'," + odds + ","
                + group_num + ",'" + account + "', '" + back_gold + "' , '" + gold + "' , '0' , '" + remarks + "');";
        System.out.println(sql_insert); //測試用，產生出SQL指令到資料庫測試
        //LogTool.showLog(sql_insert);
        boolean result = DB.query(sql_insert); //insert DB
        return result;
    }
}
