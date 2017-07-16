/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

/**
 *
 * @author JimmyYang 會員定義檔案_全部會員資料 20170506
 */
public class Member {

    //會員定義檔
    private String system_id = null;
    private String top_json = null;
    private String account = null;
    private String pwd = null;
    private String member = null;
    private String name = null;
    private String status = null;
    private String money = null;
    private String credit = null;
    private String date = null;
    private String on_time = null;
    private String level = null;
    private String open = null;
    private String money_temp = null;
    private String credit_temp = null;

    //設定資料
    public boolean setMemberData(String[] data) {
        boolean isV = false;
        if (data.length == 15) {
            system_id = data[0];
            top_json = data[1];
            account = data[2];
            pwd = data[3];
            member = data[4];
            name = data[5];
            status = data[6];
            money = data[7];
            credit = data[8];
            date = data[9];
            on_time = data[10];
            level = data[11];
            open = data[12];
            money_temp = data[13];
            credit_temp = data[14];
            isV = true;
        }
        return isV;
    }

    //取得資料
    public String getSystem_id() {
        return system_id;
    }

    public String getTop_Json() {
        return top_json;
    }

    public String getAccount() {
        return account;
    }

    public String getPwd() {
        return pwd;
    }

    public String getMember() {
        return member;
    }

    public String getStatus() {
        return status;
    }

    public String getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public String getCredit() {
        return credit;
    }

    public String getDate() {
        return date;
    }

    public String getOntime() {
        return on_time;
    }

    public String getLevel() {
        return level;
    }

    public String getOpen() {
        return open;
    }

    public String getMoney_temp() {
        return money_temp;
    }

    public String getCredit_temp() {
        return credit_temp;
    }
}
