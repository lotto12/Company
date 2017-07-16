/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lottery;

import Lotto.Lotto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import model.DB;
import model.DataTable;
import model.LogTool;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author JimmyYang 開獎
 */
public class Lottery {

    //六合彩_開獎號碼
    private String[] num_801;
    //大樂透_開獎號碼
    private String[] num_802;
    //539_開獎號碼
    private String[] num_803;

    //期數
    private String game_id;

    //TEST(測試請在遊戲畫面按'總帳')
    public static void main(String[] args) {
        String[] num_801 = {"07", "02", "03", "04", "05", "06", "01"};
        String[] num_802 = {"07", "02", "03", "04", "05", "06", "01"};
        String[] num_803 = {"01", "02", "03", "04", "05"};
        String game_id = "GS04";
        Lottery lottery = new Lottery(num_801, num_802, num_803, game_id);

        //呼叫所有方法
        System.out.println("START_" + new Date().toString());
        lottery.update_result();
        System.out.println("END_" + new Date().toString());
    }

    //建構函數
    public Lottery(String[] num_801, String[] num_802, String[] num_803, String game_id) {
        this.num_801 = num_801;
        this.num_802 = num_802;
        this.num_803 = num_803;
        this.game_id = game_id;
    }

    //呼叫所有方法
    public void update_result() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                setGame_Type1();//特別號
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                setGame_Type3();//全車
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                setGame_Type4();//台號
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                setGame_Type5();//特尾三
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                setGame_Type678_s1();//二三四星_Stype1單碰()
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                setGame_Type678_s2();//二三四星_Stype2連碰()
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                setGame_Type678_s3();//二三四星_Stype3柱碰()
            }
        }).start();
    }

    public void update_result1() {
        setGame_Type910();//天碰
    }

    // <editor-fold defaultstate="collapsed" desc="所有玩法的結算方法">
    //二三四星_Stype1單碰()
    private boolean setGame_Type678_s1() {
        try {
            String[] gtype_array = {"801", "802", "803"};
            String[] type_array = {"6", "7", "8"};
            String stype = "1";
            DataTable data_table;
            for (String gtype : gtype_array) {
                //依照gtype遊戲玩法
                for (String type : type_array) {
                    //依照二、三、四星
                    String sql_select = "SELECT * FROM warges_main where"
                            + " type= '" + type + "'"
                            + " and gtype = '" + gtype + "'"
                            + " and stype = '" + stype + "'"
                            + " and game_id = '" + game_id + "'";
                    data_table = DB.getDataTable(sql_select);
                    //最低中獎球數
                    int num_limit = 0;
                    switch (type) {
                        case "6":
                            num_limit = 2;
                            break;
                        case "7":
                            num_limit = 3;
                            break;
                        case "8":
                            num_limit = 4;
                            break;
                    }
                    //開獎號碼
                    String[] num_ans = null;
                    switch (gtype) {
                        case "801":
                            num_ans = num_801;
                            break;
                        case "802":
                            num_ans = num_802;
                            break;
                        case "803":
                            num_ans = num_803;
                            break;
                    }
                    //結算
                    for (int i = 0; i < data_table.getRow(); i++) {
                        String member_ans = data_table.getColume(i, "num");
                        JSONObject obj = new JSONObject(member_ans);
                        JSONArray obj_array = obj.getJSONArray("result");
                        obj_array = obj_array.getJSONArray(0);
                        int count = 0; //中幾個球
                        for (int j = 0; j < obj_array.length(); j++) {
                            //單碰注單
                            String ball = obj_array.getString(j);
                            //補零 -> "01"
                            if (Integer.parseInt(ball) < 10) {
                                ball = "0" + ball;
                            }
                            //有無特別號
                            int spe_num = 0;
                            if (gtype.equals("801") || gtype.equals("802")) {
                                spe_num = 1;
                            }
                            for (int k = 0; k < num_ans.length - spe_num; k++) { //不包含特別號
                                if (num_ans[k].equals(ball)) {
                                    count++;
                                }
                            }
                        }
                        //是否中獎
                        boolean isWin = false;
                        //結算中獎組數
                        if (count == num_limit) {
                            isWin = true;
                        }
                        //結算注單
                        String system_id = data_table.getColume(i, "id"); //系統流水號
                        int odds = Integer.parseInt(data_table.getColume(i, "odds")); //賠率
                        int bet_gold = Integer.parseInt(data_table.getColume(i, "bet_gold")); //賠率
                        int group_num = Integer.parseInt(data_table.getColume(i, "group_num")); //組數
                        String result_data = String.valueOf((group_num * odds * bet_gold)); //組數*倍率*下注金額
                        String sql_update = null;
                        if (isWin) {
                            sql_update = "update warges_main set earn_gold = '" + result_data + "' ,result='W' where id = " + system_id;
                        } else {
                            sql_update = "update warges_main set earn_gold = '" + result_data + "' ,result='L' where id = " + system_id;
                        }
                        //更新資料表
                        DB.query(sql_update);
                    }
                }
            }
        } catch (Exception ex) {
            LogTool.showLog("單碰開獎錯誤:" + ex.toString());
            return false;
        }
        return true;
    }

    //二三四星_Stype2連碰()
    private boolean setGame_Type678_s2() {
        try {
            String[] gtype_array = {"801", "802", "803"};
            String[] type_array = {"6", "7", "8"};
            String stype = "2";
            DataTable data_table;
            for (String gtype : gtype_array) {
                //依照gtype遊戲玩法
                for (String type : type_array) {
                    //依照二、三、四星
                    String sql_select = "SELECT * FROM warges_main where"
                            + " type= '" + type + "'"
                            + " and gtype = '" + gtype + "'"
                            + " and stype = '" + stype + "'"
                            + " and game_id = '" + game_id + "'";
                    data_table = DB.getDataTable(sql_select);
                    //最低中獎球數
                    int num_limit = 0;
                    switch (type) {
                        case "6":
                            num_limit = 2;
                            break;
                        case "7":
                            num_limit = 3;
                            break;
                        case "8":
                            num_limit = 4;
                            break;
                    }
                    //開獎號碼
                    String[] num_ans = null;
                    switch (gtype) {
                        case "801":
                            num_ans = num_801;
                            break;
                        case "802":
                            num_ans = num_802;
                            break;
                        case "803":
                            num_ans = num_803;
                            break;
                    }
                    //結算
                    for (int i = 0; i < data_table.getRow(); i++) {
                        String member_ans = data_table.getColume(i, "num");
                        JSONObject obj = new JSONObject(member_ans);
                        JSONArray obj_array = obj.getJSONArray("result");
                        obj_array = obj_array.getJSONArray(0);
                        int count = 0; //中幾個球
                        for (int j = 0; j < obj_array.length(); j++) {
                            //連碰注單
                            String ball = obj_array.getString(j);
                            //補零 -> "01"
                            if (Integer.parseInt(ball) < 10) {
                                ball = "0" + ball;
                            }
                            //有無特別號
                            int spe_num = 0;
                            if (gtype.equals("801") || gtype.equals("802")) {
                                spe_num = 1;
                            }
                            for (int k = 0; k < num_ans.length - spe_num; k++) { //不包含特別號
                                if (num_ans[k].equals(ball)) {
                                    count++;
                                }
                            }
                        }
                        //是否中獎
                        boolean isWin = false;
                        //結算中獎組數
                        int result_count = 0;
                        if (count > num_limit) {
                            isWin = true;
                            //中獎計算
                            switch (type) {
                                case "6": //二星連碰
                                    result_count = (count * (count - 1)) / 2;
                                    break;
                                case "7": //三星連碰
                                    result_count = (count * (count - 1) * (count - 2)) / 6;
                                    break;
                                case "8": //四星連碰
                                    result_count = (count * (count - 1) * (count - 2) * (count - 3)) / 24;
                                    break;
                            }
                        }
                        //結算注單
                        String system_id = data_table.getColume(i, "id"); //系統流水號
                        int odds = Integer.parseInt(data_table.getColume(i, "odds")); //賠率
                        int bet_gold = Integer.parseInt(data_table.getColume(i, "bet_gold")); //賠率
                        String result_data = String.valueOf((result_count * odds * bet_gold)); //組數*倍率*下注金額
                        String sql_update = null;
                        if (isWin) {
                            sql_update = "update warges_main set earn_gold = '" + result_data + "' ,result='W' where id = " + system_id;
                        } else {
                            sql_update = "update warges_main set earn_gold = '" + result_data + "' ,result='L' where id = " + system_id;
                        }
                        //更新資料表
                        DB.query(sql_update);
                    }
                }
            }
        } catch (Exception ex) {
            LogTool.showLog("連碰開獎錯誤:" + ex.toString());
            return false;
        }
        return true;
    }

    //二三四星_Stype3柱碰()
    private boolean setGame_Type678_s3() {
        try {
            Lotto lotto = new Lotto();
            String[] gtype_array = {"801", "802", "803"};
            String[] type_array = {"6", "7", "8"};
            String stype = "3";
            DataTable data_table;
            for (String gtype : gtype_array) {
                //依照gtype遊戲玩法
                for (String type : type_array) {
                    //依照二、三、四星
                    String sql_select = "SELECT * FROM warges_main where"
                            + " type= '" + type + "'"
                            + " and gtype = '" + gtype + "'"
                            + " and stype = '" + stype + "'"
                            + " and game_id = '" + game_id + "'";
                    data_table = DB.getDataTable(sql_select);
                    //最低中獎柱數
                    int num_limit = 0;
                    switch (type) {
                        case "6":
                            num_limit = 2;
                            break;
                        case "7":
                            num_limit = 3;
                            break;
                        case "8":
                            num_limit = 4;
                            break;
                    }
                    //開獎號碼
                    String[] num_ans = null;
                    switch (gtype) {
                        case "801":
                            num_ans = new String[6];
                            for (int i = 0; i < num_801.length - 1; i++) {
                                num_ans[i] = num_801[i];
                            }
                            break;
                        case "802":
                            num_ans = new String[6];
                            for (int i = 0; i < num_802.length - 1; i++) {
                                num_ans[i] = num_802[i];
                            }
                            break;
                        case "803":
                            num_ans = num_803;
                            break;
                    }
                    //結算
                    for (int i = 0; i < data_table.getRow(); i++) {
                        String order_data = data_table.getColume(i, "num");
                        String system_id = data_table.getColume(i, "id");
                        JSONObject obj = new JSONObject(order_data);
                        JSONArray obj_array = obj.getJSONArray("result");
                        //結果
                        ArrayList<String> result_order = new ArrayList<>();
                        //單子
                        for (int j = 0; j < obj_array.length(); j++) {
                            //每一柱
                            JSONArray obj_d1 = obj_array.getJSONArray(j);
                            int count = 0; //一柱中獎個數
                            for (int k = 0; k < obj_d1.length(); k++) {
                                //每一柱裡面的號碼
                                String num = obj_d1.getString(k);
                                //補零 -> "01"
                                if (Integer.parseInt(num) < 10) {
                                    num = "0" + num;
                                }
                                //兌獎
                                for (String ans : num_ans) {
                                    if (ans.equals(num)) {
                                        count++;
                                    }
                                }
                            }
                            if (count > 0) {
                                result_order.add(String.valueOf(count));
                            }
                        }
                        //結算
                        int group_num = 0;
                        //是否中獎
                        boolean isWin = false;
                        if (result_order.size() >= num_limit) {
                            isWin = true;
                            String[] result_array = (String[]) result_order.toArray(new String[0]);
                            group_num = Integer.parseInt(lotto.getGameNum(result_array, num_limit));
                        }
                        //結算注單
                        int odds = Integer.parseInt(data_table.getColume(i, "odds")); //賠率
                        int bet_gold = Integer.parseInt(data_table.getColume(i, "bet_gold")); //賠率
                        String result_data = String.valueOf((group_num * odds * bet_gold)); //組數*倍率*下注金額
                        String sql_update = null;
                        if (isWin) {
                            sql_update = "update warges_main set earn_gold = '" + result_data + "' ,result='W' where id = " + system_id;
                        } else {
                            sql_update = "update warges_main set earn_gold = '" + result_data + "' ,result='L' where id = " + system_id;
                        }
                        //更新資料表
                        DB.query(sql_update);
                    }
                }
            }
        } catch (Exception ex) {
            LogTool.showLog("柱碰開獎錯誤:" + ex.toString());
            return false;
        }
        return true;
    }

    //全車
    private boolean setGame_Type3() {
        try {
            String[] gtype_array = {"801", "802", "803"};
            DataTable data_table;
            for (String gtype : gtype_array) {
                String sql_select = "SELECT * FROM warges_main where"
                        + " type= '3'"
                        + " and gtype = '" + gtype + "'"
                        + " and game_id = '" + game_id + "'";
                data_table = DB.getDataTable(sql_select);
                String[] num_ans_array = null; //開獎號碼
                switch (gtype) {
                    case "801":
                        num_ans_array = num_801;
                        break;
                    case "802":
                        num_ans_array = num_802;
                        break;
                    case "803":
                        num_ans_array = num_803;
                        break;
                }
                //開始結算
                for (int i = 0; i < data_table.getRow(); i++) {
                    String member_ans = data_table.getColume(i, "num");
                    String system_id = data_table.getColume(i, "id");
                    String sql_update = null;
                    boolean isWin = false; //有無中獎
                    //補零 -> "01"
                    if (Integer.parseInt(member_ans) < 10) {
                        member_ans = "0" + member_ans;
                    }
                    //有無特別號
                    int spe_num = 0;
                    if (gtype.equals("801") || gtype.equals("802")) {
                        spe_num = 1;
                    }
                    for (int k = 0; k < num_ans_array.length - spe_num; k++) { //不包含特別號
                        if (num_ans_array[k].equals(member_ans)) {
                            //中獎
                            isWin = true;
                        }
                    }
                    if (isWin) {
                        //中獎
                        int group_num = Integer.parseInt(data_table.getColume(i, "group_num")); //組數
                        int bet_gold = Integer.parseInt(data_table.getColume(i, "bet_gold")); //下注金額
                        double odds = Double.parseDouble(data_table.getColume(i, "odds")); //賠率
                        String result = String.valueOf((group_num * odds * bet_gold)); //組數 * 賠率 * 下注金額
                        sql_update = "update warges_main set earn_gold = '" + result + "', result='W' where id = " + system_id;
                    } else {
                        //沒中
                        sql_update = "update warges_main set earn_gold = '0', result='L' where id = " + system_id;
                    }
                    //更新資料表
                    DB.query(sql_update);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            LogTool.showLog("全車開獎錯誤:" + ex.toString());
            return false;
        }
        return true;
    }

    //特別號
    private boolean setGame_Type1() {
        try {
            String[] gtype_array = {"801", "802"};
            DataTable data_table;
            for (String gtype : gtype_array) {
                String sql_select = "SELECT * FROM warges_main where"
                        + " type= '1'"
                        + " and gtype = '" + gtype + "'"
                        + " and game_id = '" + game_id + "'";
                data_table = DB.getDataTable(sql_select);
                String num_ans = null; //開獎號碼
                switch (gtype) {
                    case "801":
                        num_ans = num_801[6];
                        break;
                    case "802":
                        num_ans = num_802[6];
                        break;
                }
                //開始結算
                for (int i = 0; i < data_table.getRow(); i++) {
                    String member_ans = data_table.getColume(i, "num");
                    String system_id = data_table.getColume(i, "id");
                    String sql_update = null;
                    //補零 -> "01"
                    if (Integer.parseInt(member_ans) < 10) {
                        member_ans = "0" + member_ans;
                    }
                    if (num_ans.equals(member_ans)) {
                        //中獎
                        int group_num = Integer.parseInt(data_table.getColume(i, "group_num")); //組數
                        int odds = Integer.parseInt(data_table.getColume(i, "odds")); //賠率
                        int bet_gold = Integer.parseInt(data_table.getColume(i, "bet_gold")); //下注金額
                        String result = String.valueOf((group_num * odds * bet_gold)); //組數 * 賠率 * 下注金額
                        sql_update = "update warges_main set earn_gold = '" + result + "' ,result = 'W' where id = " + system_id;
                    } else {
                        //沒中
                        sql_update = "update warges_main set earn_gold = '0' ,result = 'L' where id = " + system_id;
                    }
                    DB.query(sql_update);
                }
            }
        } catch (Exception ex) {
            LogTool.showLog("特別號開獎錯誤:" + ex.toString());
            return false;
        }
        return true;
    }

    //台號
    private boolean setGame_Type4() {
        try {
            String[] gtype_array = {"801", "802", "803"};
            DataTable data_table;
            for (String gtype : gtype_array) {
                String sql_select = "SELECT * FROM warges_main where"
                        + " type= '4'"
                        + " and gtype = '" + gtype + "'"
                        + " and game_id = '" + game_id + "'";
                data_table = DB.getDataTable(sql_select);
                //答案
                String[] num_ans = null; //開獎號碼
                switch (gtype) {
                    case "801":
                        num_ans = new Lotto().getType4_Ans(num_801);
                        break;
                    case "802":
                        num_ans = new Lotto().getType4_Ans(num_802);
                        break;
                    case "803":
                        num_ans = new Lotto().getType4_Ans(num_803);
                        break;
                }
                //開始結算
                for (int i = 0; i < data_table.getRow(); i++) {
                    String member_ans = data_table.getColume(i, "num");
                    String system_id = data_table.getColume(i, "id");
                    String sql_update = null;
                    //補零 -> "01"
                    if (Integer.parseInt(member_ans) < 10) {
                        member_ans = "0" + member_ans;
                    }
                    //是否中獎
                    boolean isWin = false;
                    for (String num : num_ans) {
                        if (num.equals(member_ans)) {
                            isWin = true;
                        }
                    }
                    //結算                    
                    if (isWin) {
                        //中獎
                        int group_num = Integer.parseInt(data_table.getColume(i, "group_num")); //組數
                        int odds = Integer.parseInt(data_table.getColume(i, "odds")); //賠率
                        int bet_gold = Integer.parseInt(data_table.getColume(i, "bet_gold")); //下注金額
                        String result = String.valueOf((group_num * odds * bet_gold)); //組數 * 賠率 * 下注金額
                        sql_update = "update warges_main set earn_gold = '" + result + "' ,result = 'W' where id = " + system_id;
                    } else {
                        //沒中
                        sql_update = "update warges_main set earn_gold = '0' ,result = 'L' where id = " + system_id;
                    }
                    DB.query(sql_update);
                }
            }
        } catch (Exception ex) {
            LogTool.showLog("特別號開獎錯誤:" + ex.toString());
            return false;
        }
        return true;
    }

    //特尾三
    private boolean setGame_Type5() {
        try {
            String[] gtype_array = {"801", "802"};
            DataTable data_table;
            for (String gtype : gtype_array) {
                String sql_select = "SELECT * FROM warges_main where"
                        + " type= '5'"
                        + " and gtype = '" + gtype + "'"
                        + " and game_id = '" + game_id + "'";
                data_table = DB.getDataTable(sql_select);
                String num_ans = null; //開獎號碼
                switch (gtype) {
                    case "801":
                        num_ans = new Lotto().getType5_Ans(num_801);
                        break;
                    case "802":
                        num_ans = new Lotto().getType5_Ans(num_802);
                        break;
                }
                //開始結算
                for (int i = 0; i < data_table.getRow(); i++) {
                    String member_ans = data_table.getColume(i, "num");
                    String system_id = data_table.getColume(i, "id");
                    String sql_update = null;
                    //補零 -> "01"
                    if (Integer.parseInt(member_ans) < 10) {
                        member_ans = "0" + member_ans;
                    }
                    if (num_ans.equals(member_ans)) {
                        //中獎
                        int group_num = Integer.parseInt(data_table.getColume(i, "group_num")); //組數
                        int odds = Integer.parseInt(data_table.getColume(i, "odds")); //賠率
                        int bet_gold = Integer.parseInt(data_table.getColume(i, "bet_gold")); //下注金額
                        String result = String.valueOf((group_num * odds * bet_gold)); //組數 * 賠率 * 下注金額
                        sql_update = "update warges_main set earn_gold = '" + result + "' ,result = 'W' where id = " + system_id;
                    } else {
                        //沒中
                        sql_update = "update warges_main set earn_gold = '0' ,result = 'L' where id = " + system_id;
                    }
                    DB.query(sql_update);
                }
            }
        } catch (Exception ex) {
            LogTool.showLog("特別號開獎錯誤:" + ex.toString());
            return false;
        }
        return true;
    }

    //印出所有排列組合的類別
    class Permutations<T> {

        public Permutations(T obj) {//  泛型（支援任何陣列、集合、字串）
            if (obj instanceof Collection) {// 如果是集合，就轉成Object陣列
                Object[] o = new Object[((Collection) obj).size()];
                int i = 0;
                for (Object x : (Collection) obj) {
                    o[i++] = x;
                }
                int len = ((Collection) obj).size() - 1;
                perm(o, 0, len);

            } else if (obj instanceof String) {//如果是字串，就切割成Object陣列
                String[] s = ((String) obj).split("");
                String[] s2 = new String[s.length - 1];//第一個是空白，所以必須去掉
                for (int i = 0; i < s2.length; i++) {
                    s2[i] = s[i + 1];
                }
                int len = ((Object[]) s2).length - 1;
                perm((Object[]) s2, 0, len);
            } else {//  任何陣列都可以直接轉換成Object陣列
                int len = ((Object[]) obj).length - 1;
                perm((Object[]) obj, 0, len);
            }
        }

        private void perm(Object[] list, int k, int m) {
            if (k == m) {//  印出行
                for (int i = 0; i <= m; i++) {
                    System.out.print(list[i] + "　");
                }
                System.out.println();
            } else {//  繼續交換

                for (int i = k; i <= m; i++) {
                    swap(list, k, i);  //  交換
                    perm(list, k + 1, m);  //  遞迴，後面2個參數相等表示可印出
                    swap(list, k, i);  //  （因為是傳址呼叫，所以要換回來）
                }
            }
        }

        private void swap(Object[] array, int x1, int x2) {//  交換
            Object z = array[x1];
            array[x1] = array[x2];
            array[x2] = z;
        }

    }
    // </editor-fold>

    //天碰()
    private boolean setGame_Type910() {
        try {
            Lotto lotto = new Lotto();
            String gtype = "802";
            String[] type_array = {"9", "10"};
            String stype = "3";
            DataTable data_table;
            for (String type : type_array) {
                String sql_select = "SELECT * FROM warges_main where"
                        + " type= '" + type + "'"
                        + " and gtype = '" + gtype + "'"
                        + " and stype in (1,2,3)"
                        + " and game_id = '" + game_id + "'";
                data_table = DB.getDataTable(sql_select);
                //最低中獎柱數
                int num_limit = 0;
                switch (type) {
                    case "9":
                        num_limit = 2;
                        break;
                    case "10":
                        num_limit = 3;
                        break;
                }
                //開獎號碼
                String[] num_ans = null;
                switch (gtype) {
                    case "801":
                        num_ans = new String[7];
                        for (int i = 0; i < num_801.length; i++) {
                            num_ans[i] = num_801[i];
                        }
                        break;
                    case "802":
                        num_ans = new String[7];
                        for (int i = 0; i < num_802.length; i++) {
                            num_ans[i] = num_802[i];
                        }
                        break;
                }
                //結算
                for (int i = 0; i < data_table.getRow(); i++) {
                    boolean isWin = false; //是否中獎
                    String order_data = data_table.getColume(i, "num");
                    String system_id = data_table.getColume(i, "id");
                    JSONObject obj = new JSONObject(order_data);
                    JSONArray obj_array = obj.getJSONArray("result");
                    //結果
                    ArrayList<String> result_order = new ArrayList<>();
                    //單子
                    for (int j = 0; j < obj_array.length(); j++) {
                        //第一柱 比特別號
                        if (j == 0) {
                            JSONArray obj_d1 = obj_array.getJSONArray(j);
                            int count = 0; //一柱中獎個數
                            for (int k = 0; k < obj_d1.length(); k++) {
                                //每一柱裡面的號碼
                                String num = obj_d1.getString(k);
                                //補零 -> "01"
                                if (Integer.parseInt(num) < 10) {
                                    num = "0" + num;
                                }
                                //兌獎
                                if (num.equals(num_ans[6])) {
                                    count++;
                                }
                            }
                            if (count > 0) {
                                result_order.add(String.valueOf(count));
                            }
                        } else {
                            JSONArray obj_d1 = obj_array.getJSONArray(j);
                            int count = 0; //一柱中獎個數
                            for (int k = 0; k < obj_d1.length(); k++) {
                                //每一柱裡面的號碼
                                String num = obj_d1.getString(k);
                                //補零 -> "01"
                                if (Integer.parseInt(num) < 10) {
                                    num = "0" + num;
                                }
                                //兌獎
                                for (int l = 0; l < 7; l++) {
                                    if (num.equals(num_ans[l])) {
                                        count++;
                                    }
                                }
                            }
                            if (count > 0) {
                                result_order.add(String.valueOf(count));
                            }
                        }
                    }
                    //結算
                    int group_num = 0;
                    //是否中獎
                    //    boolean isWin = false;
                    if (result_order.size() >= num_limit) {
                        isWin = true;
                        String[] result_array = (String[]) result_order.toArray(new String[0]);
                        group_num = Integer.parseInt(lotto.getGameNum(result_array, num_limit));
                        if (type == "9") {
                            group_num = group_num - 1;
                        } else if (type == "10") {
                            group_num = (group_num - 1) * (group_num - 2) / 2;
                        }
                    }
                    //結算注單
                    int odds = Integer.parseInt(data_table.getColume(i, "odds")); //賠率
                    int bet_gold = Integer.parseInt(data_table.getColume(i, "bet_gold")); //賠率
                    int data_group = Integer.parseInt(data_table.getColume(i, "group_num")); //組數
                    String result_data = String.valueOf((group_num * odds * (bet_gold / data_group))); //組數*倍率*下注金額
                    String sql_update = null;
                    if (isWin) {
                        sql_update = "update warges_main set earn_gold = '" + result_data + "' ,result='W' where id = " + system_id;
                    } else {
                        sql_update = "update warges_main set earn_gold = '" + result_data + "' ,result='L' where id = " + system_id;
                    }
                    //更新資料表
                    DB.query(sql_update);
                }
            }

        } catch (Exception ex) {
            LogTool.showLog("天碰開獎錯誤:" + ex.toString());
            return false;
        }
        return true;
    }
}
