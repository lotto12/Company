/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackGround;

import static java.lang.Thread.sleep;
import java.util.Calendar;
import model.LogTool;

/**
 *
 * @author JimmyYang 背景執行使用
 */
public class MainThread {

    //主執行續
    private static Thread rateThread;
    private static Calendar calendar;

    public static void main(String[] args) {
        rateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        calendar = Calendar.getInstance();
                        int second = calendar.get(Calendar.SECOND);
                        int min = calendar.get(Calendar.MINUTE);

                        //START
                        workMission_Sec(5, second);

                        //END
                        sleep(1000);
                    } catch (InterruptedException e) {
                        LogTool.showLog("MainThread Exception->" + e.toString());
                    }
                }
            }
        });
        rateThread.start();
    }

    //執行工作(多久執行一次(秒),系統秒數)
    private static void workMission_Sec(int time, int sys_time) {
        if (sys_time % time == 0) {
            //執行程式
            System.out.println(sys_time);
        }
    }
}
