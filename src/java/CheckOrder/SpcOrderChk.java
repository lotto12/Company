/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckOrder;

import java.util.ArrayList;
import java.util.HashMap;
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

        //TEST
        System.out.println("org:" + org_data.size() + " spc:" + spc_data.size());
    }

    //取得資料庫中的特殊牌型
    private void setSQL_SPC_DATA() throws JSONException {
        sql_spc_data = new HashMap<>();

        String sql_select = "SELECT * FROM game_special_group where "
                + " gtype = " + gtype + " and "
                + " stype = " + stype + " and "
                + " status = 1;";

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

            for (int chk_data : data_in) { //使用者單號
                for (int sql_data : data) { //ＳＱＬ比對
                    if (chk_data == sql_data) {
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
