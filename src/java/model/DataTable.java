/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author JimmyYang
 */
public class DataTable {

    //欄位名稱
    private String[] column_name = null;
    private String[][] result_data = null;

    public DataTable(String[] column_name, String[][] result_data) {
        this.column_name = column_name;
        this.result_data = result_data;

    }

    //取得欄位資料
    public String getColume(int row, String name) {
        int colume_num = 0;
        boolean isV = false;
        for (int i = 0; i < this.column_name.length; i++) {
            if (column_name[i].equals(name)) {
                colume_num = i;
                isV = true;
            }
        }
        if (isV) {
            return result_data[row][colume_num];
        }
        return null;
    }

    //取得行數
    public int getRow() {
        return this.result_data.length;
    }

    //取得欄位名稱
    public String[] getColumn_Name() {
        return this.column_name;
    }
}
