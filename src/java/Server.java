/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JimmyYang
 */
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author JimmyYang 計算上線人數資料
 */
@ServerEndpoint("/Server")
public class Server {
    //queue holds the list of connected clients

    private static Queue<Session> queue = new ConcurrentLinkedQueue<Session>();
    private static Thread rateThread; //rate publisher thread

//    自動發送訊息
//    static {
//        rateThread = new Thread() {
//            public void run() {
//                while (true) {
//                    if (queue != null) {
//
//                    }
//                    try {
//                        sleep(2000);
//                    } catch (InterruptedException e) {
//                    }
//                }
//            }
//        ;
//        } ;
//  rateThread.start();
//    }
    //收到訊息
    @OnMessage
    public void onMessage(Session session, String msg) {
        System.out.println("onMessage: " + session.getId() + "-->" + msg);
    }

    //使用者進入
    @OnOpen
    public void open(Session session) {
        queue.add(session);
        System.out.println("New session opened: " + session.getId());
        System.out.println("online:" + queue.size());
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

    //公告所有使用者消息
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

}
