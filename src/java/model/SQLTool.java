/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author JimmyYang
 */
public class SQLTool {

    //LOG檔案路徑
    private static String file = "F:\\MyData\\Dropbox\\WebProject\\SQLDebug.txt";

    //紀錄時間
    private static Date date = new Date();

    //紀錄比數
    private static int data_count = 500;

    //紀錄LOG
    public synchronized static void showData_byName(String sql_select) {
        StringBuilder str = new StringBuilder();
        DataTable dt = DB.getDataTable(sql_select);
        String[] titles = dt.getColumn_Name();
        for (int i = 0; i < dt.getRow(); i++) {
            for (int j = 0; j < titles.length; j++) {
                str.append("{").append("[").append(i + 1).append("筆][").append(titles[j]).append("]").append("=>").append(dt.getColume(i, titles[j])).append("}");
            }
        }
        String old_data = readFile();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("#" + getDateTime() + "-->" + str.toString() + "\r\n");
            fw.write(old_data);
            fw.flush();
        } catch (Exception e) {
            System.out.println("SQLTOOL寫入檔案錯誤:" + e.toString());
        }
    }

    //紀錄LOG
    public synchronized static void showData_byArray(String sql_select) {
        StringBuilder str = new StringBuilder();
        String[][] selct_data = DB.getDataArray(sql_select);
        for (int i = 0; i < selct_data.length; i++) {
            for (int j = 0; j < selct_data[i].length; j++) {
                str.append("{").append("[").append(i).append("][").append(j).append("]").append("=>").append(selct_data[i][j]).append("}");
            }
        }

        String old_data = readFile();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("#" + getDateTime() + "-->" + str.toString() + "\r\n");
            fw.write(old_data);
            fw.flush();
        } catch (Exception e) {
            System.out.println("SQLTOOL寫入檔案錯誤:" + e.toString());
        }
    }

    //讀取
    private static String readFile() {
        int count = 0;
        StringBuilder result = new StringBuilder();
        try {
            FileReader dataFile = new FileReader(file);
            BufferedReader output = new BufferedReader(dataFile);
            String str;
            while ((str = output.readLine()) != null) {
                count++;
                result.append(str).append("\r\n");
                if (count == data_count) {
                    result = new StringBuilder();
                    result.append("#").append(getDateTime()).append("-->" + "刪除前").append(data_count).append("筆紀錄");
                    result.append("\n");
                }
            }
            output.close();
        } catch (Exception e) {
            System.out.println("SQLTOOL讀取檔案錯誤:" + e.toString());
        }
        return result.toString();
    }

    //取得時間yyyy/MM/dd hh:mm:ss
    private static String getDateTime() {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String strDate = sdFormat.format(date);
        return strDate;
    }
}
