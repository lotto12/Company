/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import CheckOrder.GetAllSet;
import CheckOrder.SpcOrderChk;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DB;
import model.DataTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Jimmy 下注測試
 */
public class AddOrder extends HttpServlet {

    private GetAllSet getAllSet = new GetAllSet();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(); //網頁暫存
            String user_name = String.valueOf(session.getAttribute("user_acount"));
            JSONObject obj = new JSONObject();
            try {
                if (!user_name.equals("null")) {
                    //取得權限
                    boolean isV = false;
                    boolean isSPC = false;

                    String order = request.getParameter("order");

                    //拆解
                    JSONObject obj_order = new JSONObject(order);
                    JSONArray data = obj_order.getJSONArray("data");//遊戲資料
                    int gtype = obj_order.getInt("gtype");
                    String order_id = obj_order.getString("order_id");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject order_obj = data.getJSONObject(i);
                        int type = order_obj.getInt("type");
                        int stype = order_obj.getInt("stype");
                        String num = order_obj.getString("num");

                        //注單資料
                        String bet_gold = order_obj.getString("pay");
                        String num_combin = "";
                        String odds = order_obj.getString("odds");
                        String group_num = order_obj.getString("group_num");
                        int account = getMemberID(user_name);
                        String back_gold = String.valueOf(100 - Integer.parseInt(odds));
                        String gold = "0";
                        String remarks = order_id;

                        //特殊牌型
                        String spc_data = null;

                        //拆解注單                        
                        if (type < 6) {
                            //單號下注單
                            num_combin = order_obj.getString("num");
                        } else {
                            //多號下注單
                            JSONObject result = new JSONObject(num);
                            JSONArray array = result.getJSONArray("result");
                            if (array.length() > 1) {
                                System.out.println("length:" + array.length());

                                //立柱
                                ArrayList<int[]> user_order = getOrder_stype3(array);

                                //全部組合
                                ArrayList<int[]> all_set = getAllSet.getAllSet(user_order, getStar(type));

                                //拆組（特殊牌型及一般牌型）_TEST
                                SpcOrderChk spc_order_chk = new SpcOrderChk(all_set, gtype, stype);

                                //放資料庫
                                String org_data = spc_order_chk.getORG_DATA(); //一般牌型
                                spc_data = spc_order_chk.getSPC_DATA(); //特殊牌型

                                //一般組合
                                num_combin = org_data;

                                //是否為特殊牌型
                                isSPC = spc_order_chk.isSPC();

                            } else if (array.length() == 1) {
                                //連碰
                                ArrayList<Integer> user_order = getOrder_stype2(array);

                                //全部組合
                                ArrayList<int[]> all_set = getAllSet.getAllSet_1(user_order, getStar(type));

                                //拆組（特殊牌型及一般牌型）_TEST
                                SpcOrderChk spc_order_chk = new SpcOrderChk(all_set, gtype, stype);

                                //放資料庫
                                String org_data = spc_order_chk.getORG_DATA(); //一般牌型
                                spc_data = spc_order_chk.getSPC_DATA(); //特殊牌型

                                //一般組合
                                num_combin = org_data;

                                //是否為特殊牌型
                                isSPC = spc_order_chk.isSPC();
                            }
                        }

                        //SQL_INSERT
                        String sql_insert = "INSERT INTO game_warges_main (game_id , gtype , type , "
                                + "stype , bet_gold , num , num_combin , odds,group_num ,"
                                + " account , back_gold , gold,remarks)"
                                + "VALUES (2, " + gtype + ", " + type + "," + stype + ","
                                + bet_gold + ",'" + num + "','" + num_combin + "',"
                                + odds + "," + group_num + ",'" + account + "',"
                                + back_gold + "," + gold + ",'" + remarks + "');";

                        int id = DB.query_id(sql_insert); //編號
                        if (id != -1) {
                            //放入特殊牌型
                            if (isSPC) {
                                System.out.println("spc_data:" + spc_data);
                            }
                            isV = true;
                        }

                        //isV = DB.query(sql_insert);
                    }
                    obj.put("status", isV);
                    out.println(obj.toString());
                } else {
                    obj.put("status", false);
                    out.println(obj.toString());
                }
            } catch (Exception ex) {
                out.println(ex.toString());
            }

        }
    }

    //取得會員id
    private int getMemberID(String account) {
        String sql_select = "select id from game_member where acount = '" + account + "'";
        DataTable dt = DB.getDataTable(sql_select);
        int id = -1;
        if (dt.getRow() > 0) {
            id = Integer.parseInt(dt.getColume(0, "id"));
        }
        return id;
    }

    //取得星數
    private int getStar(int type) {
        switch (type) {
            case 6:
                return 2;
            case 7:
                return 3;
            case 8:
                return 4;
            default:
                return 0;
        }
    }

    //立柱處理
    private ArrayList<int[]> getOrder_stype3(JSONArray array) {
        ArrayList<int[]> result = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONArray array_d = array.getJSONArray(i);
                int[] data = new int[array_d.length()];
                for (int j = 0; j < data.length; j++) {
                    data[j] = array_d.getInt(j);
                }
                result.add(data);
            }
        } catch (JSONException ex) {
            Logger.getLogger(AddOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //連碰處理
    private ArrayList<Integer> getOrder_stype2(JSONArray array) {
        ArrayList<Integer> result = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONArray array_d = array.getJSONArray(i);
                for (int j = 0; j < array_d.length(); j++) {
                    result.add(array_d.getInt(j));
                }
            }
        } catch (JSONException ex) {
            Logger.getLogger(AddOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
