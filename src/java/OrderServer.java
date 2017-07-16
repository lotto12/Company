/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JimmyYang
 */
import Function.EncryptionCode;
import Function.GameOrder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author JimmyYang 下注單使用WebSocket
 */
@ServerEndpoint("/OrderServer")
public class OrderServer {
    //queue holds the list of connected clients

    //Session 連線
    private static Queue<Session> queue = new ConcurrentLinkedQueue<Session>();

    //解碼
    private static EncryptionCode pCode = new EncryptionCode();

    //下注單
    private static GameOrder gameOrder = new GameOrder();

    //收到訊息
    @OnMessage
    public void onMessage(Session session, String msg) {
        try {
            JSONObject json = new JSONObject(msg);
            String function = json.getString("function");
            switch (function) {
                case "order":
                    String check_key = json.getString("check_key");
                    if (pCode.parseCode(session.getId().length(), check_key).equals(session.getId())) {
                        //驗證正確
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //分析注單
                                final String session_id = session.getId();
                                System.out.println("Order_onMessage: " + session_id + " Result:" + msg);
                                try {
                                    boolean isOrderSave = gameOrder.checkFunction(json); //下注進資料庫
                                    if (isOrderSave) {
                                        //下注成功
                                        JSONObject json = new JSONObject();
                                        json.put("type", "check_order");
                                        json.put("result", "true");
                                        sendMsgByID(session_id, json.toString());
                                    } else {
                                        //下注失敗
                                        JSONObject json = new JSONObject();
                                        json.put("type", "check_order");
                                        json.put("result", "false");
                                        sendMsgByID(session_id, json.toString());
                                    }
                                } catch (Exception e) {
                                    System.out.println("Set Order Exception:" + e.toString());
                                }
                            }
                        }).start();
                    } else {
                        //驗證錯誤
                        //紀錄IP
                        System.out.println("Check_Pwd = error");
                    }
                    break;
                case "check_user":
                    //認證完成
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String session_id = session.getId();
                            try {
                                String client_pwd = json.getString("client_pwd");
                                if (pCode.parseCode_Client(client_pwd)) {
                                    JSONObject license_json = new JSONObject();
                                    license_json.put("type", "check_user");
                                    license_json.put("key", pCode.getCheckPwd(session_id));
                                    sendMsgByID(session_id, license_json.toString());
                                }
                            } catch (Exception e) {
                                System.out.println("User Exception:" + e.toString());
                            }
                        }
                    }).start();
                    break;
            }

        } catch (Exception e) {
            System.out.println("Order_onMessage Exception-->" + e.toString());
        }
    }

    //使用者進入
    @OnOpen
    public void open(Session session) {
        queue.add(session);
        //確認已連線_配發一組檢查碼
        try {
            JSONObject json = new JSONObject();
            json.put("type", "check_online");
            sendMsgByID(session.getId(), json.toString());
            System.out.println("Order_New session opened: " + session.getId());
        } catch (Exception e) {
            System.out.println("Order_New Open Exception-->" + e.toString());
        }
    }

    //連線錯誤
    @OnError
    public void error(Session session, Throwable t) {
        queue.remove(session);
        System.err.println("Error on session " + session.getId());
    }

    //使用者離開
    @OnClose
    public void closedConnection(Session session) {
        queue.remove(session);
        System.out.println("session closed: " + session.getId());
    }

    //公告所有使用者消息_更改賠率使用
    private static void sendAll(String msg) {
        try {
            /* Send the new rate to all open WebSocket sessions */
            ArrayList<Session> closedSessions = new ArrayList<>();
            for (Session session : queue) {
                if (!session.isOpen()) {
                    System.err.println("Closed session: " + session.getId());
                    closedSessions.add(session);
                } else {
                    session.getBasicRemote().sendText(msg);
                }
            }
            queue.removeAll(closedSessions);
            System.out.println("Sending " + msg + " to " + queue.size() + " clients");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    //發送指定對象By ID
    private static void sendMsgByID(String user, String msg) {
        try {
            /* Send the new rate to one open WebSocket sessions */
            ArrayList<Session> closedSessions = new ArrayList<>();
            for (Session session : queue) {
                if (!session.isOpen()) {
                    System.err.println("Closed session: " + session.getId());
                    closedSessions.add(session);
                } else if (session.getId().equals(user)) {
                    session.getBasicRemote().sendText(msg);
                }
            }
            queue.removeAll(closedSessions);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
