/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import java.util.Date;

/**
 *
 * @author JimmyYang 加密
 */
public class EncryptionCode {

//    public static void main(String args[]) {
//        EncryptionCode pCode = new EncryptionCode();
//        String pwd = "1Wd1hGRs1ta20C2d374gx3qXg07y";
//        int length = pwd.length();
//        //String check_pwd = pCode.getCheckPwd_Client(pwd);
//        //System.out.println(check_pwd);
//        System.out.println(pCode.parseCode(2, pwd));
//
//        //System.out.println(new Date().getTime());
//        String v = pCode.getCheckPwd_Client();
//
//        System.out.println(v);
//        System.out.println(pCode.parseCode_Client("1041919F5g715R186l944i9d171"));
//
//    }

    //解密_注單使用
    public String parseCode(int length, String parseCode) {
        StringBuilder result = new StringBuilder();
        String[] parseCode_array = parseCode.split("");
        int count = length;
        for (int i = 0; i < parseCode_array.length; i++) {
            if (i % 2 == 0 && count > 0) {
                result.append(parseCode_array[i]);
                count--;
            }
        }
        return result.toString();
    }

    //產生密碼
    public String getCheckPwd(String pwd) {
        String[] pwd_array = pwd.split("");
        int Max = 30; //密碼長度
        int total = Max - pwd_array.length;
        StringBuilder str = new StringBuilder();
        java.util.Random r = new java.util.Random();
        int rnd = 0;
        int count = pwd_array.length;
        int count_p = count;
        for (int i = 0; i < total; i++) {
            if (i % 2 == 0 && count_p > 0) {
                str.append(pwd_array[count - count_p]);
                count_p--;
            } else if (getNumChar(r)) {
                rnd = r.nextInt(9);
                str.append(rnd);
            } else {
                rnd = r.nextInt(52);
                if (rnd < 26) {
                    str.append((char) (rnd + 65));
                } else {
                    str.append((char) (rnd - 26 + 97));
                }
            }
        }
        return str.toString();
    }

    //解密_注單使用_Client
    public boolean parseCode_Client(String parseCode) {
        boolean isV = false;

        Date now_time = new Date();
        String pwd_time = String.valueOf(now_time.getTime());
        String[] pwd_array = pwd_time.split("");

        StringBuilder result = new StringBuilder();
        String[] parseCode_array = parseCode.split("");
        int count = pwd_array.length;
        for (int i = 0; i < parseCode_array.length; i++) {
            if (i % 2 == 0 && count > 0) {
                result.append(parseCode_array[i]);
                count--;
            }
        }
        Long t1 = new Date().getTime();
        Long t2 = Long.parseLong(result.toString());
        if (t1 - t2 < 2500) {
            isV = true;
        }
        return isV;
    }

    //產生密碼_Client
    public String getCheckPwd_Client() {
        Date now_time = new Date();
        String pwd_time = String.valueOf(now_time.getTime());
        String[] pwd_array = pwd_time.split("");
        int Max = 40; //密碼長度
        int total = Max - pwd_array.length;
        StringBuilder str = new StringBuilder();
        java.util.Random r = new java.util.Random();
        int rnd = 0;
        int count = pwd_array.length;
        int count_p = count;
        for (int i = 0; i < total; i++) {
            if (i % 2 == 0 && count_p > 0) {
                str.append(pwd_array[count - count_p]);
                count_p--;
            } else if (getNumChar(r)) {
                rnd = r.nextInt(9);
                str.append(rnd);
            } else {
                rnd = r.nextInt(52);
                if (rnd < 26) {
                    str.append((char) (rnd + 65));
                } else {
                    str.append((char) (rnd - 26 + 97));
                }
            }
        }
        return str.toString();
    }

    private boolean getNumChar(java.util.Random r) {
        if (r.nextInt(100) > 50) {
            return true;
        } else {
            return false;
        }
    }
}
