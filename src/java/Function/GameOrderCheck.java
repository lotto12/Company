/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import java.util.ArrayList;
import model.DB;
import model.DataTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author JimmyYang 檢查下注流程
 */
public class GameOrderCheck {

    //需要的變數
    private String account;
    private String gtype;
    private String type;
    private String order_id;
    private String json;

    //散單、包牌、配比
    private String type_sub = "3"; //預設散單

    //取得相關賠率金額設定後的JSON
    private JSONObject result_obj;

//    public static void main(String[] args) {
//        String account = "aa1"; //a01
//        String json = "{\"gtype\":802,\"order_id\":\"S046-538\",\"data\":[{\"num\":{\"result\":[[1,2,3],[4,5,6,7],[4,5,6,7,8,9,0]]},\"price\":63.5,\"pay\":100,\"odds\":570,\"group_num\":1,\"stype\":2,\"type\":7}]}";
//        GameOrderCheck check = new GameOrderCheck(account, json);
//
//        //測試行數:91,196
//        
//        //是否可下單
//        boolean isV = check.checkOrder();
//        System.out.println("檢查結果:" + isV);
//
//        //匯出結果JSON
//        JSONObject o = check.getJSON_Result();
//
//        System.out.println("--------------");
//        System.out.println("原始JSON:");
//        System.out.println(json);
//        System.out.println("修正JSON:");
//        System.out.println(o.toString());
//        
//        //單邊、封牌 未完! 20170711
//    }

    //建構方法
    public GameOrderCheck(String account, String json) {
        try {
            //定義變數
            this.account = account;
            this.json = json;

            //剖析JSON
            JSONObject root = new JSONObject(json);
            JSONArray data = root.getJSONArray("data");
            gtype = root.getString("gtype");//遊戲種類

            JSONObject data_obj = new JSONObject(data.get(0).toString());
            type = data_obj.getString("type"); //遊戲玩法
            order_id = root.getString("order_id"); //備注編號

            //建立新的JSON
            result_obj = new JSONObject();
            result_obj.put("gtype", gtype);
            result_obj.put("order_id", order_id);

        } catch (Exception e) {
            System.out.println("GameOrderCheck Exception:" + e.toString());
        }
    }

    //STEP1. 下注確認
    public boolean checkOrder() {
        JSONArray array = new JSONArray();
        int total_pay = 0;
        try {
            JSONObject root = new JSONObject(json);

            //取單多筆注單資料
            JSONArray order_array = root.getJSONArray("data");
            for (int i = 0; i < order_array.length(); i++) {

                //各筆
                JSONObject order = new JSONObject(order_array.get(i).toString());
                String num = order.getString("num"); //下注號碼
                String gold = order.getString("price"); //下注價格
                String bet_gold = order.getString("pay"); //下注金額
                String odds = order.getString("odds"); //下注賠率
                String group_num = order.getString("group_num"); //下注組數
                String stype = order.getString("stype"); //二三四星分類
                String type = order.getString("type"); //玩法分類
                String back_gold = String.valueOf(Float.parseFloat(bet_gold) - (Float.parseFloat(gold) * Float.parseFloat(group_num))); //退水

                //System.out.println("91行測試中...");
                //確認有無碰觸到固定賠，特殊牌型
                String odds_fix = getFixed_odds(Integer.parseInt(type), Integer.parseInt(stype), num, odds); //固定賠率
                String odds_spc = getSpecial_odds(Integer.parseInt(type), Integer.parseInt(stype), num, odds); //特殊牌型
//                String odds_fix = getFixed_odds(6, Integer.parseInt(stype), num, odds); 
//                String odds_spc = getSpecial_odds(6, Integer.parseInt(stype), num, odds); 

                //判斷固定賠率
                String result_odds = null;
                if (!odds_fix.equals(odds)) {
                    //固定賠 step1
                    result_odds = odds_fix;
                } else if (!odds_spc.equals(odds)) {
                    //特別賠率 step2
                    result_odds = odds_spc;
                } else {
                    //原始賠率 step3
                    result_odds = odds;
                }

                //判斷組數、散單
                String offer_back_gold = getConf_offer(group_num, back_gold);

                JSONObject obj = new JSONObject();
                obj.put("num", num);
                obj.put("price", gold);
                obj.put("pay", bet_gold);
                obj.put("odds", result_odds);
                obj.put("group_num", group_num);
                obj.put("stype", stype);
                obj.put("type", type);
                obj.put("back_gold", offer_back_gold);

                array.put(obj);

                //檢查資訊
                total_pay += Integer.parseInt(bet_gold); //注單總金額

            }

            //第一關(檢查額度)
            boolean Step1 = isMoneyEnough(account, total_pay);
//            System.out.println("137行測試中...");
//            boolean Step1 = isMoneyEnough("a01", total_pay);

            //第二關(檢查單筆、單號、單邊、封牌)
            boolean Step2 = isOrder_Check(total_pay);

            result_obj.put("data", array);

            if (Step1 && Step2) {
                return true;
            }

        } catch (JSONException | NumberFormatException e) {
            System.out.println("Order Exception->" + e.toString());
        }

        return false;
    }

