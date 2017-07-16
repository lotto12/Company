/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DB;
import model.DataTable;

/**
 *
 * @author wayne
 */
@WebServlet(name = "test", urlPatterns = {"/test"})
public class test extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void method3() {
        String sq = "";
        DataTable data_table;
        sq = "SELECT * "
                + "FROM game_agents_conf "
                + "Where id<287 ";
        //LogTool.showLog("sql------>" + sql_str);
        data_table = DB.getDataTable(sq);
        for (int i = 0; i < data_table.getRow(); i++) {
            sq = "INSERT game_agents_conf (master_id,gtype,type,type_sub,percent,percent_up,cost,re_cost,earn_cost,one_limit,num_limit)"
                    + "VALUE (" + data_table.getColume(i, "master_id") + ""
                    + "," + data_table.getColume(i, "gtype") + ""
                    + "," + data_table.getColume(i, "type") + ""
                    + ",3"
                    + "," + data_table.getColume(i, "percent") + ""
                    + "," + data_table.getColume(i, "percent_up") + ""
                    + "," + data_table.getColume(i, "cost") + ""
                    + "," + data_table.getColume(i, "re_cost") + ""
                    + "," + data_table.getColume(i, "earn_cost") + ""
                    + "," + data_table.getColume(i, "one_limit") + ""
                    + "," + data_table.getColume(i, "num_limit") + ""
                    + ")";
            //    sq = "UPDATE game_agents_conf SET type_sub = '2' WHERE id='" + data_table.getColume(i, "id") + "' ";
            DB.query(sq);
        }
    }

    public void method2() {
        String sq = "";
        DataTable data_table;
        sq = "SELECT * FROM game_setting_num1 WHERE type IN(6,7,8) AND gtype IN(803) AND type_sub IN(1,3) ";
        //LogTool.showLog("sql------>" + sql_str);
        data_table = DB.getDataTable(sq);
        for (int i = 0; i < data_table.getRow(); i++) {
            sq = "UPDATE game_setting_num1 SET "
                    + " ball_1= '" + data_table.getColume(i, "ball_1") + "0000' "
                    + ",ball_2= '" + data_table.getColume(i, "ball_2") + "0000' "
                    + ",ball_3= '" + data_table.getColume(i, "ball_3") + "0000' "
                    + ",ball_4= '" + data_table.getColume(i, "ball_4") + "0000' "
                    + ",ball_5= '" + data_table.getColume(i, "ball_5") + "0000' "
                    + ",ball_6= '" + data_table.getColume(i, "ball_6") + "0000' "
                    + ",ball_7= '" + data_table.getColume(i, "ball_7") + "0000' "
                    + ",ball_8= '" + data_table.getColume(i, "ball_8") + "0000' "
                    + ",ball_9= '" + data_table.getColume(i, "ball_9") + "0000' "
                    + ",ball_10= '" + data_table.getColume(i, "ball_10") + "0000' "
                    + ",ball_11= '" + data_table.getColume(i, "ball_11") + "0000' "
                    + ",ball_12= '" + data_table.getColume(i, "ball_12") + "0000' "
                    + ",ball_13= '" + data_table.getColume(i, "ball_13") + "0000' "
                    + ",ball_14= '" + data_table.getColume(i, "ball_14") + "0000' "                                        
                    + " WHERE id= " + data_table.getColume(i, "id") + " ";
            DB.query(sq);
        }
        DB.query(sq);
    }

    public void method1() {
        int master_id;
        int[] gtype = {801, 802, 803};
        int[] type = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int percent = 90;
        int percent_up = 0;
        int[] cost_dis1 = {7380, 9500, 7370, 7500, 6600, 7500, 7500, 7500, 6500, 6500};
        int[] cost_dis2 = {7350, 9500, 7375, 7500, 7000, 7350, 6350, 6100, 6800, 6800};
        int[] cost_dis3 = {0, 0, 7200, 7500, 0, 7250, 6350, 5100, 0, 0};
        int one_limit = 10000;
        int num_limit = 10000;
        int[] odds1 = {36, 89, 59375, 36, 750, 57, 570, 7500, 240, 1800};
        int[] odds2 = {36, 89, 59375, 11, 600, 57, 570, 7500, 240, 1800};
        int[] odds3 = {0, 0, 55788, 0, 0, 53, 570, 8000, 0, 0};
        int[] cost_dis = new int[10];
        int[] odds = new int[10];
        String sq = "";
        for (master_id = 1; master_id < 7; master_id++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 10; j++) {
                    switch (gtype[i]) {
                        case 801:
                            cost_dis = cost_dis1;
                            odds = odds1;
                            break;
                        case 802:
                            cost_dis = cost_dis2;
                            odds = odds2;
                            break;
                        case 803:
                            cost_dis = cost_dis3;
                            odds = odds3;
                            break;
                    }
                    if (i == 2 && (j == 0 || j == 1 || j == 4 || j == 8 || j == 9)) {
                        continue;
                    }
                    sq = "INSERT game_agents_conf (master_id,gtype,type,percent,percent_up,cost,re_cost,earn_cost,one_limit,num_limit)"
                            + "VALUE (" + master_id + "," + gtype[i] + "," + type[j] + ",90,0," + cost_dis[j] + "," + (10000 - cost_dis[j]) + ",0," + one_limit + "," + num_limit + ")";
                    DB.query(sq);
                }
            }
        }
    }

    public void method() {
        int flw_id = 12;
        int gtype = 801;
        int type = 2;
        int type_sub = 2;
        int page = 2;
        int ball_1 = 500;
        String sq = "";
        if (gtype == 803) {
            if (type == 8) { //特號
                if (page == 1) {
                    sq = "INSERT game_setting_num1 (flw_id,gtype,type,type_sub,page,"
                            + "ball_1,ball_2,ball_3,ball_4,ball_5,ball_6,ball_7,ball_8,ball_9,ball_10,ball_11,ball_12,ball_13,"
                            + "ball_14,ball_15,ball_16,ball_17,ball_18,ball_19,ball_20,ball_21,ball_22,ball_23,ball_24,ball_25) "
                            + "VALUE (" + flw_id + "," + gtype + "," + type + "," + type_sub + "," + page + ","
                            + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + ","
                            + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + ","
                            + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + ")";
                } else if (page == 2) {
                    sq = "INSERT game_setting_num1 (flw_id,gtype,type,type_sub,page,"
                            + "ball_1,ball_2,ball_3,ball_4,ball_5,ball_6,ball_7,ball_8,ball_9,ball_10,ball_11,ball_12,ball_13,"
                            + "ball_14) "
                            + "VALUE (" + flw_id + "," + gtype + "," + type + "," + type_sub + "," + page + ","
                            + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + ","
                            + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + ")";
                }

            } else if (type == 4 && type_sub == 1) {//台號
                if (page == 1) {
                    sq = "INSERT game_setting_num1 (flw_id,gtype,type,type_sub,page,"
                            + "ball_1,ball_2,ball_3,ball_4,ball_5,ball_6,ball_7,ball_8,ball_9,ball_10,ball_11,ball_12,ball_13,"
                            + "ball_14,ball_15,ball_16,ball_17,ball_18,ball_19,ball_20,ball_21,ball_22,ball_23,ball_24,ball_25) "
                            + "VALUE (" + flw_id + "," + gtype + "," + type + "," + type_sub + "," + page + ","
                            + "4000,860,960,1080,1220,1380,2000,2200,2000,2600,2600,3000,860,960,1080,1220,1380,1550,1760,2000,2000,2600,3000,860,960)";
                } else if (page == 2) {
                    sq = "INSERT game_setting_num1 (flw_id,gtype,type,type_sub,page,"
                            + "ball_1,ball_2,ball_3,ball_4,ball_5,ball_6,ball_7,ball_8,ball_9,ball_10,ball_11,ball_12,ball_13,"
                            + "ball_14,ball_15,ball_16,ball_17,ball_18,ball_19,ball_20,ball_21,ball_22,ball_23,ball_24,ball_25) "
                            + "VALUE (" + flw_id + "," + gtype + "," + type + "," + type_sub + "," + page + ","
                            + "1080,1220,1380,1550,1760,1760,2000,2600,3000,860,960,1080,1220,1380,1550,1550,1760,2000,2600,3000,860,960,1080,1220,1380)";
                } else if (page == 3) {
                    sq = "INSERT game_setting_num1 (flw_id,gtype,type,type_sub,page,"
                            + "ball_1,ball_2,ball_3,ball_4,ball_5,ball_6,ball_7,ball_8,ball_9,ball_10,ball_11,ball_12,ball_13,"
                            + "ball_14,ball_15,ball_16,ball_17,ball_18,ball_19,ball_20,ball_21,ball_22,ball_23,ball_24,ball_25) "
                            + "VALUE (" + flw_id + "," + gtype + "," + type + "," + type_sub + "," + page + ","
                            + "1380,1550,1760,2000,2600,3000,860,960,1080,1220,1220,1380,1550,1760,2000,2600,3000,860,960,1080,1080,1220,1380,1550,1760)";
                } else if (page == 4) {
                    sq = "INSERT game_setting_num1 (flw_id,gtype,type,type_sub,page,"
                            + "ball_1,ball_2,ball_3,ball_4,ball_5,ball_6,ball_7,ball_8,ball_9,ball_10,ball_11,ball_12,ball_13,"
                            + "ball_14,ball_15,ball_16,ball_17,ball_18,ball_19,ball_20,ball_21,ball_22,ball_23,ball_24,ball_25) "
                            + "VALUE (" + flw_id + "," + gtype + "," + type + "," + type_sub + "," + page + ","
                            + "2000,2600,3000,860,960,960,1080,1220,1380,1550,1760,2000,2600,3000,860,860,960,1080,1220,1380,1550,1760,2000,2600,3000)";
                }
            }
        } else if (gtype == 801) {
            if (type == 10) { //特號
                if (page == 1) {
                    sq = "INSERT game_setting_num1 (flw_id,gtype,type,type_sub,page,"
                            + "ball_1,ball_2,ball_3,ball_4,ball_5,ball_6,ball_7,ball_8,ball_9,ball_10,ball_11,ball_12,ball_13,"
                            + "ball_14,ball_15,ball_16,ball_17,ball_18,ball_19,ball_20,ball_21,ball_22,ball_23,ball_24,ball_25) "
                            + "VALUE (" + flw_id + "," + gtype + "," + type + "," + type_sub + "," + page + ","
                            + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + ","
                            + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + ","
                            + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + ")";
                } else if (page == 2) {
                    sq = "INSERT game_setting_num1 (flw_id,gtype,type,type_sub,page,"
                            + "ball_1,ball_2,ball_3,ball_4,ball_5,ball_6,ball_7,ball_8,ball_9,ball_10,ball_11,ball_12,ball_13,"
                            + "ball_14,ball_15,ball_16,ball_17,ball_18,ball_19,ball_20,ball_21,ball_22,ball_23,ball_24) "
                            + "VALUE (" + flw_id + "," + gtype + "," + type + "," + type_sub + "," + page + ","
                            + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + ","
                            + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + ","
                            + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + ")";
                }
            } else if (type == 2) { //雙面
                if (page == 1) {
                    sq = "INSERT game_setting_num1 (flw_id,gtype,type,type_sub,page,"
                            + "ball_1,ball_2,ball_3,ball_4,ball_5,ball_6,ball_7,ball_8,ball_9,ball_10,ball_11,ball_12,ball_13,"
                            + "ball_14,ball_15,ball_16,ball_17,ball_18,ball_19,ball_20,ball_21,ball_22,ball_23,ball_24,ball_25) "
                            + "VALUE (" + flw_id + "," + gtype + "," + type + "," + type_sub + "," + page + ","
                            + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + ","
                            + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + ","
                            + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + "," + ball_1 + ")";
                } else if (page == 2) {
                    sq = "INSERT game_setting_num1 (flw_id,gtype,type,type_sub,page,"
                            + "ball_1,ball_2,ball_3) "
                            + "VALUE (" + flw_id + "," + gtype + "," + type + "," + type_sub + "," + page + ","
                            + ball_1 + "," + ball_1 + "," + ball_1 + ")";
                }
            } else if (type == 4 && type_sub == 3) {//台號
                if (page == 1) {
                    sq = "INSERT game_setting_num1 (flw_id,gtype,type,type_sub,page,"
                            + "ball_1,ball_2,ball_3,ball_4,ball_5,ball_6,ball_7,ball_8,ball_9,ball_10,ball_11,ball_12,ball_13,"
                            + "ball_14,ball_15,ball_16,ball_17,ball_18,ball_19,ball_20,ball_21,ball_22,ball_23,ball_24,ball_25) "
                            + "VALUE (" + flw_id + "," + gtype + "," + type + "," + type_sub + "," + page + ","
                            + "3500,1000,1100,1300,1400,1600,1800,2000,2200,2500,2500,2600,900,900,1000,1100,1300,1400,1600,1800,2200,2500,2600,900,900)";
                } else if (page == 2) {
                    sq = "INSERT game_setting_num1 (flw_id,gtype,type,type_sub,page,"
                            + "ball_1,ball_2,ball_3,ball_4,ball_5,ball_6,ball_7,ball_8,ball_9,ball_10,ball_11,ball_12,ball_13,"
                            + "ball_14,ball_15,ball_16,ball_17,ball_18,ball_19,ball_20,ball_21,ball_22,ball_23,ball_24,ball_25) "
                            + "VALUE (" + flw_id + "," + gtype + "," + type + "," + type_sub + "," + page + ","
                            + "1000,1100,1300,1400,1600,2000,2200,2500,2600,900,900,1000,1100,1300,1400,1800,2000,2200,2500,2600,900,900,1000,1100,1300)";
                } else if (page == 3) {
                    sq = "INSERT game_setting_num1 (flw_id,gtype,type,type_sub,page,"
                            + "ball_1,ball_2,ball_3,ball_4,ball_5,ball_6,ball_7,ball_8,ball_9,ball_10,ball_11,ball_12,ball_13,"
                            + "ball_14,ball_15,ball_16,ball_17,ball_18,ball_19,ball_20,ball_21,ball_22,ball_23,ball_24,ball_25) "
                            + "VALUE (" + flw_id + "," + gtype + "," + type + "," + type_sub + "," + page + ","
                            + "1600,1800,2000,2200,2500,2600,900,900,1000,1100,1400,1600,1800,2000,2200,2500,2600,900,900,1000,1300,1400,1600,1800,2000)";
                } else if (page == 4) {
                    sq = "INSERT game_setting_num1 (flw_id,gtype,type,type_sub,page,"
                            + "ball_1,ball_2,ball_3,ball_4,ball_5,ball_6,ball_7,ball_8,ball_9,ball_10,ball_11,ball_12,ball_13,"
                            + "ball_14,ball_15,ball_16,ball_17,ball_18,ball_19,ball_20,ball_21,ball_22,ball_23,ball_24,ball_25) "
                            + "VALUE (" + flw_id + "," + gtype + "," + type + "," + type_sub + "," + page + ","
                            + "2200,2500,2600,900,900,1100,1300,1400,1600,1800,2000,2200,2500,2600,900,1000,1100,1300,1400,1600,1800,2000,2200,2500,2600)";
                }
            }
        }
        DB.query(sq);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet test</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet test at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            method2();
            response.sendRedirect("WebPage/OrderHistory.jsp");
        }
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

    private void alert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void alert(String ok) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
