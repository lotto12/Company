
import model.DB;
import model.DataTable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jimmy
 */
public class DB_TEST {

    public static void main(String[] args) {
        DataTable dt = DB.getDataTable("SELECT * FROM lottodb.game_setting_num1");
        String test = dt.getColume(0, "ball_1");
        System.out.println(test);
    }
}