    //STEP2. 取得經過固定賠、特殊牌型等賠率設定的JSON
    public JSONObject getJSON_Result() {
        return result_obj;
    }

    //    -------------以下為檢查使用-------------
    //<editor-fold defaultstate="collapsed" desc="檢查使用">
    //判斷有無達到上限門檻(單筆、單號、單邊、封牌金額)
    private boolean isOrder_Check(int pay) {
        String sql_select = "SELECT * FROM game_agents_conf where gtype = " + gtype + " and type = " + type + " "
                + "and type_sub=" + type_sub + " "
                + "and master_id "
                + "in (SELECT id FROM game_agents where acount = '" + account + "')";
        //System.out.println("sql_select:" + sql_select);
        DataTable dt = DB.getDataTable(sql_select);
        if (dt.getRow() > 0) {
            int one_limit = Integer.parseInt(dt.getColume(0, "one_limit")); //單筆上限
            int num_limit = Integer.parseInt(dt.getColume(0, "num_limit")); //單號上限
            int edge_limit = Integer.parseInt(dt.getColume(0, "edge_limit")); //單邊上限

            ArrayList<Integer> array = new ArrayList<>();
            array.add(1); //特別號
            array.add(2); //正特碼
            array.add(3); //全車
            array.add(4); //台號
            array.add(5); //特尾三

            //System.out.println("type:" + type);
            //比對資料
            if (array.contains(Integer.parseInt(type))) {
                if (pay > one_limit) {
                    return true;
                } else if (pay > num_limit) {
                    return true;
                }
            } else {
                //不需要比對的遊戲種類
                return true;
            }

        }
        //資料庫無資料
        return false;
    }

    //取得散單、各組數 退水
    private String getConf_offer(String group_num, String oring_back) {
        String sql_select = "SELECT * FROM game_conf_offer where gtype =" + gtype + " and type=" + type + " order by offer asc";
        DataTable dt = DB.getDataTable(sql_select);

        int member_order_group = Integer.parseInt(group_num); //會員下注組數
        String result_back = oring_back;

        if (dt.getRow() > 0) {
            for (int i = 0; i < dt.getRow(); i++) {
                int conf_offer = Integer.parseInt(dt.getColume(i, "offer"));
                int back_gold = Integer.parseInt(dt.getColume(i, "back_gold"));
                if (member_order_group > conf_offer) {
                    type_sub = "2"; //配比
                    result_back = String.valueOf(back_gold);
                }
            }

        }

        return result_back;
    }

