/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JsonTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author JimmyYang 產出各Json
 */
public class JsonCoding {

    //測試用請按shift + F6 可以觀看執行結果
    public static void main(String[] args) {
        JsonCoding json_coding = new JsonCoding();

        //呼叫方法使用
        System.out.println(json_coding.getJson_801_1());

        System.out.println("------立注單");
        ArrayList<int[]> data = new ArrayList<>();
        data.add(new int[]{1,2});
        data.add(new int[]{1,2,3,4,5,6});
    //    data.add(new int[]{5,6,7,8});
    //    data.add(new int[]{9,10,11,12});
    //    data.add(new int[]{13,14,15});
        System.out.println(json_coding.wagers_num(data));

        System.out.println("------int二微陣列json編碼通用");
        String[][] f = {{"1", "76", "57000"}, {"2", "76", "57000"}, {"3", "76", "57000"}, {"4", "76", "57000"}, {"5", "76", "57000"}};
        String g = "cash_temp";
        System.out.println(json_coding.ary2_string_json(g, f));

        System.out.println("------json編碼 基本設定");
        String a = "cash_basic";
        int b = 39;
        String[] c = {"51","8000"};
        System.out.println(json_coding.getJson_data(a, b, c));

    }

    //立柱碰注單
    public String wagers_num(ArrayList<int[]> data) {
        JSONObject obj_result = new JSONObject();
        JSONArray list = new JSONArray();
        JSONArray list_in;
        String result_json = null;
        try {
            for (int[] d : data) {
                list_in = new JSONArray();
                for (int i = 0; i < d.length; i++) {
                    list_in.put(d[i]);
                }
                list.put(list_in);
            }
            obj_result.put("result", list);
            result_json = obj_result.toString();
        } catch (Exception ex) {
            result_json = "error";
        }
        return result_json;
    }

    //int二微陣列json編碼通用 
    //玩法基本設定 本金基本 本金浮動 實.虛貨本金浮動
    //注單主要 各階層小記
    //會員 上層階層
    public String ary2_int_json(String title, int data[][]) {
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();
        String result_json = null;
        try {
            obj.put(title, data);
            result_json = obj.toString();
        } catch (Exception ex) {
            result_json = "error";
        }
        return result_json;
    }

    //string二微陣列json編碼通用 
    //玩法基本設定 包牌
    public String ary2_string_json(String title, String data[][]) {
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();
        String result_json = null;
        try {
            obj.put(title, data);
            result_json = obj.toString();
        } catch (Exception ex) {
            result_json = "error";
        }
        return result_json;
    }

    //產出[六合彩->特瑪->基本遊戲資料(號碼、本金、賠率)]
    //801 = 六合彩
    //1 = 特別號
    public String getJson_801_1() {
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();
        String result_json = null;
        try {
            for (int i = 0; i < 49; i++) {
                Map m = new HashMap();
                m.put("Num", i + 1);
                m.put("Pay", 73.8);
                m.put("Odds", 0.36);
                list.put(m);
            }
            obj.put("GameType", 8011);
            obj.put("Result", list);
            result_json = obj.toString();
        } catch (Exception ex) {
            result_json = "error";
        }
        return result_json;
    }

    //json產生器 
    public String getJson_data(String title, int ball_num, String data[]) {
        JSONObject obj = new JSONObject();
        //JSONArray list = new JSONArray();
        String result_json = null;
        try {
            String[][] get_data = new String[ball_num][data.length];
            for (int i = 0; i < ball_num; i++) {
                for (int j = 0; j < data.length; j++) {
                    get_data[i][j] = data[j];
                }
            }
            obj.put(title, get_data);
            result_json = obj.toString();
        } catch (Exception ex) {
            System.out.println(ex.toString());
            result_json = "error";
        }
        return result_json;
    }

}
