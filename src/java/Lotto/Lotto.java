/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lotto;

import java.math.BigInteger;
import java.util.Arrays;

/**
 *
 * @author JimmyYang
 */
public class Lotto {

    public static void main(String[] args) {
        Lotto a = new Lotto();
        String str = a.getGameNum(new String[]{"1", "2", "3", "4"}, 2);
        System.out.println(str);
    }

    //輸入組數 data , 星_立柱碰用
    public String getGameNum(String[] data, int get_num) {
        long total = 0;
        String number[] = data; //需要輸入數字個數(3)

        int m = get_num;  // 要選取的個數(2)

        long h = number.length;

        /*
     *建一個二位元字串矩陣 28~42行==>000,001,010,011,100,101,110,111
         */
        int q = (int) (Math.pow(2, h));
        String binstr[] = new String[q];
        int i = 0;
        for (i = 0; i < q; i++) {
            binstr[i] = Integer.toBinaryString(i);
        }//for
        String formatStr = "%0" + h + "d";

        BigInteger num[] = new BigInteger[q];

        for (i = 0; i < binstr.length; i++) {
            //System.out.println(binstr[i]);
            num[i] = new BigInteger(binstr[i]);

            //num[i] = Long.decode(binstr[i]);
            binstr[i] = String.format(formatStr, num[i]);

        }//for

        for (int k = 0; k < q; k++) { //比對每一個二位元字串，並選取其中"1"個為m個數的字串 例:101=>count=2,100=>count=1
            int count = 0;
            for (i = 0; i < binstr[k].length(); i++) {

                char c = binstr[k].charAt(i);
                if (c == '1') {
                    count++;
                }

            }
            if (count == m) {
                long total_m = 1;
                for (i = 0; i < h; i++) {
                    if (binstr[k].charAt(i) == '1') {
                        total_m *= Integer.parseInt(number[i]);
                    }
                }
                total += total_m;
            }
        }
        //System.out.println("產生--->" + total);
        return String.valueOf(total);
    }

    //匯出台號(不包含特別號)
    public String[] getType4_Ans(String[] ans) {
        //判斷有無特別號
        int spc = 0;
        if (ans.length > 6) {
            spc = 1;
        }
        int[] ans_int = new int[ans.length - spc];
        for (int i = 0; i < ans_int.length; i++) {
            ans_int[i] = Integer.parseInt(ans[i]);
        }
        Arrays.sort(ans_int);
        //組回String
        for (int i = 0; i < ans_int.length; i++) {
            String num = null;
            if (ans_int[i] < 10) {
                num = "0" + ans_int[i];
            } else {
                num = String.valueOf(ans_int[i]);
            }
            ans[i] = num;
        }
        //組裝台號
        String[] result = new String[ans.length - 1];
        for (int i = 0; i < result.length; i++) {
            String num1 = ans[i].substring(1, 2); //後面的數字
            String num2 = ans[i + 1].substring(1, 2); //後面的數字
            result[i] = num1 + num2;
        }
        return result;
    }

    //取特尾三
    public String getType5_Ans(String[] ans) {
        String[] data = getType4_Ans(ans);
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= 3; i++) {
            result.append(data[i].substring(1, 2));
        }
        return result.toString();
    }
}