    //取得固定賠率
    private String getFixed_odds(int type, int stype, String num, String orign_odds) throws JSONException {
        int down; //上限
        int up; //下限
        String odds; //賠率
        ArrayList<int[]> fix_Array = new ArrayList<>(); //固定賠球號

        String star_str = getStar_Name(type); //取得二三四星

        //抓取固定賠率
        String sql_select = "SELECT * FROM game_fixed_odds"
                + " where gtype = " + gtype + " and type = " + type
                + " and stype ='" + stype + "' "
                + " and account = '" + account + "' and status = 1";
        sql_select += " and " + star_str + "_open=1";
        DataTable dt = DB.getDataTable(sql_select);

        if (dt.getRow() > 0) {

            down = Integer.parseInt(dt.getColume(0, star_str + "_down")); //下限
            up = Integer.parseInt(dt.getColume(0, star_str + "_up")); //上限

            String fix_odds_num = dt.getColume(0, "num"); //固定賠資料
            JSONObject fix_odds = new JSONObject(fix_odds_num);

            JSONArray fix_array = fix_odds.getJSONArray("result");
            for (int i = 0; i < fix_array.length(); i++) {
                JSONArray fix_data = fix_array.getJSONArray(i);
                int[] data = new int[fix_data.length()];
                for (int j = 0; j < fix_data.length(); j++) {
                    //單號資料
                    data[j] = fix_data.getInt(j);
                }
                fix_Array.add(data);
            }
        }

        //分析球號
        JSONObject order = new JSONObject(num);
        JSONArray order_array = order.getJSONArray("result");
        //比對資料
        int total = 0;
        int fix_total_num = fix_Array.size();
        for (int[] fix_Array_data : fix_Array) {
            int array_total = fix_Array_data.length;
            for (int i = 0; i < order_array.length(); i++) {
                //單柱資料
                JSONArray order_data = order_array.getJSONArray(i);
                int row_total = 0;
                for (int j = 0; j < order_data.length(); j++) {
                    //單號
                    for (int data : fix_Array_data) {
                        if (data == order_data.getInt(j)) {
                            row_total++;
                        }
                    }
                }
                if (row_total == array_total) {
                    total++;
                }
            }
        }

        //比對完成
        if (total == fix_total_num && dt.getRow() > 0) {
            odds = dt.getColume(0, star_str + "_odds");
        } else {
            odds = orign_odds;
        }
        return odds;
    }

    //取得固定賠率
    private String getSpecial_odds(int type, int stype, String num, String orign_odds) throws JSONException {
        int down; //上限
        int up; //下限
        String odds; //賠率
        ArrayList<int[]> fix_Array = new ArrayList<>(); //固定賠球號

        String star_str = getStar_Name(type); //取得二三四星

        //抓取特殊牌型賠率
//        System.out.println("196行測試中...");
//        String gtype = "801";

        String sql_select = "SELECT * FROM game_special_group"
                + " where gtype = " + gtype + " and stype ='" + stype + "' "
                + " and account = '" + account + "' and status = 1";
        sql_select += " and " + star_str + "_open=1";
        DataTable dt = DB.getDataTable(sql_select);

        if (dt.getRow() > 0) {
            String spc_odds_num = dt.getColume(0, "num"); //固定賠資料
            JSONObject spc_odds = new JSONObject(spc_odds_num);

            JSONArray spc_array = spc_odds.getJSONArray("result");
            for (int i = 0; i < spc_array.length(); i++) {
                JSONArray fix_data = spc_array.getJSONArray(i);
                int[] data = new int[fix_data.length()];
                for (int j = 0; j < fix_data.length(); j++) {
                    //單號資料
                    data[j] = fix_data.getInt(j);
                }
                fix_Array.add(data);
            }
        }

        //分析球號
        JSONObject order = new JSONObject(num);
        JSONArray order_array = order.getJSONArray("result");

        //比對資料
        int total = 0;
        int fix_total_num = fix_Array.size();

        for (int[] fix_Array_data : fix_Array) {
            int array_total = fix_Array_data.length;
            for (int i = 0; i < order_array.length(); i++) {
                //單柱資料
                JSONArray order_data = order_array.getJSONArray(i);
                int row_total = 0;
                for (int j = 0; j < order_data.length(); j++) {
                    //單號
                    for (int data : fix_Array_data) {
                        if (data == order_data.getInt(j)) {
                            row_total++;
                        }
                    }
                }
                if (row_total == array_total) {
                    total++;
                }
            }
        }

        //比對完成
        if (total == fix_total_num && dt.getRow() > 0) {
            odds = dt.getColume(0, star_str + "_odds");
        } else {
            odds = orign_odds;
        }
        return odds;
    }

    //STEP1 檢查額度是否夠下單
    private boolean isMoneyEnough(String account, int total_pay) {
        String sql_select = "SELECT id,money_temp FROM game_member where acount = '" + account + "'";
        DataTable dt = DB.getDataTable(sql_select);

        //System.out.println("sql_select:" + sql_select);
        int member_money; //會員剩餘額度
        if (dt.getRow() > 0) {
            member_money = Integer.parseInt(dt.getColume(0, "money_temp"));
            if (member_money > total_pay) {
                return true;
            }
        }
        return false;
    }

    //回傳2、3、4星欄位名稱
    private String getStar_Name(int type) {
        String data = null;
        switch (type) {
            case 6:
                //二星
                data = "two";
                break;
            case 7:
                //三星
                data = "three";
                break;
            case 8:
                //四星
                data = "four";
                break;
        }
        return data;
    }
//</editor-fold>
}
