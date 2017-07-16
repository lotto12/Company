/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Function.GameMember;
import Function.GameMethod;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author JimmyYang 接收下注單使用
 */
public class Order extends HttpServlet {

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
        //選擇功能
        String function = request.getParameter("function");
        try {
            //選取功能
            HttpSession session;
            session = request.getSession();
            switch (function) {
                case "add_order":
                    //新增注單
                    String gtype = request.getParameter("gtype");
                    String type = request.getParameter("type");
                    String data = request.getParameter("data");
                    session.setAttribute("order_data", data);
                    response.sendRedirect("Odd/Odds.jsp");
                    break;
                case "set_order":
                    //確認下注
                    //寫入資料庫
                    break;
            }
        } catch (Exception ex) {
            show_html(response, "功能編號:" + function + "<br>網頁錯誤<br>" + ex.toString());
        }
    }

    //顯示文字
    private void show_html(HttpServletResponse response, String msg) {
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Main</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>" + msg + "</p>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
            System.out.println("show_html exception =" + ex.toString());
        }
    }

    //顯示alert訊息
    private void showAlert(HttpServletResponse response, String msg, String back_url) {
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<script type=\"text/javascript\">\n"
                    + "alert('" + msg + "')\n"
                    + "location='" + back_url + "'\n"
                    + "</script>");
        } catch (Exception ex) {
            System.out.println("show_result exception =" + ex.toString());
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

}
