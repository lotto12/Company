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

                    String order = request.getParameter("order");

                    //拆解
                    JSONObject obj_order = new JSONObject(order);
                    JSONArray data = obj_order.getJSONArray("data");//遊戲資料
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject order_obj = data.getJSONObject(i);
                        int type = order_obj.getInt("type");
                        String num = order_obj.getString("num");

                        //拆解注單                        
                        if (type < 6) {
                            //單號下注單

                        } else {
                            //多號下注單
                            JSONObject result = new JSONObject(num);
                            JSONArray array = result.getJSONArray("result");
                            if (array.length() > 0) {
                                //立柱
                                ArrayList<int[]> user_order = getOrder_stype3(array);

                                //全部組合
                                ArrayList<int[]> all_set = getAllSet.getAllSet(user_order, getStar(type));

                                //拆組（特殊牌型及一般牌型）
                                SpcOrderChk spc_order_chk = new SpcOrderChk(all_set, type, type);
                                
                            } else {
                                //連碰

                            }
                        }

                        isV = true;
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
