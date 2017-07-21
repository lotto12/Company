/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DB;
import model.DataTable;
import org.json.JSONObject;

/**
 *
 * @author JimmyYang 取得遊戲相關設定
 */
public class GetSetting extends HttpServlet {

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
                    //無權限
                    System.out.println("test:" + request.getContextPath());
                    obj.put("status", true);
                    obj.put("account", user_name);
                    String[] data = getGameSetting(request);
                    obj.put("odd", data[0]);
                    obj.put("back_gold", data[1]);
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

    //取得遊戲相關設定
    private String[] getGameSetting(HttpServletRequest request) {
        String gtype = request.getParameter("gtype");
        String type = request.getParameter("type");
        String ball = request.getParameter("ball");

        String odd = "error"; //賠率
        String back_gold = "error"; //本金
        String[] data = new String[2];
        System.out.println("ball:" + ball);
        try {
            String[] num_sql = getNum_SQL_str(Integer.parseInt(ball));
            String page_str = num_sql[0];
            String ball_str = num_sql[1];

            String sql_select = "select ball_" + ball_str + " "
                    + "from game_setting_num1 where "
                    + page_str + " and gtype = " + gtype + " and type = " + type + " "
                    + "and type_sub in (1,2) order by type_sub asc";

            System.out.println(sql_select);

            DataTable dt = DB.getDataTable(sql_select);

            if (dt.getRow() > 0) {
                odd = dt.getColume(0, "ball_" + ball_str);
                back_gold = dt.getColume(1, "ball_" + ball_str);

                //換算
                odd = String.valueOf(Integer.parseInt(odd) / 10000);
                back_gold = String.valueOf(Integer.parseInt(back_gold) / 100.0);

            }
        } catch (Exception e) {
            System.out.println("e:" + e.toString());
        }

        data[0] = odd;
        data[1] = back_gold;
        return data;
    }

    //取得球號SQL
    private String[] getNum_SQL_str(int num) {
        double page = num / 25;
        int num_order = num - (25 * (int) page);

        if (page - (int) page != 0) {
            page++;
        }

        System.out.println("num_order:" + num_order);

        String page_str = String.valueOf((int) page);
        String ball_str = "1"; //SQL球號
        if (num_order == 0) {
            num_order = num - ((int) page - 1) * 25;
        }
        ball_str = String.valueOf(num_order);

        if (page < 1) {
            page_str = "1";
        }

        String sql_where = " page = " + page_str;
        String[] data = {sql_where, ball_str};
        return data;
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
