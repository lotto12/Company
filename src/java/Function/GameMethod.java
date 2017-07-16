/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

/**
 *
 * @author JimmyYang 玩法各項參數轉換以及取得
 */
public class GameMethod {

    //取得遊戲預設種類
    public String getGame_InitGame(String Gtype) {
        String result = null;
        switch (Gtype) {
            case "801":
                result = "1";
                break;
            case "802":
                result = "1";
                break;
            case "803":
                result = "3";
                break;
        }
        return result;
    }

    //取得遊戲玩法類型
    public String getGame_Type(String Game_Type) {
        String[] game_type_str = {"特別號", "正特碼雙面", "全車", "台號", "特尾三", "二三四星", "天碰", "特殊包牌", "快速下單"};
        return game_type_str[Integer.parseInt(Game_Type) - 1];
    }

    //取得遊戲類型
    public String getGame_GType(String GType) {
        String[] gtype_str = {"六合彩", "大樂透", "539"};
        String result = null;
        switch (GType) {
            case "801":
                result = gtype_str[0];
                break;
            case "802":
                result = gtype_str[1];
                break;
            case "803":
                result = gtype_str[2];
                break;
        }
        return result;
    }
}
