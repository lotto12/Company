
import java.util.Arrays;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author JimmyYang
 */
public class test01 {

    public static void main(String[] args) {

        Scanner sn = new Scanner(System.in);

        System.out.println("總個數:");
        String number[] = new String[sn.nextInt()]; //需要輸入數字個數(3)

        for (int i = 0; i < number.length; i++) {
            System.out.println("第" + (i + 1) + "個數:");
            number[i] = sn.next();  //  依使用者輸入的數字，建一字串矩陣{"1","3","5"}
        }
        System.out.println("取幾幾個為一組:");
        int m = sn.nextInt();  // 要選取的個數(2)

        int h = number.length;

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

        int num[] = new int[q];

        for (i = 0; i < binstr.length; i++) {
            num[i] = Integer.parseInt(binstr[i]);
            binstr[i] = String.format(formatStr, num[i]);

        }//for

        for (int k = 0; k < q; k++) { //比對每一個二位元字串，並選取其中"1"個為m個數的字串 例:101=>count=2,100=>count=1
            int count = 0;
            for (i = 0; i < binstr[k].length(); i++) {

                char c = binstr[k].charAt(i);
                if (c == '1') {
                    count++;
                }

            }//for

            if (count == m) {  //二位字串轉換成number[]值，例 110 =>{1,3} ,101=>{1,7} ,011=>{3,7}
                for (i = 0; i < h; i++) {
                    if (binstr[k].charAt(i) == '1') {

                        String fin = "";
                        fin += number[i] + " ";
                        System.out.print(fin);
                    }//if
                }//for
                System.out.println();
            }//if(count==m)

        }//for

    }//main

}
