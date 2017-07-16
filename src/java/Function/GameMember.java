/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import Class.Member;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.DB;

/**
 *
 * @author JimmyYang 會員用戶資料相關類別_edit 20170427
 */
public class GameMember {

    //會員留言
    public boolean isMsgSend(String user_acount, String phone, String email, String msg) {
        boolean result = false;
        String build_date = new PracticalTool().getDateTime();
        if (phone.length() > 0 && email.length() > 0 && msg.length() > 0) {
            String sql_insert = "INSERT INTO game_comment (acount, phone, email, msg, build_date,status)"
                    + "VALUES ('" + user_acount + "','" + phone + "','" + email + "','" + msg + "','" + build_date + "','0');";
            result = DB.query(sql_insert);
        }
        return result;
    }

    //判斷會員帳號密碼是否正確_紀錄上線時間
    public boolean isMemberOK(String user_acount, String user_pwd) {
        boolean result = false;
        String sql_selct_str = "select pwd from game_member where acount = '" + user_acount + "'";
        String[][] sql_selct = DB.getDataArray(sql_selct_str);
        if (sql_selct.length > 0) {
            if (sql_selct[0][0].equals(user_pwd)) {
                //紀錄上線時間
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                        Date date = new Date();
                        String strDate = sdFormat.format(date);
                        String sql_update = "update game_member set on_time ='" + strDate + "' where acount = '" + user_acount + "'";
                        DB.query(sql_update);
                    }
                }).start();
                result = true;
            }
        }
        return result;
    }

    //會員密碼驗證更新
    public String updatePwd(String acount, String old_pwd, String new_pwd, String new_pwd_chk) {
        String result = "密碼更新失敗";
        if (new_pwd.equals(new_pwd_chk)) {
            String sql_selct_str = "select pwd from game_member where acount = '" + acount + "'";
            String[][] sql_selct = DB.getDataArray(sql_selct_str);
            if (sql_selct[0][0].equals(old_pwd)) {
                String sql_update = "update game_member set pwd ='" + new_pwd + "' where acount = '" + acount + "'";
                DB.query(sql_update);
                result = "密碼更新成功";
            } else {
                result = "舊密碼錯誤";
            }
        } else {
            result = "請確認新密碼輸入正確";
        }
        return result;
    }

    //取得會員資料
    public Member getMemberData(String account) {
        Member result_member = new Member();
        String sql_select = "select * from game_member where acount = '" + account + "'";
        String[][] select_data = DB.getDataArray(sql_select);
        if (sql_select.length() > 0) {
            if (result_member.setMemberData(select_data[0])) {
                return result_member;
            }
        }
        return null;
    }
}
