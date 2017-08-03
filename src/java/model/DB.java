/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author JimmyYang
 */
public class DB {

    //DB連線使用_edit by jimmy 20170212      
    //需要先載入DB_Config、DataTable類別
    //DB.getDataTable(String sql)-->取得SQL資料_DataTable格式
    //DB.getDataArray(String sql)-->取得SQL資料_Aarry格式
    //DB.query(String query_sql)-->向SQL資料庫下指令_回傳boolean   
    //--------------------------------------
    //Test->DB.debug_showData(String[][] result)-->Debug用_顯示SQL查詢資料
    //--------------------------------------
    //連線物件
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pst = null; //預先指令 ?
    private ResultSet rs = null;

    //DB_帳號密碼
    private DB_Config db_config;

    //取得DB_result_DataTable
    public static DataTable getDataTable(String sql) {
        DB db = new DB();
        DataTable result = db.getDataTable_private(sql);
        db.close();
        return result;
    }

    //私有_取得DB_result_DataTable
    private DataTable getDataTable_private(String sql) {
        String[][] result_data = null;
        String[] colume_name = null;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            ResultSetMetaData rm = rs.getMetaData();
            int cnum = rm.getColumnCount(); //項目數
            colume_name = new String[cnum];
            for (int i = 0; i < cnum; i++) {
                colume_name[i] = rm.getColumnName(i + 1);
            }
            rs.last();
            int total = rs.getRow(); //行數
            result_data = new String[total][cnum];
            String[] c_data = null;
            rs.beforeFirst();
            for (int i = 0; i < total; i++) {
                rs.next();
                c_data = new String[cnum];
                for (int j = 0; j < cnum; j++) {
                    c_data[j] = String.valueOf(rs.getObject(j + 1));
                }
                result_data[i] = c_data;
            }
        } catch (SQLException e) {
            LogTool.showLog("SelectDB Exception :" + e.toString());
            System.out.println("SelectDB Exception :" + e.toString());
        }

        DataTable result = null;
        if (result_data != null && colume_name != null) {
            result = new DataTable(colume_name, result_data);
        }
        return result;
    }

    //下指令
    public static boolean query(String query_sql) {
        DB db = new DB();
        boolean result = db.query_private(query_sql);
        db.close();
        return result;
    }

    //下指令回傳ID
    public static int query_id(String query_sql) {
        int result_int = -1;
        DB db = new DB();
        boolean result = db.query_private(query_sql);
        String select = "SELECT @@IDENTITY;";
        String[][] result_s = db.getDataArray_private(select);
        if (result_s.length > 0 && result) {
            result_int = Integer.parseInt(result_s[0][0]);
        }
        db.close();
        return result_int;
    }

    //下指令_privte
    private boolean query_private(String query_sql) {
        try {
            pst = conn.prepareStatement(query_sql);
            pst.executeUpdate();
            return true;
        } catch (SQLException ex) {
            LogTool.showLog("Query exception ->" + ex.toString());
            System.out.println("Query exception ->" + ex.toString());
            return false;
        }
    }

    //取得DB_result_Array
    public static String[][] getDataArray(String sql) {
        DB db = new DB();
        String[][] reuslt = db.getDataArray_private(sql);
        db.close();
        return reuslt;
    }

    //私有_取得Array Data
    private String[][] getDataArray_private(String select_sql) {
        String[][] result_data = null;
        try {
            pst = conn.prepareStatement(select_sql);
            rs = pst.executeQuery();
            ResultSetMetaData rm = rs.getMetaData();
            int cnum = rm.getColumnCount(); //項目數
            rs.last();
            int total = rs.getRow(); //行數
            result_data = new String[total][cnum];
            String[] c_data = null;
            rs.beforeFirst();
            for (int i = 0; i < total; i++) {
                rs.next();
                c_data = new String[cnum];
                for (int j = 0; j < cnum; j++) {
                    c_data[j] = String.valueOf(rs.getObject(j + 1));
                }
                result_data[i] = c_data;
            }
        } catch (SQLException e) {
            LogTool.showLog("SelectDB Exception :" + e.toString());
            System.out.println("SelectDB Exception :" + e.toString());
        }
        return result_data;
    }

    //取得單筆資料
    public String getDataResult(String select_sql) {
        String result_data = null;
        try {
            pst = conn.prepareStatement(select_sql);
            rs = pst.executeQuery();
            ResultSetMetaData rm = rs.getMetaData();
            rs.first();
            result_data = String.valueOf(rs.getObject(1));
        } catch (SQLException e) {
            LogTool.showLog("SelectDB Exception :" + e.toString());
            System.out.println("SelectDB Exception :" + e.toString());
        }
        return result_data;
    }

    //測試_Debug用_顯示資料
    public static void debug_showData(String[][] result) {
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                System.out.print("result[" + i + "][" + j + "]-->" + result[i][j] + "\t");
            }
            System.out.println("\n");
        }
    }

    //SQL連線資料
    public DB() {
        db_config = new DB_Config();
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://" + db_config.getIP() + ":3306/" + db_config.getDB() + "?useUnicode=true&characterEncoding=UTF8";
        String user = db_config.getID();
        String password = db_config.getPwd();

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null && !conn.isClosed()) {
                //System.out.println("資料庫連線測試成功！");
            }
        } catch (ClassNotFoundException e) {
            LogTool.showLog("DriverClassNotFound :" + e.toString());
            System.out.println("DriverClassNotFound :" + e.toString());
        } catch (SQLException x) {
            LogTool.showLog("Exception :" + x.toString());
            System.out.println("Exception :" + x.toString());
        }
    }

    //關閉SQL資源
    public void close() {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (pst != null) {
                pst.close();
                pst = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            LogTool.showLog("Close Exception :" + e.toString());
            System.out.println("Close Exception :" + e.toString());
        }
    }

}
