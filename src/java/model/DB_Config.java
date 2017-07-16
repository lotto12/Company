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
public class DB_Config {

    /**
     * DB連線使用_edit by jimmy 20170212 存放DB的帳號、密碼 資料庫名稱
     */
    //帳號
    private String id = "root";
    //密碼
    private String pwd = "x22122327";
    //資料庫
    private String db = "lottodb";
    //IP
    private String ip = "127.0.0.1";

    public String getDB() {
        return this.db;
    }

    public String getID() {
        return this.id;
    }

    public String getPwd() {
        return this.pwd;
    }

    public String getIP() {
        return this.ip;
    }
}
