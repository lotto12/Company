package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.nio.file.Files.newBufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author JimmyYang 用來DEBUG使用 調用Ex. LogTool.showLog("test");
 */
public class LogTool {

    //LOG檔案路徑
    private static String file = "F:\\MyData\\Dropbox\\WebProject\\Log.txt";

    //紀錄時間
    private static Date date = new Date();

    //紀錄比數
    private static int data_count = 500;

    //紀錄LOG
    public synchronized static void showLog(String msg) {
        String old_data = readFile();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("#" + getDateTime() + "-->" + msg + "\r\n");
            fw.write(old_data);
            fw.flush();
        } catch (Exception e) {
            System.out.println("LOG寫入檔案錯誤:" + e.toString());
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
            System.out.println("LOG讀取檔案錯誤:" + e.toString());
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
