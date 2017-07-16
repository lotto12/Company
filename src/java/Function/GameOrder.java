/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import model.DB;
import model.DataTable;
import model.LogTool;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author JimmyYang 處理注單使用
 */
public class GameOrder {

    private GameOrderSQL gameOrderSQL;

    public GameOrder() {
        gameOrderSQL = new GameOrderSQL();
    }

    //分析注單種類_參數(JSON)-------------------<<主要>>  STEP1
    public boolean checkFunction(JSONObject root) {
        //{"order_data":"{\"gtype\":801,\"type\":1,\"order_id\":\"S046-521\",\"data\":[{\"num\":1,\"price\":73,\"pay\":100}]}","function":"order","check_key":"33dd658R400Au2ph61qK4b3dvW5F065W642ZV74N0K8KKAU","account":"a01","game_id":"GS04"}
        boolean result = false;
        try {
            //選取功能
            String function = root.getString("function");
            switch (function) {
                case "order":
                    //下注基本資料
                    String account_order = root.getString("account");
                    String game_id_order = root.getString("game_id");
                    String data_order = root.getString("order_data");

                    //找尋對應的塞資料庫方法
                    result = searchGame(game_id_order, account_order, data_order);
                    break;
            }
        } catch (Exception e) {
            result = false;
            LogTool.showLog("checkFunction=" + e.toString());
            System.out.println("checkFunction=" + e.toString());
        }
        return result;
    }

    //尋找符合賽事_並使用對應的方法塞入資料_檢查資料type遊戲玩法---------STEP2
    public boolean searchGame(String game_id, String account, String json) {
        boolean result = false;
        try {
            result = setOrder_Type(game_id, account, json);
        } catch (Exception e) {
            LogTool.showLog("Order Exception->" + e.toString());
            System.out.println("Order Exception->" + e.toString());
            result = false;
        }
        return result;
    }

//以下為個別玩法使用 /////////////////////////////////////////////////////////////////////////////////////////////////
//塞入資料庫 //////////////////////////////////////////////////////////////////////////////////////////////////////// 
//注單使用_參數(賽事ID、帳號、注單Json)
    public boolean setOrder_Type(String game_id, String account, String json) {
        //{"gtype":801,"type":1,"order_id":"S046-521","data":[{"num":1,"price":73,"pay":100}]}
        boolean result = false;
        try {
            JSONObject root = new JSONObject(json);
            String gtype = root.getString("gtype");
            //   String type = root.getString("type");
            String order_id = root.getString("order_id"); //備注編號

            //取單多筆注單資料
            JSONArray order_array = root.getJSONArray("data");
            for (int i = 0; i < order_array.length(); i++) {
                JSONObject order = new JSONObject(order_array.get(i).toString());
                String num = order.getString("num"); //下注號碼
                String gold = order.getString("price"); //下注價格
                String bet_gold = order.getString("pay"); //下注金額
                String odds = order.getString("odds"); //下注賠率
                String group_num = order.getString("group_num"); //下注組數
                String stype = order.getString("stype"); //二三四星分類
                String type = order.getString("type"); //遊戲分類
                String back_gold = String.valueOf(Float.parseFloat(bet_gold) - (Float.parseFloat(gold) * Float.parseFloat(group_num))); //退水

                //寫入資料庫
                result = gameOrderSQL.setInsertOrder(account, game_id, gtype, type, stype, bet_gold, num, odds, group_num, back_gold, gold, order_id);
                if (!result) {
                    //如果寫入不正確跳出
                    return false;
                }
            }
        } catch (Exception e) {
            LogTool.showLog("Order Exception->" + e.toString());
            System.out.println("Order Exception->" + e.toString());
            result = false;
        }
        return result;
    }
}
