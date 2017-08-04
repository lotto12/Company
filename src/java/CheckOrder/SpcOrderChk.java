/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DB;
import model.DataTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Jimmy 分別儲存特殊牌型及一般牌型
 */
public class SpcOrderChk {

    private ArrayList<int[]> data; //所有牌型
    private HashMap<Integer, int[]> sql_spc_data; //資料庫中的特殊牌型

    private ArrayList<int[]> org_data; //一般牌型
    private ArrayList<int[]> spc_data; //特殊牌型
    private ArrayList<Integer> spc_group; //特殊牌型ＳＱＬ編號

    private int gtype;
    private int stype;

    public SpcOrderChk(ArrayList<int[]> data, int gtype, int stype) {
        this.data = data;
        this.gtype = gtype;
        this.stype = stype;
        try {
            setSQL_SPC_DATA();
            rand_set();
        } catch (Exception e) {
            System.out.println("SpcOrderChk_exception:" + e.toString());
        }
    }

    //是否為特殊牌型
    public boolean isSPC() {
        boolean isV = false;
        if (spc_data.size() > 0) {
            isV = true;
        }
        return isV;
    }

    //取得特殊牌型
    public JSONArray getSPC_DATA() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < spc_data.size(); i++) {
            JSONArray obj = new JSONArray();
            try {
                obj.put(0, String.valueOf(spc_group.get(i)));
                obj.put(1, toJSONarray(spc_data.get(i)));
            } catch (JSONException ex) {
                System.out.println("json exception:" + ex.toString());
                return null;
            }
            array.put(obj);
        }
        return array;
    }

    //轉JSONARRAY
    private String toJSONarray(int[] data) {
        JSONArray json = new JSONArray();
        for (int d : data) {
            json.put(d);
        }
        JSONArray json_result = new JSONArray();
        json_result.put(json);
        return json_result.toString();
    }

    //取得一般牌型
    public String getORG_DATA() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < org_data.size(); i++) {
            array.put(org_data.get(i));
        }
        return array.toString();
    }

    //取得資料庫中的特殊牌型
    private void setSQL_SPC_DATA() throws JSONException {
        sql_spc_data = new HashMap<>();

        String sql_select = "SELECT * FROM game_special_group where "
                + " gtype = " + gtype + " and "
                + " stype = " + stype + " and "
                + " status = 1;";

        System.out.println("sql_select:" + sql_select);
        DataTable dt = DB.getDataTable(sql_select);
        if (dt.getRow() > 0) {
            for (int i = 0; i < dt.getRow(); i++) {
                JSONObject obj = new JSONObject(dt.getColume(i, "num"));
                JSONArray array = obj.getJSONArray("result");
                int id = Integer.parseInt(dt.getColume(i, "id"));
                int[] set_data = new int[array.length()];
                for (int j = 0; j < set_data.length; j++) {
                    JSONArray array_1 = array.getJSONArray(j);
                    set_data[j] = array_1.getInt(0);
                }
                sql_spc_data.put(id, set_data);
            }
        }

    }

    //分類
    private void rand_set() {
        spc_group = new ArrayList<>();
        org_data = new ArrayList<>();
        spc_data = new ArrayList<>();
        for (int[] chk_data : data) {
            Chk_data chk = isSpcGroup(chk_data);
            if (chk.isV()) {
                //特殊牌型
                spc_data.add(chk_data);
                spc_group.add(chk.getID());
            } else {
                //一般牌型
                org_data.add(chk_data);
            }
        }
    }

    //---------------------------------------------
    //檢查是否為特殊牌型
    private Chk_data isSpcGroup(int[] data_in) {
        Chk_data chk_data_result = new Chk_data();
        //System.out.println(sql_spc_data.size());
        for (Object key : sql_spc_data.keySet()) {
            int[] data = (int[]) sql_spc_data.get(key);
            int total = data.length; //符合特殊
            int count = 0;

            //data_in -> 使用者的單柱
            for (int sql_data : data) { //ＳＱＬ比對
                for (int chk_data : data_in) { //使用者單號
                    if (chk_data == sql_data) {
                        //System.out.println("chk_data:" + chk_data);
                        count++;
                    }
                }
            }

            //System.out.println("count:" + count + " total:" + total);
            if (total == count) {
                //符合特殊牌型
                chk_data_result.setCHK(true);
                chk_data_result.setID((int) key);
            }

        }
        return chk_data_result;
    }

}

class Chk_data {

    private boolean isV = false;
    private int id;

    public void setID(int id) {
        this.id = id;
    }

    public void setCHK(boolean c) {
        isV = c;
    }

    public boolean isV() {
        return isV;
    }

    public int getID() {
        return id;
    }

}
