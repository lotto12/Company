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
import model.DB;

/**
 *
 * @author JimmyYang 網頁跳板
 */
public class Main extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        //選擇功能
        String id = request.getParameter("function");
        try {
            //選取功能
            HttpSession session;
            switch (id) {
                case "1":
                    //SQL_select_DEBUG
                    String sql_select = request.getParameter("sql_select");
                    session = request.getSession();
                    session.setAttribute("sql_select", sql_select);
                    response.sendRedirect("Debug/Search.jsp");
                    break;
                case "2":
                    //SQL_select_table_DEBUG
                    String table = request.getParameter("table");
                    session = request.getSession();
                    session.setAttribute("sql_select", "select * from " + table);
                    response.sendRedirect("Debug/Search.jsp");
                    break;
                case "login":
                    //首頁登入功能
                    String user_acount = request.getParameter("user_acount");
                    String user_pwd = request.getParameter("user_pwd");
                    String ip = request.getRemoteAddr();
                    System.out.println("[登入] ip->" + ip);
                    if (new GameMember().isMemberOK(user_acount, user_pwd)) {
                        session = request.getSession();
                        session.setAttribute("user_acount", user_acount);
                        //預設_六合彩_特別號
                        session.setAttribute("Gtype", "801");
                        session.setAttribute("game_type", "1");
                        response.sendRedirect("Warning.jsp");
                    } else {
                        showAlert(response, "帳號密碼錯誤", "index.html");
                    }
                    break;
                case "change_pwd":
                    //更改密碼
                    String old_pwd = request.getParameter("old_pwd");
                    String new_pwd = request.getParameter("mew_pwd");
                    String new_pwd_chk = request.getParameter("new_pwd_chk");
                    session = request.getSession();
                    String acount = (String) session.getAttribute("user_acount");
                    if (acount != null) {
                        String reuslt = new GameMember().updatePwd(acount, old_pwd, new_pwd, new_pwd_chk);
                        showAlert(response, reuslt, "WebPage/ChangePwd.jsp");
                    }
                    break;
                case "send_msg":
                    //會員留言
                    String acount_str = request.getParameter("acount");
                    String phone = request.getParameter("phone");
                    String email = request.getParameter("email");
                    String msg = request.getParameter("msg");
                    if (new GameMember().isMsgSend(acount_str, phone, email, msg)) {
                        showAlert(response, "留言成功", "WebPage/Contact.jsp");
                    } else {
                        showAlert(response, "請確認資料是否正確", "WebPage/Contact.jsp");
                    }
                    break;
                case "set_gtype":
                    //設定遊戲種類_gtype
                    String Gtype = request.getParameter("Gtype");
                    String type_init = new GameMethod().getGame_InitGame(Gtype);
                    session = request.getSession();
                    //抓取遊戲種類第一項遊戲類型
                    session.setAttribute("Gtype", Gtype);
                    session.setAttribute("game_type", type_init);
                    response.sendRedirect("Game_index.jsp");
                    break;
                case "set_type":
                    //設定遊戲種類_type
                    String type = request.getParameter("game_type");
                    session = request.getSession();
                    session.setAttribute("game_type", type);
                    response.sendRedirect("Game_Main.jsp");
                    break;

            }
        } catch (Exception ex) {
            show_html(response, "功能編號:" + id + "<br>網頁錯誤<br>" + ex.toString());
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
            out.println("<h1>" + msg + "</h1>");
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
